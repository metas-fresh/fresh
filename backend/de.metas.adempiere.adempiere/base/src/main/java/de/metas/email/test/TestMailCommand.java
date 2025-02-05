package de.metas.email.test;

import ch.qos.logback.classic.Level;
import de.metas.email.EMailRequest;
import de.metas.email.MailService;
import de.metas.email.mailboxes.MailboxQuery;
import de.metas.logging.LogManager;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import lombok.Builder;
import lombok.NonNull;
import org.slf4j.Logger;

@Builder
public class TestMailCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(TestMailCommand.class);
	@NonNull private final MailService mailService;

	@NonNull private final TestMailRequest request;

	public void execute()
	{
		final ILoggable loggable = request.getLoggable() != null ? request.getLoggable() : Loggables.getLoggableOrLogger(logger, Level.INFO);
		
		mailService.sendEMail(EMailRequest.builder()
				.debugLoggable(loggable)
				.mailboxQuery(MailboxQuery.builder()
						.clientId(request.getClientId())
						.orgId(request.getOrgId())
						.adProcessId(request.getProcessId())
						.customType(request.getEmailCustomType())
						.fromUserId(request.getFromUserId())
						.build())
				.to(request.getMailTo())
				.subject(request.getSubject())
				.message(request.getMessage())
				.html(request.isHtml())
				.build());
	}
}
