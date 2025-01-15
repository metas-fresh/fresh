package de.metas.business_rule.descriptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.business_rule.descriptor.model.BusinessRule;
import de.metas.business_rule.descriptor.model.BusinessRuleId;
import de.metas.business_rule.descriptor.model.BusinessRulePrecondition;
import de.metas.business_rule.descriptor.model.BusinessRuleTrigger;
import de.metas.business_rule.descriptor.model.BusinessRuleTriggerId;
import de.metas.business_rule.descriptor.model.BusinessRulesCollection;
import de.metas.business_rule.descriptor.model.TriggerTiming;
import de.metas.business_rule.descriptor.model.Validation;
import de.metas.business_rule.descriptor.model.ValidationType;
import de.metas.util.GuavaCollectors;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_BusinessRule;
import org.compiere.model.I_AD_BusinessRule_Precondition;
import org.compiere.model.I_AD_BusinessRule_Trigger;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

@Builder
class BusinessRuleLoader
{
	@NonNull private final IQueryBL queryBL;

	private final HashMap<BusinessRuleId, LinkedHashSet<Integer>> preconditionIdsByRuleId = new HashMap<>();
	private final HashMap<BusinessRuleId, LinkedHashSet<Integer>> triggerIdsByRuleId = new HashMap<>();

	private final HashMap<Integer, I_AD_BusinessRule_Precondition> preconditionsById = new HashMap<>();
	private final HashMap<Integer, I_AD_BusinessRule_Trigger> triggersById = new HashMap<>();

	public void validate(final I_AD_BusinessRule record)
	{
		fromRecord(record);
	}

	public void validate(final I_AD_BusinessRule_Precondition record)
	{
		addToCache(record);
		fromId(BusinessRuleId.ofRepoId(record.getAD_BusinessRule_ID()));
	}

	public void validate(final I_AD_BusinessRule_Trigger record)
	{
		addToCache(record);
		fromId(BusinessRuleId.ofRepoId(record.getAD_BusinessRule_ID()));
	}

	public BusinessRulesCollection retrieveAll()
	{
		queryPreconditions()
				.create()
				.forEach(this::addToCache);

		queryTriggers()
				.create()
				.forEach(this::addToCache);

		return queryBL.createQueryBuilder(I_AD_BusinessRule.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_BusinessRule.COLUMNNAME_AD_BusinessRule_ID)
				.create()
				.stream()
				.map(record -> fromRecord(record))
				.collect(GuavaCollectors.collectUsingListAccumulator(BusinessRulesCollection::ofList));
	}

	private IQueryBuilder<I_AD_BusinessRule_Precondition> queryPreconditions()
	{
		return queryBL.createQueryBuilder(I_AD_BusinessRule_Precondition.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_BusinessRule_Precondition.COLUMNNAME_AD_BusinessRule_ID)
				.orderBy(I_AD_BusinessRule_Precondition.COLUMNNAME_AD_BusinessRule_Precondition_ID);
	}

	private IQueryBuilder<I_AD_BusinessRule_Trigger> queryTriggers()
	{
		return queryBL.createQueryBuilder(I_AD_BusinessRule_Trigger.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_BusinessRule_Trigger.COLUMNNAME_AD_BusinessRule_ID)
				.orderBy(I_AD_BusinessRule_Trigger.COLUMNNAME_AD_BusinessRule_Trigger_ID);
	}

	private void addToCache(final I_AD_BusinessRule_Precondition record)
	{
		final int id = record.getAD_BusinessRule_Precondition_ID();
		preconditionIdsByRuleId.computeIfAbsent(BusinessRuleId.ofRepoId(record.getAD_BusinessRule_ID()), ignored -> new LinkedHashSet<>())
				.add(id);
		preconditionsById.putIfAbsent(id, record);
	}

	private void addToCache(final I_AD_BusinessRule_Trigger record)
	{
		final int id = record.getAD_BusinessRule_Trigger_ID();
		triggerIdsByRuleId.computeIfAbsent(BusinessRuleId.ofRepoId(record.getAD_BusinessRule_ID()), ignored -> new LinkedHashSet<>())
				.add(id);
		triggersById.putIfAbsent(id, record);
	}

	private BusinessRule fromId(@NonNull final BusinessRuleId businessRuleId)
	{
		final I_AD_BusinessRule record = InterfaceWrapperHelper.load(businessRuleId, I_AD_BusinessRule.class);
		if (record == null)
		{
			throw new AdempiereException("No business rule found for " + businessRuleId);
		}
		return fromRecord(record);
	}

