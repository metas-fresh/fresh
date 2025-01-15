package de.metas.business_rule.trigger;

import com.google.common.collect.ImmutableList;
import de.metas.business_rule.descriptor.model.BusinessRuleAndTrigger;
import de.metas.business_rule.descriptor.model.BusinessRuleTrigger;
import de.metas.business_rule.descriptor.model.BusinessRulesCollection;
import de.metas.business_rule.descriptor.model.TriggerTiming;
import de.metas.business_rule.descriptor.model.Validation;
import de.metas.business_rule.event.BusinessRuleEventCreateRequest;
import de.metas.business_rule.event.BusinessRuleEventRepository;
import de.metas.business_rule.log.BusinessRuleLogger;
import de.metas.business_rule.log.BusinessRuleStopwatch;
import de.metas.i18n.BooleanWithReason;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Evaluatee;

import javax.annotation.Nullable;

public class BusinessRuleFireTriggersCommand
{
	@NonNull private static final String LOGGER_MODULE = BusinessRuleFireTriggersCommand.class.getSimpleName();

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final BusinessRuleEventRepository eventRepository;
	@NonNull private final BusinessRuleLogger logger;

	@NonNull final BusinessRulesCollection rules;
	@NonNull private final Object sourceModel;
	@NonNull private final TriggerTiming timing;

	@NonNull private final TableRecordReference sourceModelRef;
	@NonNull private final ClientAndOrgId clientAndOrgId;

	@Builder
	private BusinessRuleFireTriggersCommand(
			@NonNull final BusinessRuleEventRepository eventRepository,
			@NonNull final BusinessRuleLogger logger,
			@NonNull final BusinessRulesCollection rules,
			@NonNull final Object sourceModel,
			@NonNull final TriggerTiming timing)
	{
		this.eventRepository = eventRepository;
		this.logger = logger;
		this.rules = rules;
		this.sourceModel = sourceModel;
		this.timing = timing;

		this.sourceModelRef = TableRecordReference.of(sourceModel);
		this.clientAndOrgId = InterfaceWrapperHelper.getClientAndOrgId(sourceModel);
	}

	public void execute()
	{
		try (final IAutoCloseable ignored = setupLoggerContext())
		{
			for (final BusinessRuleAndTrigger ruleAndTrigger : getRuleAndTriggers())
			{
				try (final IAutoCloseable ignored2 = updateLoggerContextFrom(ruleAndTrigger))
				{
					final BusinessRuleStopwatch stopwatch = logger.newStopwatch();
					final BooleanWithReason matching = checkTriggerMatching(ruleAndTrigger);
					logger.debug(stopwatch, "Checked if trigger is matching source: {}", matching);

					if (matching.isFalse())
					{
						logger.debug("Skip enqueueing event because trigger is not matching");
						continue;
					}

					enqueueToRecompute(ruleAndTrigger);
				}
			}
		}
	}

	private IAutoCloseable setupLoggerContext()
	{
		return logger.temporaryChangeContext(context -> context.moduleName(LOGGER_MODULE)
				.sourceRecordRef(sourceModelRef));
	}

	private IAutoCloseable updateLoggerContextFrom(final BusinessRuleAndTrigger ruleAndTrigger)
	{
		return logger.temporaryChangeContext(contextBuilder -> contextBuilder.businessRuleId(ruleAndTrigger.getBusinessRuleId())
				.triggerId(ruleAndTrigger.getTriggerId()));
	}

	private ImmutableList<BusinessRuleAndTrigger> getRuleAndTriggers()
	{
		return rules.getByTriggerTableId(sourceModelRef.getAdTableId());
	}

	private BooleanWithReason checkTriggerMatching(@NonNull final BusinessRuleAndTrigger ruleAndTrigger)
	{
		try
		{
			final BusinessRuleTrigger trigger = ruleAndTrigger.getTrigger();

			if (!trigger.isChangeTypeMatching(timing))
			{
				return BooleanWithReason.falseBecause("timing not matching");
			}

			return checkConditionMatching(trigger.getCondition());
		}
		catch (final Exception ex)
		{
			logger.debug("Failed evaluating trigger condition {} for {}/{}", ruleAndTrigger, sourceModel, timing, ex);
			return BooleanWithReason.falseBecause(ex.getLocalizedMessage());
		}
	}

	private BooleanWithReason checkConditionMatching(@Nullable final Validation condition)
	{
		if (condition == null)
		{
			return BooleanWithReason.TRUE;
		}

		//noinspection SwitchStatementWithTooFewBranches
		switch (condition.getType())
		{
			case SQL:
			{
				final IStringExpression sqlWhereClauseExpr = condition.getSql();
				if (sqlWhereClauseExpr == null || sqlWhereClauseExpr.isNullExpression())
				{
					return BooleanWithReason.TRUE;
				}

				final Evaluatee ctx = InterfaceWrapperHelper.getEvaluatee(sourceModel);
				final String sqlWhereClause = sqlWhereClauseExpr.evaluate(ctx, IExpressionEvaluator.OnVariableNotFound.ReturnNoResult);
				final String tableName = InterfaceWrapperHelper.getModelTableName(sourceModel);
				final String keyColumnName = InterfaceWrapperHelper.getModelKeyColumnName(sourceModel);
				final int recordId = InterfaceWrapperHelper.getId(sourceModel);

				try
				{
					final boolean marching = queryBL.createQueryBuilder(tableName)
							.addEqualsFilter(keyColumnName, recordId)
							.addFilter(TypedSqlQueryFilter.of(sqlWhereClause))
							.create()
							.anyMatch();
					return marching
							? BooleanWithReason.TRUE
							: BooleanWithReason.falseBecause("SQL condition not met");
				}
				catch (final Exception ex)
				{
					return BooleanWithReason.falseBecause(ex.getLocalizedMessage());
				}
			}
			// case ValidationRule: {
			// 	break;
			// }
			default:
			{
				throw new AdempiereException("Unknown type: " + condition.getType());
			}
		}
	}

	private void enqueueToRecompute(@NonNull final BusinessRuleAndTrigger ruleAndTrigger)
	{
		eventRepository.create(BusinessRuleEventCreateRequest.builder()
				.clientAndOrgId(clientAndOrgId)
				.recordRef(sourceModelRef)
				.businessRuleId(ruleAndTrigger.getBusinessRuleId())
				.triggerId(ruleAndTrigger.getTriggerId())
				.build());

		logger.debug("Enqueued event for re-computation");
	}
}
