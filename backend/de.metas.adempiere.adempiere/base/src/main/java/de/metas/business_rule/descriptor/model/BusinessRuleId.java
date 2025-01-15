package de.metas.business_rule.descriptor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_AD_BusinessRule;

import javax.annotation.Nullable;

@Value
public class BusinessRuleId implements RepoIdAware
{
	int repoId;

	private BusinessRuleId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_AD_BusinessRule.COLUMNNAME_AD_BusinessRule_ID);
	}

	@JsonCreator
	public static BusinessRuleId ofRepoId(final int repoId) {return new BusinessRuleId(repoId);}

	@Nullable
	public static BusinessRuleId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new BusinessRuleId(repoId) : null;}

	public static int toRepoId(@Nullable final BusinessRuleId BusinessRuleId) {return BusinessRuleId != null ? BusinessRuleId.getRepoId() : -1;}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