	private BusinessRule fromRecord(@NonNull final I_AD_BusinessRule record)
	{
		final BusinessRuleId id = BusinessRuleId.ofRepoId(record.getAD_BusinessRule_ID());
		return BusinessRule.builder()
				.id(id)
				.name(record.getName())
				.adTableId(AdTableId.ofRepoId(record.getAD_Table_ID()))
				.preconditions(getPreconditions(id).stream()
						.map(BusinessRuleLoader::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.validation(extractValidation(record))
				.triggers(getTriggers(id).stream()
						.map(BusinessRuleLoader::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.warningMessage(record.getWarningMessage())
				.build();
	}

	private List<I_AD_BusinessRule_Precondition> getPreconditions(final BusinessRuleId businessRuleId)
	{
		if (preconditionIdsByRuleId.get(businessRuleId) == null)
		{
			// make sure we have preconditionIdsByRuleId set even if there were no preconditions found 
			preconditionIdsByRuleId.computeIfAbsent(businessRuleId, ignored -> new LinkedHashSet<>());

			queryPreconditions()
					.addEqualsFilter(I_AD_BusinessRule_Precondition.COLUMNNAME_AD_BusinessRule_ID, businessRuleId)
					.create()
					.forEach(this::addToCache);
		}

		return preconditionIdsByRuleId.get(businessRuleId)
				.stream()
				.map(preconditionsById::get)
				.collect(ImmutableList.toImmutableList());
	}

	private List<I_AD_BusinessRule_Trigger> getTriggers(final BusinessRuleId businessRuleId)
	{
		if (triggerIdsByRuleId.get(businessRuleId) == null)
		{
			// make sure we have triggerIdsByRuleId set even if there were no triggers found 
			triggerIdsByRuleId.computeIfAbsent(businessRuleId, ignored -> new LinkedHashSet<>());

			queryTriggers()
					.addEqualsFilter(I_AD_BusinessRule_Trigger.COLUMNNAME_AD_BusinessRule_ID, businessRuleId)
					.create()
					.forEach(this::addToCache);
		}

		return triggerIdsByRuleId.get(businessRuleId)
				.stream()
				.map(triggersById::get)
				.collect(ImmutableList.toImmutableList());
	}

	private static Validation extractValidation(final I_AD_BusinessRule record)
	{
		return Validation.adValRule(AdValRuleId.ofRepoId(record.getValidation_Rule_ID()));
	}

	private static BusinessRulePrecondition fromRecord(I_AD_BusinessRule_Precondition record)
	{
		return BusinessRulePrecondition.builder()
				.validation(extractValidation(record))
				.build();
	}

	private static Validation extractValidation(I_AD_BusinessRule_Precondition record)
	{
		final ValidationType type = ValidationType.ofCode(record.getPreconditionType());
		switch (type)
		{
			case SQL:
				//noinspection DataFlowIssue
				return Validation.sql(record.getPreconditionSQL());
			case ValidationRule:
				return Validation.adValRule(AdValRuleId.ofRepoId(record.getPrecondition_Rule_ID()));
			default:
				throw new AdempiereException("Unknown validation type: " + type);
		}
	}

	private static BusinessRuleTrigger fromRecord(I_AD_BusinessRule_Trigger record)
	{
		return BusinessRuleTrigger.builder()
				.id(BusinessRuleTriggerId.ofRepoId(record.getAD_BusinessRule_Trigger_ID()))
				.triggerTableId(AdTableId.ofRepoId(record.getSource_Table_ID()))
				.timings(extractTimings(record))
				.condition(extractCondition(record))
				.targetRecordMappingSQL(record.getTargetRecordMappingSQL())
				.build();
	}

	private static @NonNull ImmutableSet<TriggerTiming> extractTimings(final I_AD_BusinessRule_Trigger record)
	{
		ImmutableSet.Builder<TriggerTiming> timings = ImmutableSet.builder();
		if (record.isOnNew())
		{
			timings.add(TriggerTiming.NEW);
		}
		if (record.isOnUpdate())
		{
			timings.add(TriggerTiming.UPDATE);
		}
		if (record.isOnDelete())
		{
			timings.add(TriggerTiming.DELETE);
		}
		return timings.build();
	}

	@Nullable
	private static Validation extractCondition(final I_AD_BusinessRule_Trigger record)
	{
		return StringUtils.trimBlankToOptional(record.getConditionSQL())
				.map(Validation::sql)
				.orElse(null);
	}
}
