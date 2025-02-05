package de.metas.email;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxId;
import de.metas.email.mailboxes.MailboxNotFoundException;
import de.metas.email.mailboxes.MailboxQuery;
import de.metas.email.mailboxes.MailboxRepository;
import de.metas.email.mailboxes.MailboxType;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.email.sender.MailSender;
import de.metas.email.sender.MicrosoftGraphMailSender;
import de.metas.email.sender.SMTPMailSender;
import de.metas.email.templates.MailTemplate;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTemplateRepository;
import de.metas.email.templates.MailText;
import de.metas.email.templates.MailTextBuilder;
import de.metas.email.test.TestMailCommand;
import de.metas.email.test.TestMailRequest;
import de.metas.logging.LogManager;
import de.metas.user.api.IUserBL;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.mail.internet.InternetAddress;
import java.util.List;
import java.util.Properties;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class MailService
{
	@NonNull private static final Logger logger = LogManager.getLogger(MailService.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final MailboxRepository mailboxRepo;
	@NonNull private final IClientDAO clientsRepo = Services.get(IClientDAO.class);
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);
	@NonNull private final MailTemplateRepository mailTemplatesRepo;
	@NonNull private final ImmutableMap<MailboxType, MailSender> mailSenders;

	private static final String SYSCONFIG_DEBUG = "de.metas.email.Debug";
	private static final String SYSCONFIG_DebugMailTo = "org.adempiere.user.api.IUserBL.DebugMailTo";

	public MailService(
			@NonNull final MailboxRepository mailboxRepo,
			@NonNull final MailTemplateRepository mailTemplatesRepo,
			@NonNull final List<MailSender> mailSenders)
	{
		this.mailboxRepo = mailboxRepo;
		this.mailTemplatesRepo = mailTemplatesRepo;
		this.mailSenders = Maps.uniqueIndex(mailSenders, MailSender::getMailboxType);
	}

	public static MailService newInstanceForUnitTesting()
	{
		return new MailService(
				new MailboxRepository(),
				new MailTemplateRepository(),
				ImmutableList.of(
						new SMTPMailSender(),
						new MicrosoftGraphMailSender()
				)
		);
	}

	public Mailbox getMailboxById(final MailboxId mailboxId)
	{
		return mailboxRepo.getById(mailboxId);
	}

	public Mailbox findMailbox(@NonNull final MailboxQuery query)
	{
		Mailbox mailbox = mailboxRepo.findMailBox(query)
				.orElseGet(() -> clientsRepo.getEMailConfigById(query.getClientId()).getMailboxNotNull());

		if (query.getFromUserId() != null)
		{
			final UserEMailConfig userEMailConfig = userBL.getEmailConfigById(query.getFromUserId());
			mailbox = mailbox.mergeFrom(userEMailConfig).orElseThrow(MailboxNotFoundException::new);
		}

		return mailbox;
	}

	public void sendEMail(
			@NonNull final MailboxQuery mailboxQuery,
			@NonNull final EMailAddress to,
			@NonNull final MailText mailText)
	{
		sendEMail(EMailRequest.builder()
				.mailboxQuery(mailboxQuery)
				.to(to)
				.mailText(mailText)
				.build());
	}

	public EMail createEMail(
			@NonNull final Mailbox mailbox,
			@NonNull final EMailAddress to,
			@Nullable final String subject,
			@Nullable final String message,
			final boolean html)
	{
		final EMail email = new EMail(mailbox, to, subject, message, html);
		email.setMailSender(getMailSender(mailbox.getType()));
		email.setDebugMode(isDebugModeEnabled());
		email.setDebugMailToAddress(getDebugMailToAddressOrNull());
		return email;
	}

	public EMail sendEMail(@NonNull final EMailRequest request)
	{
		final ILoggable debugLoggable = request.getDebugLoggable();
		if (debugLoggable != null)
		{
			debugLoggable.addLog("Request: {}", request);
		}

		final Mailbox mailbox;
		if (request.getMailbox() != null)
		{
			mailbox = request.getMailbox();
		}
		else if (request.getMailboxQuery() != null)
		{
			mailbox = findMailbox(request.getMailboxQuery());
		}
		else
		{
			throw new AdempiereException("Cannot find mailbox from " + request);
		}
		if (debugLoggable != null)
		{
			debugLoggable.addLog("Mailbox: {}", mailbox);
		}

		final EMail email = createEMail(mailbox, request.getTo(), request.getSubject(), request.getMessage(), request.isHtml());
		if (request.getDebugLoggable() != null)
		{
			email.setDebugMode(true);
			email.setDebugLoggable(debugLoggable);
		}
		if(request.isForceRealEmailRecipients())
		{
			email.forceRealEmailRecipients();
		}

		request.getToList().forEach(email::addTo);
		request.getAttachments().forEach(email::addAttachment);

		final EMailSentStatus sentStatus = email.send();
		if (request.isFailIfNotSent())
		{
			sentStatus.throwIfNotOK((exception) -> exception.setParameter("email", email));
		}

		return email;
	}

	private MailSender getMailSender(@NonNull final MailboxType type)
	{
		final MailSender mailSender = mailSenders.get(type);
		if (mailSender == null)
		{
			throw new AdempiereException("Unsupported type: " + type);
		}
		return mailSender;
	}

	private boolean isDebugModeEnabled()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_DEBUG, false);
	}

	/**
	 * Gets the EMail TO address to be used when sending mails.
	 * If present, this address will be used to send all emails to a particular address instead of actual email addresses.
	 *
	 * @return email address or null
	 */
	@Nullable
	private InternetAddress getDebugMailToAddressOrNull()
	{
		final Properties ctx = Env.getCtx();
		String emailStr = StringUtils.trimBlankToNull(
				sysConfigBL.getValue(SYSCONFIG_DebugMailTo,
						null,             // defaultValue
						Env.getAD_Client_ID(ctx),
						Env.getAD_Org_ID(ctx))
		);
		if (Check.isEmpty(emailStr, true) || emailStr.equals("-"))
		{
			return null;
		}

		try
		{
			return new InternetAddress(emailStr, true);
		}
		catch (final Exception ex)
		{
			logger.warn("Invalid debug email address provided by sysconfig {}: {}. Returning null.", SYSCONFIG_DebugMailTo, emailStr, ex);
			return null;
		}
	}

	public void send(final EMail email)
	{
		final EMailSentStatus sentStatus = email.send();
		sentStatus.throwIfNotOK();
	}

	public MailTextBuilder newMailTextBuilder(@NonNull final MailTemplate mailTemplate)
	{
		return MailTextBuilder.newInstance(mailTemplate);
	}

	public MailTextBuilder newMailTextBuilder(final MailTemplateId mailTemplateId)
	{
		final MailTemplate mailTemplate = mailTemplatesRepo.getById(mailTemplateId);
		return newMailTextBuilder(mailTemplate);
	}

	public void test(@NonNull TestMailRequest request)
	{
		TestMailCommand.builder()
				.mailService(this)
				.request(request)
				.build()
				.execute();
	}
}
