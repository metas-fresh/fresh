package de.metas.rfq.impl;

import de.metas.document.archive.spi.impl.DefaultModelArchiver;
import de.metas.email.EMail;
import de.metas.email.EMailAddress;
import de.metas.email.EMailRequest;
import de.metas.email.EMailSentStatus;
import de.metas.email.MailService;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxQuery;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailText;
import de.metas.email.templates.MailTextBuilder;
import de.metas.report.PrintFormatId;
import de.metas.rfq.IRfqDAO;
import de.metas.rfq.RfQResponsePublisherRequest;
import de.metas.rfq.RfQResponsePublisherRequest.PublishingType;
import de.metas.rfq.exceptions.RfQPublishException;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQ_Topic;
import de.metas.util.Services;
import org.adempiere.archive.api.ArchiveEmailSentStatus;
import org.adempiere.archive.api.ArchiveResult;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;

import javax.annotation.Nullable;
import java.sql.Timestamp;

/*
 * #%L
 * de.metas.rfq
 * %%
 * Copyright (C) 2016 metas GmbH
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

/* package */class MailRfqResponsePublisherInstance
{
	public static MailRfqResponsePublisherInstance newInstance()
	{
		return new MailRfqResponsePublisherInstance();
	}

	// services
	private final transient IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
	private final transient IArchiveEventManager archiveEventManager = Services.get(IArchiveEventManager.class);
	private final MailService mailService = SpringContextHolder.instance.getBean(MailService.class);

	public enum RfQReportType
	{
		Invitation, InvitationWithoutQtyRequired, Won, Lost,
	}

	private MailRfqResponsePublisherInstance()
	{
		super();
	}

	public void publish(final RfQResponsePublisherRequest request)
	{
		final RfQReportType rfqReportType = getRfQReportType(request);
		publish(request, rfqReportType);

	}

	public void publish(final RfQResponsePublisherRequest request, final RfQReportType rfqReportType)
	{
		try
		{
			publish0(request, rfqReportType);
		}
		catch (final Exception e)
		{
			throw RfQPublishException.wrapIfNeeded(e)
					.setRequest(request);
		}
	}

	public void publish0(final RfQResponsePublisherRequest request, final RfQReportType rfqReportType)
	{
		final I_C_RfQResponse rfqResponse = request.getC_RfQResponse();

		//
		// Check and get the user's mail where we will send the email to
		final I_AD_User userTo = rfqResponse.getAD_User();
		if (userTo == null)
		{
			throw new RfQPublishException(request, "@NotFound@ @AD_User_ID@");
		}
		final EMailAddress userToEmail = EMailAddress.ofNullableString(userTo.getEMail());
		if (userToEmail == null)
		{
			throw new RfQPublishException(request, "@NotFound@ @AD_User_ID@ @Email@ - " + userTo);
		}

		//
		final MailText mailText = createMailTextBuilder(rfqResponse, rfqReportType).build();

		//
		final String subject = mailText.getMailHeader();
		final String message = mailText.getFullMailText();
		final PrintFormatId printFormatId = getPrintFormatId(rfqResponse, rfqReportType);
		final DefaultModelArchiver archiver = DefaultModelArchiver.of(rfqResponse, printFormatId);
		final ArchiveResult pdfArchive = archiver.archive();

		final Mailbox mailbox = mailService.findMailbox(MailboxQuery.ofClientId(ClientId.ofRepoId(rfqResponse.getAD_Client_ID())));

		//
		// Send it
		final EMail email = mailService.sendEMail(EMailRequest.builder()
				.mailbox(mailbox)
				.to(userToEmail)
				.subject(subject)
				.message(message)
				.html(mailText.isHtml())
				.attachmentIfNotEmpty("RfQ_" + rfqResponse.getC_RfQResponse_ID() + ".pdf", pdfArchive.getData())
				.build());
		final EMailSentStatus emailSentStatus = email.getLastSentStatus();

		//
		// Fire mail sent/not sent event (even if there were some errors)
		{
			final EMailAddress from = email.getFrom();
			final EMailAddress to = email.getTo();
			archiveEventManager.fireEmailSent(
					pdfArchive.getArchiveRecord(), // archive
					null, // user
					from, // from
					to, // to
					null, // cc
					null, // bcc
					ArchiveEmailSentStatus.ofEMailSentStatus(emailSentStatus) // status
			);
		}

		//
		// Update RfQ response (if success)
		if (emailSentStatus.isSentOK())
		{
			rfqResponse.setDateInvited(new Timestamp(System.currentTimeMillis()));
			InterfaceWrapperHelper.save(rfqResponse);
		}
		else
		{
			throw new RfQPublishException(request, emailSentStatus.getSentMsg());
		}
	}

	public RfQReportType getRfQReportType(final RfQResponsePublisherRequest request)
	{
		final I_C_RfQResponse rfqResponse = request.getC_RfQResponse();
		final PublishingType publishingType = request.getPublishingType();
		if (publishingType == PublishingType.Invitation)
		{
			if (rfqDAO.hasQtyRequiered(rfqResponse))
			{
				return RfQReportType.Invitation;
			}
			else
			{
				return RfQReportType.InvitationWithoutQtyRequired;
			}
		}
		else if (publishingType == PublishingType.Close)
		{
			if (rfqDAO.hasSelectedWinnerLines(rfqResponse))
			{
				return RfQReportType.Won;
			}
			else
			{
				return RfQReportType.Lost;
			}
		}
		else
		{
			throw new AdempiereException("@Invalid@ @PublishingType@: " + publishingType);
		}
	}

	@Nullable
	private PrintFormatId getPrintFormatId(final I_C_RfQResponse rfqResponse, final RfQReportType rfqReportType)
	{
		final I_C_RfQ_Topic rfqTopic = rfqResponse.getC_RfQ().getC_RfQ_Topic();

		if (rfqReportType == RfQReportType.Invitation)
		{
			return PrintFormatId.ofRepoIdOrNull(rfqTopic.getRfQ_Invitation_PrintFormat_ID());
		}
		else if (rfqReportType == RfQReportType.InvitationWithoutQtyRequired)
		{
			return PrintFormatId.ofRepoIdOrNull(rfqTopic.getRfQ_InvitationWithoutQty_PrintFormat_ID());
		}
		else if (rfqReportType == RfQReportType.Won)
		{
			return PrintFormatId.ofRepoIdOrNull(rfqTopic.getRfQ_Win_PrintFormat_ID());
		}
		else if (rfqReportType == RfQReportType.Lost)
		{
			return PrintFormatId.ofRepoIdOrNull(rfqTopic.getRfQ_Lost_PrintFormat_ID());
		}
		else
		{
			throw new AdempiereException("@Invalid@ @Type@: " + rfqReportType);
		}
	}

	private MailTextBuilder createMailTextBuilder(final I_C_RfQResponse rfqResponse, final RfQReportType rfqReportType)
	{
		final I_C_RfQ_Topic rfqTopic = rfqResponse.getC_RfQ().getC_RfQ_Topic();

		final MailTextBuilder mailTextBuilder;
		if (rfqReportType == RfQReportType.Invitation)
		{
			mailTextBuilder = mailService.newMailTextBuilder(MailTemplateId.ofRepoId(rfqTopic.getRfQ_Invitation_MailText_ID()));
		}
		else if (rfqReportType == RfQReportType.InvitationWithoutQtyRequired)
		{
			mailTextBuilder = mailService.newMailTextBuilder(MailTemplateId.ofRepoId(rfqTopic.getRfQ_InvitationWithoutQty_MailText_ID()));
		}
		else if (rfqReportType == RfQReportType.Won)
		{
			mailTextBuilder = mailService.newMailTextBuilder(MailTemplateId.ofRepoId(rfqTopic.getRfQ_Win_MailText_ID()));
		}
		else if (rfqReportType == RfQReportType.Lost)
		{
			mailTextBuilder = mailService.newMailTextBuilder(MailTemplateId.ofRepoId(rfqTopic.getRfQ_Lost_MailText_ID()));
		}
		else
		{
			throw new AdempiereException("@Invalid@ @Type@: " + rfqReportType);
		}

		mailTextBuilder.bpartner(rfqResponse.getC_BPartner());
		mailTextBuilder.bpartnerContact(rfqResponse.getAD_User());
		mailTextBuilder.record(rfqResponse);
		return mailTextBuilder;
	}
}
