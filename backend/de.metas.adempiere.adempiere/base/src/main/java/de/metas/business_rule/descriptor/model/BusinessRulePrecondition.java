package de.metas.business_rule.descriptor.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class BusinessRulePrecondition
{
	@NonNull Validation validation;
}
