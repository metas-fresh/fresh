package de.metas.email.sender;

import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.email.mailboxes.MailboxType;
import lombok.NonNull;

public interface MailSender
{
	MailboxType getMailboxType();

	EMailSentStatus send(@NonNull EMail mail);
}
