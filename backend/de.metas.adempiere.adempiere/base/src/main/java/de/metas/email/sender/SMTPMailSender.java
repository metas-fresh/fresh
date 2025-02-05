package de.metas.email.sender;

import ch.qos.logback.classic.Level;
import com.sun.mail.smtp.SMTPMessage;
import de.metas.email.EMail;
import de.metas.email.EMailAttachment;
import de.metas.email.EMailSentStatus;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxType;
import de.metas.email.mailboxes.SMTPConfig;
import de.metas.logging.LogManager;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.Nullable;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;

@Component
public class SMTPMailSender implements MailSender
{
	private static final Logger logger = LogManager.getLogger(SMTPMailSender.class);

	@Override
	public MailboxType getMailboxType() {return MailboxType.SMTP;}

	@Override
	public EMailSentStatus send(@NonNull final EMail mail)
	{
		final boolean isDebugModeEnabled = mail.isDebugMode();
		final ILoggable debugLoggable = mail.getDebugLoggable() != null
				? mail.getDebugLoggable()
				: Loggables.getLoggableOrLogger(logger, Level.INFO);

		final Mailbox mailbox = mail.getMailbox();

		//
		// Create session
		final Session session;
		try
		{
			session = createSession(mailbox.getSmtpConfigNotNull(), isDebugModeEnabled, debugLoggable);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed creating the session from {}", mailbox, ex);
			return EMailSentStatus.invalid(ex);
		}

		try
		{
			final SMTPMessage smtpMessage = new SMTPMessage(session);

			// Addresses
			smtpMessage.setFrom(mail.getFrom().toInternetAddress());

			//
			// Note: setRecipients can and will use a default debug email address if configured (see JavaDoc & impl)
			setRecipients(smtpMessage, Message.RecipientType.TO, mail.getTos(), mail.getDebugMailToAddress());
			setRecipients(smtpMessage, Message.RecipientType.CC, mail.getCcs(), mail.getDebugMailToAddress());
			setRecipients(smtpMessage, Message.RecipientType.BCC, mail.getBccs(), mail.getDebugMailToAddress());

			final InternetAddress replyTo = mail.getReplyTo();
			if (replyTo != null)
			{
				smtpMessage.setReplyTo(new Address[] { replyTo });
			}
			//
			smtpMessage.setSentDate(new java.util.Date());
			smtpMessage.setHeader("X-Comments", "mail send by metasfresh");
			// m_msg.setDescription("Description");
			// SMTP specifics
			smtpMessage.setAllow8bitMIME(true);
			// Send notification on Failure & Success - no way to set envid in Java yet
			// m_msg.setNotifyOptions (SMTPMessage.NOTIFY_FAILURE | SMTPMessage.NOTIFY_SUCCESS);
			// Bounce only header
			smtpMessage.setReturnOption(SMTPMessage.RETURN_HDRS);
			// m_msg.setHeader("X-Mailer", "msgsend");
			//
			setContent(smtpMessage, mail);
			smtpMessage.saveChanges();

			if (isDebugModeEnabled)
			{
				debugLoggable.addLog("SMTP message to send:\n{}", toString(smtpMessage));
			}

			Transport.send(smtpMessage);

			final String messageId = smtpMessage.getMessageID();
			logger.debug("Sent OK - MessageID={}", messageId);

			return EMailSentStatus.ok(messageId);
		}
		catch (final MessagingException me)
		{
			return EMailSentStatus.error(me);
		}
		catch (final Exception ex)
		{
			logger.error("Send error", ex);
			return EMailSentStatus.error(ex);
		}
	}

	/**
	 * Sets recipients.
	 * <b>NOTE: If <code>debugMailTo</code> is not null, it will send to that instead!</b>
	 */
	private void setRecipients(
			@NonNull final SMTPMessage message,
			@NonNull final Message.RecipientType type,
			@Nullable final List<? extends Address> addresses,
			@Nullable final InternetAddress debugMailTo) throws MessagingException
	{
		if (addresses == null || addresses.isEmpty())
		{
			return;
		}

		if (debugMailTo != null)
		{
			if (Message.RecipientType.TO.equals(type))
			{
				message.setRecipient(Message.RecipientType.TO, debugMailTo);
			}

			for (final Address address : addresses)
			{
				message.addHeader("X-metasfreshDebug-Original-Address" + type, address.toString());
			}
		}
		else
		{
			final Address[] addressesArr = addresses.toArray(new Address[0]);
			message.setRecipients(Message.RecipientType.TO, addressesArr);
		}
	}

