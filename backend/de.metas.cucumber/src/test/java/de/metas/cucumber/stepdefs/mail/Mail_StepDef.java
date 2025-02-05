package de.metas.cucumber.stepdefs.mail;

import ch.qos.logback.classic.Level;
import de.metas.common.util.CoalesceUtil;
import de.metas.email.EMailAddress;
import de.metas.email.EMailRequest;
import de.metas.email.MailService;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxType;
import de.metas.email.mailboxes.MicrosoftGraphConfig;
import de.metas.email.mailboxes.SMTPConfig;
import de.metas.logging.LogManager;
import de.metas.util.Loggables;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

public class Mail_StepDef
{
	@NonNull private final Logger logger = LogManager.getLogger(Mail_StepDef.class);
	@NonNull private final MailService mailService = SpringContextHolder.instance.getBean(MailService.class);

	@Given("email successfully sent")
	public void sendTestMail(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		final Map<String, String> row = CollectionUtils.singleElement(rows);

		mailService.sendEMail(EMailRequest.builder()
				.debugLoggable(Loggables.withLogger(logger, Level.INFO))
				.mailbox(extractMailbox(row))
				.to(StringUtils.trimBlankToOptional(resolveEnv(row, "to"))
						.map(EMailAddress::ofNullableString)
						.orElseThrow(() -> new AdempiereException("No 'to' specified")))
				.subject(row.get("subject"))
				.message(row.get("message"))
				.html(StringUtils.toBoolean(row.get("html")))
				.failIfNotSent(true)
				.build());
	}

	private static Mailbox extractMailbox(final Map<String, String> row)
	{
		final MailboxType type = MailboxType.ofCode(row.get("mailbox.type"));
		final Mailbox.MailboxBuilder builder = Mailbox.builder()
				.type(type)
				.email(EMailAddress.ofString(resolveEnv(row, "mailbox.from")));
		switch (type)
		{
			case SMTP:
			{
				return builder
						.smtpConfig(extractSMTPConfig(row))
						.build();
			}
			case MICROSOFT_GRAPH:
			{
				return builder
						.microsoftGraphConfig(extractMicrosoftGraphConfig(row))
						.build();
			}
			default:
			{
				throw new AdempiereException("Unknown mailbox type: " + type);
			}
		}
	}

	private static SMTPConfig extractSMTPConfig(final Map<String, String> row)
	{
		return SMTPConfig.builder()
				.smtpHost(resolveEnv(row, "mailbox.smtp.host"))
				.smtpPort(Integer.parseInt(resolveEnv(row, "mailbox.smtp.port")))
				.smtpAuthorization(StringUtils.toBoolean(resolveEnv(row, "mailbox.smtp.auth")))
				.username(resolveEnv(row, "mailbox.smtp.username"))
				.password(resolveEnv(row, "mailbox.smtp.password"))
				.startTLS(StringUtils.toBoolean(resolveEnv(row, "mailbox.smtp.startTLS")))
				.build();
	}

	private static MicrosoftGraphConfig extractMicrosoftGraphConfig(final Map<String, String> row)
	{
		return MicrosoftGraphConfig.builder()
				.clientId(resolveEnv(row, "mailbox.msgraph.client_id"))
				.tenantId(resolveEnv(row, "mailbox.msgraph.tenantId"))
				.clientSecret(resolveEnv(row, "mailbox.msgraph.clientSecret"))
				.defaultReplyTo(StringUtils.trimBlankToOptional(resolveEnv(row, "mailbox.msgraph.defaultReplyTo"))
						.map(EMailAddress::ofNullableString)
						.orElse(null))
				.build();
	}

	private static String resolveEnv(final Map<String, String> row, final String name)
	{
		final String value = StringUtils.trimBlankToNull(row.get(name));
		if (value != null)
		{
			return value;
		}

		final String propertyName = name + "$env";
		final String env = StringUtils.trimBlankToNull(row.get(propertyName));
		if (env != null)
		{
			return CoalesceUtil.coalesceSuppliers(
					() -> System.getProperty(env),
					() -> System.getenv(env),
					() -> {
						throw new AdempiereException("No system property/environment variable '" + env + "' found");
					}
			);
		}

		throw new AdempiereException("Either '" + name + "' or '" + propertyName + "' is required");
	}
}
