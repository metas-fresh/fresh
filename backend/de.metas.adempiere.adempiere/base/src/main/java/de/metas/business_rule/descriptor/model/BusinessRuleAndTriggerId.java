package de.metas.business_rule.descriptor.model;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class BusinessRuleAndTriggerId
{
	@NonNull BusinessRuleId businessRuleId;
	@NonNull BusinessRuleTriggerId triggerId;

	public static BusinessRuleAndTriggerId ofRepoIds(final int businessRuleId, final int triggerId)
	{
		return of(BusinessRuleId.ofRepoId(businessRuleId), BusinessRuleTriggerId.ofRepoId(triggerId));
	}
}