	private void setContent(final MimeMessage msg, final EMail from) throws MessagingException
	{
		final String subject = from.getSubject();
		msg.setSubject(subject, from.getCharsetName());

		//
		// Simple Message (no attachments)
		final List<EMailAttachment> attachments = from.getAttachments();
		if (attachments.isEmpty())
		{
			setMessageContent(msg, from);
			logger.debug("(simple) {}", subject);
		}
		//
		// Multi-part message (with attachments)
		else
		{
			final Multipart mp = new MimeMultipart();

			//
			// First Part - the Message
			{
				final MimeBodyPart mbp_message = new MimeBodyPart();
				setMessageContent(mbp_message, from);
				mp.addBodyPart(mbp_message);
				logger.debug("Composing multipart message for subject {} - {}", subject, mbp_message);
			}

			//
			// for all attachments
			for (final EMailAttachment attachment : attachments)
			{
				final DataSource ds = attachment.createDataSource();

				// Attachment Part
				final MimeBodyPart mbp_attachment = new MimeBodyPart();
				mbp_attachment.setDataHandler(new DataHandler(ds));
				mbp_attachment.setFileName(ds.getName());
				logger.debug("Added Attachment {} - {}", ds.getName(), mbp_attachment);
				mp.addBodyPart(mbp_attachment);
			}

			// Add to Message
			msg.setContent(mp);
		}
	}

	private void setMessageContent(final MimePart part, final EMail from) throws MessagingException
	{
		if (from.isHtmlMessage())
		{
			part.setText(from.getMessageHTML(), from.getCharsetName(), "html"); // #107 FRESH-445: this call solves the issue
		}
		else
		{
			part.setText(from.getMessageCRLF(), from.getCharsetName(), "plain"); // just explicitly adding "plain" for better contrast with "html"
		}
	}

	private Session createSession(@NonNull final SMTPConfig smtpConfig, boolean isDebugModeEnabled, @NonNull ILoggable debugLoggable)
	{
		final MailAuthenticator auth;
		if (smtpConfig.isSmtpAuthorization())
		{
			auth = MailAuthenticator.of(smtpConfig.getUsername(), smtpConfig.getPassword());
		}
		else
		{
			auth = null;
		}

		//
		// Setup mail system properties
		final Properties props = new Properties(System.getProperties());
		props.put("mail.store.protocol", "smtp");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.host", smtpConfig.getSmtpHost());
		props.put("mail.smtp.port", smtpConfig.getSmtpPort());
		if (smtpConfig.isStartTLS())
		{
			props.put("mail.smtp.starttls.enable", "true");
		}
		if (auth != null)
		{
			props.put("mail.smtp.auth", "true");
		}

		if (isDebugModeEnabled)
		{
			props.put("mail.debug", "true");
		}

		if (isDebugModeEnabled)
		{
			debugLoggable.addLog("Session properties: \n{}", props);
		}

		final Session session = Session.getInstance(props, auth);

		if (isDebugModeEnabled)
		{
			session.setDebug(isDebugModeEnabled);
			//noinspection resource
			session.setDebugOut(LoggableOutputStreamAdapter.ofLoggable(debugLoggable).toPrintStream());
		}
		return session;
	}

	private static String toString(@NonNull final SMTPMessage message)
	{
		try
		{
			final ByteArrayOutputStream os = new ByteArrayOutputStream();
			message.writeTo(os);
			return os.toString();
		}
		catch (Exception ex)
		{
			return message.toString();
		}
	}

	//
	//
	//
	//
	//
	private static class LoggableOutputStreamAdapter extends OutputStream
	{
		@NonNull private final ILoggable loggable;
		@NonNull private final ByteArrayOutputStream buffer;

		private LoggableOutputStreamAdapter(@NonNull final ILoggable loggable)
		{
			this.loggable = loggable;
			this.buffer = new ByteArrayOutputStream();
		}
		
		public static LoggableOutputStreamAdapter ofLoggable(@NonNull final ILoggable loggable)
		{
			return new LoggableOutputStreamAdapter(loggable);
		}
		
		public PrintStream toPrintStream()
		{
			return new PrintStream(this);
		}

		@Override
		public void write(final int b)
		{
			buffer.write(b);
			flushCompleteLinesFromBuffer();
		}

		@Override
		public void write(final byte @NonNull [] b, final int off, final int len)
		{
			buffer.write(b, off, len);
			flushCompleteLinesFromBuffer();
		}

		@Override
		public void flush()
		{
			flushCompleteLinesFromBuffer();
		}

		private void flushCompleteLinesFromBuffer()
		{
			final String string = buffer.toString();
			if (string.isEmpty())
			{
				return;
			}

			buffer.reset();

			String remaining = string;
			int idx;
			while ((idx = remaining.indexOf('\n')) >= 0)
			{
				final String line = string.substring(0, idx);
				loggable.addLog(line);

				remaining = string.substring(idx + 1);
			}

			if(!remaining.isEmpty())
			{
				buffer.write(remaining.getBytes(), 0, remaining.length());
			}
		}
	}
}
