package de.metas.email;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxQuery;
import de.metas.email.templates.MailText;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class EMailRequest
{
	@Nullable MailboxQuery mailboxQuery;
	@Nullable Mailbox mailbox;

	@NonNull ImmutableList<EMailAddress> toList;
	@Nullable String subject;
	@Nullable String message;
	boolean html;
	@NonNull ImmutableList<EMailAttachment> attachments;

	boolean failIfNotSent;
	@Nullable ILoggable debugLoggable;
	boolean forceRealEmailRecipients;

	@Builder
	private EMailRequest(
			@Nullable final MailboxQuery mailboxQuery,
			@Nullable final Mailbox mailbox,
			@NonNull @Singular("to") final ImmutableList<EMailAddress> toList,
			@Nullable final String subject,
			@Nullable final String message,
			final boolean html,
			@NonNull @Singular final ImmutableList<EMailAttachment> attachments,
			@Nullable final Boolean failIfNotSent,
			@Nullable final ILoggable debugLoggable,
			final boolean forceRealEmailRecipients)
	{
		if (CoalesceUtil.countNotNulls(mailboxQuery, mailbox) != 1)
		{
			throw new AdempiereException("One and only one of mailboxQuery or mailbox shall be specified");
		}
		this.mailboxQuery = mailboxQuery;
		this.mailbox = mailbox;

		this.toList = Check.assumeNotEmpty(toList, "at least one to recipient shall be specified");
		this.subject = subject;
		this.message = message;
		this.html = html;
		this.attachments = attachments;
		this.failIfNotSent = failIfNotSent != null ? failIfNotSent : true;
		this.debugLoggable = debugLoggable;
		this.forceRealEmailRecipients = forceRealEmailRecipients;
	}

	@SuppressWarnings("unused")
	public static class EMailRequestBuilder
	{
		public EMailRequestBuilder mailText(@NonNull final MailText mailText)
		{
			subject(mailText.getMailHeader());
			message(mailText.getFullMailText());
			html(mailText.isHtml());
			return this;
		}

		public EMailRequestBuilder attachmentIfNotEmpty(@NonNull final String filename, @Nullable final byte[] content)
		{
			final EMailAttachment attachment = EMailAttachment.ofNullable(filename, content);
			if (attachment != null)
			{
				attachments.add(attachment);
			}
			return this;
		}
	}

	public EMailAddress getTo() {return toList.get(0);}
}
