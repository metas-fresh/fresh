package de.metas.business_rule;

import de.metas.business_rule.descriptor.BusinessRuleRepository;
import de.metas.business_rule.descriptor.BusinessRulesChangedListener;
import de.metas.business_rule.descriptor.model.BusinessRulesCollection;
import de.metas.business_rule.descriptor.model.TriggerTiming;
import de.metas.business_rule.event.BusinessRuleEventProcessorCommand;
import de.metas.business_rule.event.BusinessRuleEventRepository;
import de.metas.business_rule.log.BusinessRuleLogger;
import de.metas.business_rule.trigger.BusinessRuleFireTriggersCommand;
import de.metas.record.warning.RecordWarningRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.QueryLimit;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessRuleService
{
	@NonNull private final BusinessRuleRepository ruleRepository;
	@NonNull private final BusinessRuleEventRepository eventRepository;
	@NonNull private final RecordWarningRepository recordWarningRepository;
	@NonNull private final BusinessRuleLogger logger;

	public BusinessRulesCollection getRules()
	{
		return ruleRepository.getAll();
	}

	public void addRulesChangedListener(final BusinessRulesChangedListener listener)
	{
		ruleRepository.addCacheResetListener(listener);
	}

	public void fireTriggersForSourceModel(@NonNull final Object sourceModel, @NonNull final TriggerTiming timing)
	{
		BusinessRuleFireTriggersCommand.builder()
				.eventRepository(eventRepository)
				.logger(logger)
				.rules(ruleRepository.getAll())
				.sourceModel(sourceModel)
				.timing(timing)
				.build()
				.execute();
	}

	public void processEvents(@NonNull final QueryLimit limit)
	{
		BusinessRuleEventProcessorCommand.builder()
				.ruleRepository(ruleRepository)
				.eventRepository(eventRepository)
				.recordWarningRepository(recordWarningRepository)
				.logger(logger)
				//
				.limit(limit)
				//
				.build().execute();
	}
}
