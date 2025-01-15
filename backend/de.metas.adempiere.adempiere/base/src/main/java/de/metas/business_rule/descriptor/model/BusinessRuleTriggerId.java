package de.metas.business_rule.descriptor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_AD_BusinessRule_Trigger;

import javax.annotation.Nullable;

@Value
public class BusinessRuleTriggerId implements RepoIdAware
{
	int repoId;

	private BusinessRuleTriggerId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_AD_BusinessRule_Trigger.COLUMNNAME_AD_BusinessRule_Trigger_ID);
	}

	@JsonCreator
	public static BusinessRuleTriggerId ofRepoId(final int repoId) {return new BusinessRuleTriggerId(repoId);}

	@Nullable
	public static BusinessRuleTriggerId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new BusinessRuleTriggerId(repoId) : null;}

	public static int toRepoId(@Nullable final BusinessRuleTriggerId BusinessRuleTriggerId) {return BusinessRuleTriggerId != null ? BusinessRuleTriggerId.getRepoId() : -1;}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
