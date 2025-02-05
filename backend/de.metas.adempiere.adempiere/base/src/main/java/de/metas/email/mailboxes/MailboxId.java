package de.metas.email.mailboxes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

@Value
public class MailboxId implements RepoIdAware
{
	@JsonCreator
	public static MailboxId ofRepoId(final int repoId) {return new MailboxId(repoId);}

	public static MailboxId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new MailboxId(repoId) : null;}



	int repoId;

	private MailboxId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_MailBox_ID");
	}

	@JsonValue
	@Override
	public int getRepoId() {return repoId;}

	public static int toRepoId(final MailboxId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

}
