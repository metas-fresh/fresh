package de.metas.ui.web.mail;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.attachments.EmailAttachment;
import de.metas.email.EMailAddress;
import de.metas.email.EMailAttachment;
import de.metas.email.EMailRequest;
import de.metas.email.MailService;
import de.metas.email.mailboxes.MailboxQuery;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.logging.LogManager;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.mail.WebuiEmail.WebuiEmailBuilder;
import de.metas.ui.web.mail.WebuiMailRepository.WebuiEmailRemovedEvent;
import de.metas.ui.web.mail.json.JSONEmail;
import de.metas.ui.web.mail.json.JSONEmailRequest;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesPage;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Check;
import de.metas.util.Services;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static de.metas.attachments.AttachmentTags.TAGNAME_SEND_VIA_EMAIL;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@RestController
@RequestMapping(MailRestController.ENDPOINT)
@Schema(description = "Outbound email endpoint")
@RequiredArgsConstructor
public class MailRestController
{
	private static final Logger logger = LogManager.getLogger(MailRestController.class);

	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/mail";

	private final IUserBL userBL = Services.get(IUserBL.class);
	private final UserSession userSession;
	private final WebuiMailRepository mailRepo;
	private final WebuiMailAttachmentsRepository mailAttachmentsRepo;
	private final MailService mailService;
	private final DocumentCollection documentCollection;
	private final MailRestService mailRestService;

	private static final String PATCH_FIELD_To = "to";
	private static final String PATCH_FIELD_Subject = "subject";
	private static final String PATCH_FIELD_Message = "message";
	private static final String PATCH_FIELD_Attachments = "attachments";
	private static final String PATCH_FIELD_TemplateId = "templateId";
	private static final Set<String> PATCH_FIELD_ALL = ImmutableSet.of(PATCH_FIELD_To, PATCH_FIELD_Subject, PATCH_FIELD_Message, PATCH_FIELD_Attachments, PATCH_FIELD_TemplateId);

	private void assertReadable(final WebuiEmail email)
	{
		// Make sure current logged-in user is the owner
		if (!userSession.isLoggedInAs(email.getOwnerUserId()))
		{
			throw new AdempiereException("No credentials to change the email")
					.setParameter("emailId", email.getEmailId())
					.setParameter("ownerUserId", email.getOwnerUserId());
		}

	}

	private void assertWritable(final WebuiEmail email)
	{
		assertReadable(email);

		// Make sure the email was not already sent
		if (email.isSent())
		{
			throw new AdempiereException("Cannot change an email which was already sent")
					.setParameter("emailId", email.getEmailId());
		}
	}

	@PostMapping()
	@Operation(summary = "Creates a new email")
	public JSONEmail createNewEmail(@RequestBody final JSONEmailRequest request)
	{
		userSession.assertLoggedIn();

		final UserId fromUserId = userSession.getLoggedUserId();
		mailService.findMailbox(mailboxQuery(fromUserId)); // i.e. assert can send mails / mailbox is valid


		final IntegerLookupValue from = IntegerLookupValue.of(fromUserId.getRepoId(), userSession.getUserFullname() + " <" + userSession.getUserEmail() + "> ");
		final DocumentPath contextDocumentPath = JSONDocumentPath.toDocumentPathOrNull(request.getDocumentPath());

		final BoilerPlateContext attributes = documentCollection.createBoilerPlateContext(contextDocumentPath);

		final List<LookupValue> lookupValues = new ArrayList<>();
		final Integer toUserId = attributes.getAD_User_ID();
		final LookupValue to = mailRepo.getToByUserId(toUserId);
		if (to != null)
		{
			lookupValues.add(to);
		}

		final Integer ccUserId = attributes.getCC_User_ID();
		if (ccUserId != null && ccUserId > 0) // avoid user System
		{
			final LookupValue cc = mailRepo.getToByUserId(ccUserId);
			if (cc != null)
			{
				lookupValues.add(cc);
			}
		}
		final LookupValuesList emailsTo = LookupValuesList.fromCollection(lookupValues);

		final String emailId = mailRepo.createNewEmail(fromUserId, from, emailsTo, contextDocumentPath).getEmailId();

		if (contextDocumentPath != null)
		{
			try
			{
				final List<EmailAttachment> attachments = mailRestService.getEmailAttachments(contextDocumentPath, TAGNAME_SEND_VIA_EMAIL);

				for (final EmailAttachment attachment : attachments)
				{
					final Supplier<LookupValue> attachmentProducer = () -> mailAttachmentsRepo
							.createAttachment(emailId,
									attachment.getFilename(),
									new ByteArrayResource(attachment.getAttachmentDataSupplier().get()));

					attachFile(emailId, attachmentProducer);
				}
			}
			catch (final Exception ex)
			{
				logger.debug("Failed creating attachment from document print of {}", contextDocumentPath, ex);
			}
		}

		return JSONEmail.of(mailRepo.getEmail(emailId), userSession.getAD_Language());
	}

