package de.metas.business_rule.descriptor.model;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;

@Value(staticConstructor = "of")
public class BusinessRuleAndTrigger
{
	@NonNull BusinessRule businessRule;
	@NonNull BusinessRuleTrigger trigger;

	public BusinessRuleId getBusinessRuleId() {return businessRule.getId();}

	public BusinessRuleTriggerId getTriggerId() {return trigger.getId();}

	public AdTableId getTriggerTableId() {return trigger.getTriggerTableId();}
}
