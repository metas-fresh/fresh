package de.metas.business_rule.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_AD_BusinessRule_Event;

import javax.annotation.Nullable;

@Value
public class BusinessRuleEventId implements RepoIdAware
{
	int repoId;

	private BusinessRuleEventId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_AD_BusinessRule_Event.COLUMNNAME_AD_BusinessRule_Event_ID);
	}

	@JsonCreator
	public static BusinessRuleEventId ofRepoId(final int repoId) {return new BusinessRuleEventId(repoId);}

	@Nullable
	public static BusinessRuleEventId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new BusinessRuleEventId(repoId) : null;}

	public static int toRepoId(@Nullable final BusinessRuleEventId BusinessRuleEventId) {return BusinessRuleEventId != null ? BusinessRuleEventId.getRepoId() : -1;}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