	@GetMapping("/{emailId}")
	@Operation(summary = "Gets email by ID")
	public JSONEmail getEmail(@PathVariable("emailId") final String emailId)
	{
		userSession.assertLoggedIn();
		final WebuiEmail email = mailRepo.getEmail(emailId);
		assertReadable(email);
		return JSONEmail.of(email, userSession.getAD_Language());
	}

	@PostMapping("/{emailId}/send")
	@Operation(summary = "Sends the email")
	public void sendEmail(@PathVariable("emailId") final String emailId)
	{
		userSession.assertLoggedIn();

		changeEmail(emailId, this::sendEmail);
		mailRepo.removeEmailById(emailId);
	}

	private WebuiEmail sendEmail(final WebuiEmail webuiEmail)
	{
		final List<EMailAddress> toList = extractEMailAddresses(webuiEmail.getTo()).collect(ImmutableList.toImmutableList());
		if (toList.isEmpty())
		{
			throw new FillMandatoryException("To");
		}

		mailService.sendEMail(EMailRequest.builder()
				.mailboxQuery(mailboxQuery(webuiEmail.getFromUserId()))
				.toList(toList)
				.subject(webuiEmail.getSubject())
				.message(webuiEmail.getMessage())
				.html(false)
				.attachments(extractEMailAttachments(webuiEmail))
				.build());

		//
		// Delete temporary attachments
		mailAttachmentsRepo.deleteAttachments(webuiEmail.getEmailId(), webuiEmail.getAttachments());

		// Mark the webui email as sent
		return webuiEmail.toBuilder().sent(true).build();
	}

	private MailboxQuery mailboxQuery(final @NonNull UserId fromUserId)
	{
		return MailboxQuery.builder()
				.clientId(userSession.getClientId())
				.fromUserId(fromUserId)
				.build();
	}

	private Stream<EMailAddress> extractEMailAddresses(final LookupValuesList users)
	{
		return users.stream()
				.map(this::extractEMailAddress);
	}

	private EMailAddress extractEMailAddress(final LookupValue userLookupValue)
	{
		final UserId adUserId = extractUserIdOrNull(userLookupValue);
		if (adUserId == null)
		{
			// consider the email as the DisplayName
			return EMailAddress.ofString(userLookupValue.getDisplayName());
		}
		else
		{
			return userBL.getEMailAddressById(adUserId).orElseThrow();
		}
	}

