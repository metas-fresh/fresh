package de.metas.business_rule.event;

import de.metas.business_rule.descriptor.model.BusinessRuleAndTriggerId;
import de.metas.business_rule.descriptor.model.BusinessRuleId;
import de.metas.business_rule.descriptor.model.BusinessRuleTriggerId;
import de.metas.error.AdIssueId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class BusinessRuleEvent
{
	@NonNull BusinessRuleEventId id;
	@NonNull BusinessRuleAndTriggerId businessRuleAndTriggerId;
	@NonNull TableRecordReference sourceRecordRef;
	boolean processed;
	@Nullable AdIssueId errorId;

	public BusinessRuleId getBusinessRuleId() {return businessRuleAndTriggerId.getBusinessRuleId();}

	public BusinessRuleTriggerId getTriggerId() {return businessRuleAndTriggerId.getTriggerId();}

	public BusinessRuleEvent markAsProcessedOK()
	{
		return toBuilder().processed(true).errorId(null).build();
	}

	public BusinessRuleEvent markAsProcessingError(@NonNull final AdIssueId errorId)
	{
		return toBuilder().processed(true).errorId(errorId).build();
	}
}
