package de.metas.business_rule.log;

import de.metas.business_rule.descriptor.model.BusinessRuleId;
import de.metas.business_rule.descriptor.model.BusinessRuleTriggerId;
import de.metas.business_rule.event.BusinessRuleEventId;
import de.metas.error.AdIssueId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.time.Duration;

@Value
@Builder
public class BusinessRuleLogEntryRequest
{
	@Nullable String moduleName;
	@NonNull BusinessRuleLogLevel level;
	@NonNull String message;
	@Nullable AdIssueId errorId;
	@Nullable Duration duration;

	//
	// Context
	@Nullable BusinessRuleId businessRuleId;
	@Nullable BusinessRuleTriggerId triggerId;
	@Nullable BusinessRuleEventId eventId;
	@Nullable TableRecordReference sourceRecordRef;
	@Nullable TableRecordReference targetRecordRef;

	public static BusinessRuleLogEntryRequestBuilder fromContext(@NonNull final BusinessRuleLoggerContext context)
	{
		return builder()
				.moduleName(context.getModuleName())
				.businessRuleId(context.getBusinessRuleId())
				.triggerId(context.getTriggerId())
				.sourceRecordRef(context.getSourceRecordRef())
				.targetRecordRef(context.getTargetRecordRef())
				.eventId(context.getEventId());
	}
}