	private ImmutableList<EMailAttachment> extractEMailAttachments(final WebuiEmail webuiEmail)
	{
		final String emailId = webuiEmail.getEmailId();

		return webuiEmail.getAttachments()
				.stream()
				.map(webuiAttachment -> {
					final byte[] content = mailAttachmentsRepo.getAttachmentAsByteArray(emailId, webuiAttachment);
					return EMailAttachment.of(webuiAttachment.getDisplayName(), content);
				})
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private static UserId extractUserIdOrNull(final LookupValue userLookupValue)
	{
		try
		{
			return userLookupValue.getIdAs(UserId::ofRepoIdOrNull);
		}
		catch (final Exception ex)
		{
			return null;
		}
	}

	@PatchMapping("/{emailId}")
	@Operation(summary = "Changes the email")
	public JSONEmail changeEmail(@PathVariable("emailId") final String emailId, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertLoggedIn();

		final WebuiEmailChangeResult result = changeEmail(emailId, emailOld -> changeEmail(emailOld, events));
		return JSONEmail.of(result.getEmail(), userSession.getAD_Language());
	}

	private WebuiEmailChangeResult changeEmail(final String emailId, final UnaryOperator<WebuiEmail> emailModifier)
	{
		final WebuiEmailChangeResult result = mailRepo.changeEmail(emailId, emailOld -> {
			assertWritable(emailOld);
			return emailModifier.apply(emailOld);
		});

		// Delete the attachments which were removed from email
		final LookupValuesList attachmentsOld = result.getOriginalEmail().getAttachments();
		final LookupValuesList attachmentsNew = result.getEmail().getAttachments();
		final LookupValuesList attachmentsToRemove = attachmentsOld.removeAll(attachmentsNew);
		mailAttachmentsRepo.deleteAttachments(emailId, attachmentsToRemove);

		return result;
	}

	private WebuiEmail changeEmail(final WebuiEmail email, final List<JSONDocumentChangedEvent> events)
	{
		final WebuiEmailBuilder emailBuilder = email.toBuilder();
		events.forEach(event -> changeEmail(email, emailBuilder, event));
		return emailBuilder.build();
	}

	private void changeEmail(final WebuiEmail email, final WebuiEmailBuilder newEmailBuilder, final JSONDocumentChangedEvent event)
	{
		if (!event.isReplace())
		{
			throw new AdempiereException("Unsupported event")
					.setParameter("event", event);
		}

		final String fieldName = event.getPath();
		if (PATCH_FIELD_To.equals(fieldName))
		{
			newEmailBuilder.to(extractStringLookupValuesList(event));

		}
		else if (PATCH_FIELD_Subject.equals(fieldName))
		{
			final String subject = (String)event.getValue();
			newEmailBuilder.subject(subject);
		}
		else if (PATCH_FIELD_Message.equals(fieldName))
		{
			final String message = (String)event.getValue();
			newEmailBuilder.message(message);
		}
		else if (PATCH_FIELD_Attachments.equals(fieldName))
		{
			newEmailBuilder.attachments(extractStringLookupValuesList(event));
		}
		else if (PATCH_FIELD_TemplateId.equals(fieldName))
		{
			final LookupValue templateId = JSONLookupValue.integerLookupValueFromJsonMap((Map<String, Object>)event.getValue());

			if (templateId == null)
			{
				throw new AdempiereException("Invalid " + PATCH_FIELD_TemplateId + ": " + event.getValue());
			}

			applyTemplate(email, newEmailBuilder, templateId);
		}
		else
		{
			throw new AdempiereException("Unsupported event path")
					.setParameter("event", event)
					.setParameter("fieldName", fieldName)
					.setParameter("availablePaths", PATCH_FIELD_ALL);
		}
	}

	private static LookupValuesList extractStringLookupValuesList(final JSONDocumentChangedEvent event)
	{
		@SuppressWarnings("unchecked") final List<Object> jsonList = (List<Object>)event.getValue();
		//noinspection unchecked
		return jsonList.stream()
				.map(value -> toStringLookupValue(value))
				.collect(LookupValuesList.collect());
	}

	private static LookupValue.StringLookupValue toStringLookupValue(final Object value)
	{
		if (value instanceof Map)
		{
			@SuppressWarnings("unchecked") final Map<String, Object> map = (Map<String, Object>)value;
			return JSONLookupValue.stringLookupValueFromJsonMap(map);
		}
		else
		{
			return LookupValue.StringLookupValue.of((String)value, (String)value);
		}

	}

	@EventListener
	public void onWebuiMailRemovedFromRepository(final WebuiEmailRemovedEvent event)
	{
		logger.debug("Got event: {}", event);
		final WebuiEmail email = event.getEmail();
		mailAttachmentsRepo.deleteAttachments(email.getEmailId(), email.getAttachments());
	}

	@GetMapping("/{emailId}/field/to/typeahead")
	@Operation(summary = "Typeahead endpoint for any To field")
	public JSONLookupValuesPage getToTypeahead(@PathVariable("emailId") final String emailId, @RequestParam("query") final String query)
	{
		userSession.assertLoggedIn();
		final LookupValuesPage toTypeahead = mailRepo.getToTypeahead(emailId, query);
		if (!toTypeahead.isEmpty())
		{

			return toJson(toTypeahead);
		}
		else if (!Check.isEmpty(query))
		{
			final String email = query.trim();
			return toJson(LookupValuesPage.ofNullable(LookupValue.StringLookupValue.of(email, email)));
		}
		else
		{
			return toJson(LookupValuesPage.EMPTY.EMPTY);
		}
	}

	private JSONLookupValuesPage toJson(final LookupValuesPage page)
	{
		return JSONLookupValuesPage.of(page, userSession.getAD_Language());
	}

	@PostMapping("/{emailId}/field/attachments")
	@Operation(summary = "Attaches a file to email")
	public JSONEmail attachFile(@PathVariable("emailId") final String emailId, @RequestParam("file") final MultipartFile file)
	{
		userSession.assertLoggedIn();

		final WebuiEmail email = attachFile(emailId, () -> mailAttachmentsRepo.createAttachment(emailId, file));
		return JSONEmail.of(email, userSession.getAD_Language());
	}

	private WebuiEmail attachFile(final String emailId, final Supplier<LookupValue> attachmentProducer)
	{
		// Ask the producer to create the attachment
		@NonNull final LookupValue attachment = attachmentProducer.get();

		try
		{
			final WebuiEmailChangeResult result = changeEmail(emailId, emailOld -> {
				final LookupValuesList attachmentsOld = emailOld.getAttachments();
				final LookupValuesList attachmentsNew = attachmentsOld.addIfAbsent(attachment);

				return emailOld.toBuilder().attachments(attachmentsNew).build();
			});

			return result.getEmail();
		}
		catch (final Throwable ex)
		{
			mailAttachmentsRepo.deleteAttachment(emailId, attachment);
			throw AdempiereException.wrapIfNeeded(ex);
		}

	}

	@GetMapping("/templates")
	@Operation(summary = "Available Email templates")
	public JSONLookupValuesList getTemplates()
	{
		userSession.assertLoggedIn();

		return MADBoilerPlate.streamAllReadable(userSession.getUserRolePermissions())
				.map(adBoilerPlate -> JSONLookupValue.of(adBoilerPlate.getAD_BoilerPlate_ID(), adBoilerPlate.getName()))
				.collect(JSONLookupValuesList.collect());
	}

	private void applyTemplate(final WebuiEmail email, final WebuiEmailBuilder newEmailBuilder, final LookupValue templateId)
	{
		final Properties ctx = Env.getCtx();
		final MADBoilerPlate boilerPlate = MADBoilerPlate.get(ctx, templateId.getIdAsInt());

		if (boilerPlate == null)
		{
			throw new AdempiereException("No template found for " + templateId);
		}

		//
		// Attributes
		final BoilerPlateContext attributes = documentCollection.createBoilerPlateContext(email.getContextDocumentPath());

		//
		// Subject
		final String subject = MADBoilerPlate.parseText(ctx, boilerPlate.getSubject(), true, attributes, ITrx.TRXNAME_None);
		newEmailBuilder.subject(subject);

		// Message
		newEmailBuilder.message(boilerPlate.getTextSnippetParsed(attributes));
	}
}
