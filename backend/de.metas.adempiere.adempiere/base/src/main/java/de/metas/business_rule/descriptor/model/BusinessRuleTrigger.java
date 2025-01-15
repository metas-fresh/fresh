package de.metas.business_rule.descriptor.model;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;

import javax.annotation.Nullable;

@Value
@Builder
public class BusinessRuleTrigger
{
	@NonNull BusinessRuleTriggerId id;
	@NonNull AdTableId triggerTableId;
	@NonNull ImmutableSet<TriggerTiming> timings;
	@Nullable Validation condition;
	@Nullable String targetRecordMappingSQL;

	public boolean isChangeTypeMatching(@NonNull final TriggerTiming timing) {return timings.contains(timing);}
}