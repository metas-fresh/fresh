package de.metas.business_rule.trigger.interceptor;

import de.metas.business_rule.BusinessRuleService;
import de.metas.business_rule.descriptor.model.BusinessRulesCollection;
import de.metas.business_rule.descriptor.model.TriggerTiming;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.compiere.model.I_AD_Client;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;

/**
 * Intercepts source tables and pushes the trigger if necessary.
 */
@Component
public class BusinessRuleTriggerInterceptor extends AbstractModelInterceptor
{
	@NonNull private static final Logger logger = LogManager.getLogger(BusinessRuleTriggerInterceptor.class);
	@NonNull private final BusinessRuleService ruleService;
	private IModelValidationEngine engine;

	@NonNull private BusinessRulesCollection rules = BusinessRulesCollection.EMPTY;
	@NonNull private final HashSet<String> registeredTableNames = new HashSet<>();

	public BusinessRuleTriggerInterceptor(final @NonNull BusinessRuleService ruleService)
	{
		this.ruleService = ruleService;
	}

	private @NotNull BusinessRulesCollection getRules() {return rules;}

	private IModelValidationEngine getEngine() {return Check.assumeNotNull(this.engine, "engine is set");}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		this.engine = engine;
		ruleService.addRulesChangedListener(this::updateFromBusinessRulesRepository);

		updateFromBusinessRulesRepository();
	}

	private synchronized void updateFromBusinessRulesRepository()
	{
		final BusinessRulesCollection rulesPrev = this.rules;
		this.rules = ruleService.getRules();
		if (Objects.equals(this.rules, rulesPrev))
		{
			return;
		}

		updateRegisteredInterceptors();
	}

	private synchronized void updateRegisteredInterceptors()
	{
		final IModelValidationEngine engine = getEngine();
		final BusinessRulesCollection rules = getRules();

		final HashSet<String> registeredTableNamesNoLongerNeeded = new HashSet<>(registeredTableNames);

		for (final AdTableId triggerTableId : rules.getTriggerTableIds())
		{
			final String triggerTableName = TableIdsCache.instance.getTableName(triggerTableId);
			registeredTableNamesNoLongerNeeded.remove(triggerTableName);

			if (registeredTableNames.contains(triggerTableName))
			{
				// already registered
				continue;
			}

			engine.addModelChange(triggerTableName, this);
			registeredTableNames.add(triggerTableName);
			logger.info("Registered trigger for {}", triggerTableName);
		}

		//
		// Remove no longer needed interceptors
		for (final String triggerTableName : registeredTableNamesNoLongerNeeded)
		{
			engine.removeModelChange(triggerTableName, this);
			registeredTableNames.remove(triggerTableName);
			logger.info("Unregistered trigger for {}", triggerTableName);
		}
	}

	@Override
	public void onModelChange(@NonNull final Object model, @NonNull final ModelChangeType modelChangeType) throws Exception
	{
		final TriggerTiming timing = TriggerTiming.ofModelChangeType(modelChangeType).orElse(null);
		if (timing == null)
		{
			return;
		}

		ruleService.fireTriggersForSourceModel(model, timing);
	}
}
