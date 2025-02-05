package de.metas.email.sender;

import ch.qos.logback.classic.Level;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.logger.ILogger;
import com.microsoft.graph.logger.LoggerLevel;
import com.microsoft.graph.models.Attachment;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.FileAttachment;
import com.microsoft.graph.models.InternetMessageHeader;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.models.UserSendMailParameterSet;
import com.microsoft.graph.requests.AttachmentCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.UserRequestBuilder;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.util.time.SystemTime;
import de.metas.email.EMail;
import de.metas.email.EMailAddress;
import de.metas.email.EMailAttachment;
import de.metas.email.EMailSentStatus;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxType;
import de.metas.email.mailboxes.MicrosoftGraphConfig;
import de.metas.error.IErrorManager;
import de.metas.logging.LogManager;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import okhttp3.Request;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.MimeType;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MicrosoftGraphMailSender implements MailSender
{
	@NonNull private final Logger logger = LogManager.getLogger(MicrosoftGraphMailSender.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);

	@Override
	public MailboxType getMailboxType() {return MailboxType.MICROSOFT_GRAPH;}

	@Override
	public EMailSentStatus send(@NonNull final EMail mail)
	{
		final ILogger logger = createGraphLogger(mail);

		final Message message;
		try
		{
			message = toMessage(mail);
		}
		catch (final Exception ex)
		{
			errorManager.createIssue(ex); // make sure the exception is recorded
			return EMailSentStatus.invalid(ex);
		}

		if (mail.isDebugMode())
		{
			logger.logDebug("Sending message:\n " + toString(message));
		}

		try
		{
			newUserRequest(mail.getMailbox(), logger)
					.sendMail(UserSendMailParameterSet.newBuilder()
							.withMessage(message)
							.withSaveToSentItems(true)
							.build())
					.buildRequest()
					.post();

			return EMailSentStatus.ok(message.internetMessageId);
		}
		catch (final Exception ex)
		{
			errorManager.createIssue(ex); // make sure the exception is recorded
			return EMailSentStatus.error(ex);
		}
	}

	private ILogger createGraphLogger(@NonNull final EMail mail)
	{
		if (mail.isDebugMode())
		{
			final ILoggable debugLoggable = mail.getDebugLoggable() != null ? mail.getDebugLoggable() : Loggables.getLoggableOrLogger(logger, Level.INFO);
			return new LoggableAsGraphLoggerAdapter(debugLoggable);
		}
		else
		{
			return new SLF4JAsGraphLoggerAdapter(logger);
		}
	}

	private static Message toMessage(@NonNull final EMail mail)
	{
		final Message message = new Message();
		message.internetMessageId = UUID.randomUUID().toString();

		//
		// Recipients
		addRecipients(message, RecipientType.TO, mail.getTos(), mail.getDebugMailToAddress());
		addRecipients(message, RecipientType.CC, mail.getCcs(), mail.getDebugMailToAddress());
		addRecipients(message, RecipientType.BCC, mail.getBccs(), mail.getDebugMailToAddress());

		if (mail.getReplyTo() != null)
		{
			addRecipient(message, RecipientType.REPLY_TO, mail.getReplyTo());
		}
		else if (mail.getMailbox().getMicrosoftGraphConfigNotNull().getDefaultReplyTo() != null)
		{
			addRecipient(message, RecipientType.REPLY_TO, mail.getMailbox().getMicrosoftGraphConfigNotNull().getDefaultReplyTo());
		}

		//
		// Subject, Body
		message.subject = mail.getSubject();
		message.body = new ItemBody();
		if (mail.isHtmlMessage())
		{
			message.body.contentType = BodyType.HTML;
			message.body.content = mail.getMessageHTML();
		}
		else
		{
			message.body.contentType = BodyType.TEXT;
			message.body.content = mail.getMessageCRLF();
		}

		//
		// Attachments
		message.hasAttachments = true;
		message.attachments = toGraphAttachments(mail.getAttachments());

		//
		// Others
		message.sentDateTime = SystemTime.asZonedDateTime().toOffsetDateTime();
		addHeader(message, "X-Comments", "mail send by metasfresh");

		return message;
	}

	private enum RecipientType
	{TO, CC, BCC, REPLY_TO,}

	private static void addRecipients(
			@NonNull final Message message,
			@NonNull final RecipientType type,
			@Nullable final List<? extends Address> addresses,
			@Nullable final InternetAddress debugMailTo)
	{
		if (addresses == null || addresses.isEmpty())
		{
			return;
		}

		if (debugMailTo != null)
		{
			if (RecipientType.TO.equals(type))
			{
				addRecipient(message, RecipientType.TO, debugMailTo);
			}

			for (final Address address : addresses)
			{
				addHeader(message, "X-metasfreshDebug-Original-Address" + type, address.toString());
			}
		}
		else
		{
			for (final Address address : addresses)
			{
				addRecipient(message, type, address);
			}
		}
	}

	private static void addRecipient(@NonNull final Message message, @NonNull final RecipientType type, @NonNull final Address address)
	{
		addRecipient(message, type, toGraphRecipient(address));
	}

	@SuppressWarnings("SameParameterValue")
	private static void addRecipient(@NonNull final Message message, @NonNull final RecipientType type, @NonNull final EMailAddress address)
	{
		addRecipient(message, type, toGraphRecipient(address));
	}

	private static void addRecipient(@NonNull final Message message, @NonNull final RecipientType type, @NonNull final Recipient recipient)
	{
		final List<Recipient> recipients;
		if (RecipientType.TO.equals(type))
		{
			if (message.toRecipients == null)
			{
				message.toRecipients = new ArrayList<>();
			}
			recipients = message.toRecipients;
		}
		else if (RecipientType.CC.equals(type))
		{
			if (message.ccRecipients == null)
			{
				message.ccRecipients = new ArrayList<>();
			}
			recipients = message.ccRecipients;
		}
		else if (RecipientType.BCC.equals(type))
		{
			if (message.bccRecipients == null)
			{
				message.bccRecipients = new ArrayList<>();
			}
			recipients = message.bccRecipients;
		}
		else if (RecipientType.REPLY_TO.equals(type))
		{
			if (message.replyTo == null)
			{
				message.replyTo = new ArrayList<>();
			}
			recipients = message.replyTo;
		}
		else
		{
			throw new AdempiereException("Recipient type not handled: " + type);
		}

		recipients.add(recipient);
	}

	private static Recipient toGraphRecipient(@NonNull final Address address)
	{
		final Recipient recipient = new Recipient();
		recipient.emailAddress = new EmailAddress();
		recipient.emailAddress.address = address.toString();
		return recipient;
	}

	private static Recipient toGraphRecipient(final @NonNull EMailAddress address)
	{
		final Recipient recipient = new Recipient();
		recipient.emailAddress = new EmailAddress();
		recipient.emailAddress.address = address.getAsString();
		return recipient;
	}

	private static void addHeader(final Message message, final String name, final String value)
	{
		final InternetMessageHeader header = new InternetMessageHeader();
		header.name = name;
		header.value = value;

		if (message.internetMessageHeaders == null)
		{
			message.internetMessageHeaders = new ArrayList<>();
		}
		message.internetMessageHeaders.add(header);
	}

	@Nullable
	private static AttachmentCollectionPage toGraphAttachments(final List<EMailAttachment> emailAttachments)
	{
		if (emailAttachments == null || emailAttachments.isEmpty())
		{
			return null;
		}

		final ImmutableList<Attachment> graphAttachments = emailAttachments.stream()
				.map(MicrosoftGraphMailSender::toGraphAttachment)
				.collect(ImmutableList.toImmutableList());

		return new AttachmentCollectionPage(graphAttachments, null);
	}

	private static Attachment toGraphAttachment(@NonNull final EMailAttachment emailAttachment)
	{
		final FileAttachment graphAttachment = new FileAttachment();
		graphAttachment.name = emailAttachment.getFilename();
		graphAttachment.contentType = MimeType.getMimeType(graphAttachment.name);
		graphAttachment.contentBytes = emailAttachment.getContent();
		graphAttachment.size = graphAttachment.contentBytes != null ? graphAttachment.contentBytes.length : 0;
		graphAttachment.oDataType = "#microsoft.graph.fileAttachment";
		return graphAttachment;
	}

	private UserRequestBuilder newUserRequest(@NonNull final Mailbox mailbox, @NonNull final ILogger logger)
	{
		final String fromEmailId = mailbox.getEmail().getAsString();
		return newClient(mailbox, logger).users(fromEmailId);
	}

	private GraphServiceClient<Request> newClient(@NonNull final Mailbox mailbox, @NonNull final ILogger logger)
	{
		final MicrosoftGraphConfig microsoftGraphConfig = mailbox.getMicrosoftGraphConfigNotNull();

		return GraphServiceClient.builder()
				.authenticationProvider(
						new TokenCredentialAuthProvider(
								ImmutableList.of("https://graph.microsoft.com/.default"),
								new ClientSecretCredentialBuilder()
										.clientId(microsoftGraphConfig.getClientId())
										.clientSecret(microsoftGraphConfig.getClientSecret())
										.tenantId(microsoftGraphConfig.getTenantId())
										.build()
						)
				)
				.logger(logger)
				.buildClient();
	}

	private static String toString(@NonNull final Message message)
	{
		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(message);
		}
		catch (final JsonProcessingException e)
		{
			return message.toString();
		}
	}

	//
	//
	//
	//
	@RequiredArgsConstructor
	private static class LoggableAsGraphLoggerAdapter implements ILogger
	{
		@NonNull private final ILoggable loggable;
		@NonNull @Getter @Setter private LoggerLevel loggingLevel = LoggerLevel.DEBUG;

		@Override
		public void logDebug(@NonNull final String message)
		{
			loggable.addLog(message);
		}

		@Override
		public void logError(@NonNull final String message, @Nullable final Throwable throwable)
		{
			final StringBuilder finalMessage = new StringBuilder("ERROR: ").append(message);
			if (throwable != null)
			{
				finalMessage.append("\n").append(Util.dumpStackTraceToString(throwable));
			}

			loggable.addLog(finalMessage.toString());
		}
	}

	@RequiredArgsConstructor
	private static class SLF4JAsGraphLoggerAdapter implements ILogger
	{
		@NonNull private final Logger logger;

		@Override
		public void setLoggingLevel(@NonNull final LoggerLevel level)
		{
			switch (level)
			{
				case ERROR:
					LogManager.setLoggerLevel(logger, Level.WARN);
					break;
				case DEBUG:
				default:
					LogManager.setLoggerLevel(logger, Level.DEBUG);
					break;
			}
		}

		@Override
		public @NonNull LoggerLevel getLoggingLevel()
		{
			final Level level = LogManager.getLoggerLevel(logger);
			if (level == null)
			{
				return LoggerLevel.DEBUG;
			}
			else if (Level.ERROR.equals(level) || Level.WARN.equals(level))
			{
				return LoggerLevel.ERROR;
			}
			else
			{
				return LoggerLevel.DEBUG;
			}
		}

		@Override
		public void logDebug(@NonNull final String message)
		{
			logger.debug(message);
		}

		@Override
		public void logError(@NonNull final String message, @Nullable final Throwable throwable)
		{
			logger.warn(message, throwable);
		}
	}
}
