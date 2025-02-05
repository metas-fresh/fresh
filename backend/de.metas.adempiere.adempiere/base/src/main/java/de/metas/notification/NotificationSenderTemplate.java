package de.metas.notification;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.email.EMailAttachment;
import de.metas.email.EMailRequest;
import de.metas.email.MailService;
import de.metas.email.mailboxes.Mailbox;
import de.metas.email.mailboxes.MailboxQuery;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.notification.UserNotificationRequest.TargetAction;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.notification.UserNotificationRequest.TargetViewAction;
import de.metas.notification.spi.IRecordTextProvider;
import de.metas.notification.spi.impl.NullRecordTextProvider;
import de.metas.security.IRoleDAO;
import de.metas.security.RoleId;
import de.metas.ui.web.WebuiURLs;
import de.metas.user.UserGroupId;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserGroupUserAssignment;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.lang3.StringUtils;
import org.apache.ecs.ClearElement;
import org.apache.ecs.xhtml.body;
import org.apache.ecs.xhtml.br;
import org.apache.ecs.xhtml.html;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class NotificationSenderTemplate
{
	// services
	private static final Logger logger = LogManager.getLogger(NotificationSenderTemplate.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IUserDAO usersRepo = Services.get(IUserDAO.class);
	private final INotificationBL notificationsService = Services.get(INotificationBL.class);
	private final IRoleDAO rolesRepo = Services.get(IRoleDAO.class);
	private final IRoleNotificationsConfigRepository roleNotificationsConfigRepository = Services.get(IRoleNotificationsConfigRepository.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final INotificationRepository notificationsRepo = Services.get(INotificationRepository.class);
	private final IEventBusFactory eventBusFactory = Services.get(IEventBusFactory.class);
	private final MailService mailService = SpringContextHolder.instance.getBean(MailService.class);
	private final UserGroupRepository userGroupRepository = SpringContextHolder.instance.getBean(UserGroupRepository.class);

	private IRecordTextProvider recordTextProvider = NullRecordTextProvider.instance;

	public void setRecordTextProvider(@NonNull final IRecordTextProvider recordTextProvider)
	{
		this.recordTextProvider = recordTextProvider;
	}

	public void sendAfterCommit(@NonNull final UserNotificationRequest request)
	{
		sendAfterCommit(ImmutableList.of(request));
	}

	public void sendAfterCommit(@NonNull final List<UserNotificationRequest> requests)
	{
		if (requests.isEmpty())
		{
			return;
		}

		final ImmutableList<UserNotificationRequest> requestsEffective = ImmutableList.copyOf(requests);

		trxManager.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.invokeMethodJustOnce(true)
				.registerHandlingMethod(innerTrx -> send(requestsEffective));
	}

	private void send(@NonNull final List<UserNotificationRequest> requests)
	{
		requests.forEach(this::send);
	}

	public void send(@NonNull final UserNotificationRequest request)
	{
		logger.trace("Prepare sending notification: {}", request);
		try
		{
			Stream.of(resolve(request))
					.flatMap(this::explodeByUser)
					.flatMap(this::explodeByEffectiveNotificationsConfigs)
					.forEach(this::send0);
		}
		catch(Exception ex)
		{
			logger.error("Failed to send notification: {}", request, ex);
		}
	}

	private void send0(final UserNotificationRequest request)
	{
		logger.trace("Sending notification: {}", request);

		final UserNotificationsConfig notificationsConfig = request.getNotificationsConfig();
		final UserNotificationsGroup notificationsGroup = notificationsConfig.getGroupByName(request.getNotificationGroupName());
		boolean notifyByInternalMessage = notificationsGroup.isNotifyByInternalMessage();

		if (notificationsGroup.isNotifyByEMail() && !request.isNoEmail())
		{
			try
			{
				sendMail(request);
			}
			catch (final Exception ex)
			{
				logger.warn("Failed sending email for {}. Trying to Send user notification instead.", request, ex);
				notifyByInternalMessage = true;
			}
		}

		if (notifyByInternalMessage)
		{
			sendNotice(request);
		}
	}

	private UserNotificationRequest resolve(@NonNull final UserNotificationRequest request)
	{
		return request.toBuilder()
				.targetAction(resolveTargetRecordAction(request.getTargetAction()))
				.build();
	}

	private Stream<UserNotificationRequest> explodeByUser(final UserNotificationRequest request)
	{
		return explodeRecipients(request.getRecipient())
				.map(request::deriveByRecipient);
	}

	private Stream<Recipient> explodeRecipients(final Recipient recipient)
	{
		if (recipient.isAllUsers())
		{
			return usersRepo.retrieveSystemUserIds()
					.stream()
					.map(Recipient::user);
		}
		else if (recipient.isUser())
		{
			return Stream.of(recipient);
		}
		else if (recipient.isRole())
		{
			final RoleId roleId = recipient.getRoleId();
			return rolesRepo.retrieveUserIdsForRoleId(roleId)
					.stream()
					.map(userId -> Recipient.userAndRole(userId, roleId));
		}
		else if (recipient.isAllRolesContainingGroup())
		{
			final Set<RoleId> roleIds = roleNotificationsConfigRepository.getRoleIdsContainingNotificationGroupName(recipient.getNotificationGroupName());
			return roleIds.stream()
					.flatMap(roleId -> rolesRepo.retrieveUserIdsForRoleId(roleId)
							.stream()
							.map(userId -> Recipient.userAndRole(userId, roleId)));
		}
		else if (recipient.isGroup())
		{
			final UserGroupId groupId = recipient.getGroupId();
			return userGroupRepository.getByUserGroupId(groupId)
					.streamAssignmentsFor(groupId, Instant.now())
					.map(UserGroupUserAssignment::getUserId)
					.map(Recipient::user);
		}
		else
		{
			throw new AdempiereException("Recipient type not supported: " + recipient);
		}
	}

	private Stream<UserNotificationRequest> explodeByEffectiveNotificationsConfigs(final UserNotificationRequest request)
	{
		return extractEffectiveNotificationsConfigs(request)
				.stream()
				.map(request::deriveByNotificationsConfig);
	}

	private TargetAction resolveTargetRecordAction(final TargetAction targetAction)
	{
		if (!(targetAction instanceof TargetRecordAction))
		{
			return targetAction;
		}

		final TargetRecordAction targetRecordAction = TargetRecordAction.cast(targetAction);
		return targetRecordAction.toBuilder()
				.recordDisplayText(resolveTargetRecordDisplayText(targetRecordAction))
				.adWindowId(resolveTargetWindowId(targetRecordAction))
				.build();
	}

	private String resolveTargetRecordDisplayText(@NonNull final TargetRecordAction targetRecordAction)
	{
		if (!Check.isEmpty(targetRecordAction.getRecordDisplayText()))
		{
			return targetRecordAction.getRecordDisplayText();
		}

		// Provide more specific information to the user, in case there exists a notification context provider (task 09833)
		final ITableRecordReference record = targetRecordAction.getRecord();
		final String targetRecordDisplayText = recordTextProvider.getTextMessageIfApplies(record).orNull();
		if (!Check.isEmpty(targetRecordDisplayText, true))
		{
			return targetRecordDisplayText;
		}

		//
		try
		{
			final Object targetRecordModel = record.getModel(PlainContextAware.createUsingOutOfTransaction());
			final String documentNo = documentBL.getDocumentNo(targetRecordModel);
			return documentNo;
		}
		catch (final Exception ex)
		{
			logger.info("Failed retrieving record for " + record, ex);
		}

		//
		return "#" + record.getRecord_ID();
	}

	private Optional<AdWindowId> resolveTargetWindowId(final TargetRecordAction targetRecordAction)
	{
		if (targetRecordAction.getAdWindowId().isPresent())
		{
			return targetRecordAction.getAdWindowId();
		}

		return RecordWindowFinder.newInstance(targetRecordAction.getRecord())
				.checkRecordPresentInWindow()
				.findAdWindowId();
	}

	private String extractSubjectText(final UserNotificationRequest request)
	{
		if (!Check.isEmpty(request.getSubjectPlain()))
		{
			return request.getSubjectPlain();
		}

		if (!Check.isEmpty(request.getSubjectADMessage()))
		{
			final String adLanguage = request.getADLanguageOrGet(Env::getADLanguageOrBaseLanguage);
			return msgBL.getMsg(
					adLanguage,
					request.getSubjectADMessage(),
					request.getSubjectADMessageParams());
		}

		return "";
	}

	private static String extractSubjectFromContent(final String content)
	{
		if (Check.isEmpty(content, true))
		{
			return "";
		}

		String subject = content.trim();
		final int idx = subject.indexOf("\n");
		if (idx > 0)
		{
			subject = subject.substring(0, idx).trim();
		}

		return StringUtils.abbreviate(subject, 100);
	}

	private String extractContentText(final UserNotificationRequest request, final boolean html)
	{
		if (!Check.isEmpty(request.getContentPlain()))
		{
			return request.getContentPlain();
		}

		if (!Check.isEmpty(request.getContentADMessage()))
		{
			return prepareMessageFormatter(request)
					.html(html)
					.adLanguage(request.getADLanguageOrGet(Env::getADLanguageOrBaseLanguage))
					.format(request.getContentADMessage(), request.getContentADMessageParams());
		}

		return "";
	}

	private List<UserNotificationsConfig> extractEffectiveNotificationsConfigs(@NonNull final UserNotificationRequest request)
	{
		final Recipient recipient = request.getRecipient();
		Check.assume(!recipient.isAllUsers(), "request shall not have broadcast flag set: {}", request);

		UserNotificationsConfig notificationsConfig;
		if (request.getNotificationsConfig() != null)
		{
			notificationsConfig = request.getNotificationsConfig();
		}
		else if (recipient.isUser())
		{

			notificationsConfig = notificationsService.getUserNotificationsConfig(recipient.getUserId());

			if (recipient.isRoleIdSet())
			{
				final RoleNotificationsConfig roleNotificationsConfig = notificationsService.getRoleNotificationsConfig(recipient.getRoleId());
				notificationsConfig = notificationsConfig.deriveWithNotificationGroups(roleNotificationsConfig.getNotificationGroups());
			}
		}
		else
		{
			throw new AdempiereException("Recipient not supported: " + recipient);
		}

		final NotificationGroupName groupName = request.getNotificationGroupName();
		return extractEffectiveNotificationsConfigs(notificationsConfig, groupName);
	}

	private List<UserNotificationsConfig> extractEffectiveNotificationsConfigs(
			@NonNull final UserNotificationsConfig notificationsConfig,
			@NonNull final NotificationGroupName groupName)
	{
		final List<UserNotificationsConfig> result = new ArrayList<>();

		final LinkedHashSet<UserId> seenUserIds = new LinkedHashSet<>();
		UserNotificationsConfig currentNotificationsConfig = notificationsConfig;

		while (currentNotificationsConfig != null)
		{
			if (!seenUserIds.add(currentNotificationsConfig.getUserId()))
			{
				logger.warn("Detected a cycle in the AD_User.AD_User_InCharge_IDs. The AD_User_IDs in question are {}, initialConfig={}, groupName={}", seenUserIds, notificationsConfig, groupName);
				break;
			}

			final UserNotificationsGroup currentNotificationsGroup = currentNotificationsConfig.getGroupByName(groupName);
			if (currentNotificationsGroup.hasAnyNotificationTypesExceptUserInCharge())
			{
				result.add(currentNotificationsConfig);
			}

			if (!currentNotificationsGroup.isNotifyUserInCharge())
			{
				currentNotificationsConfig = null;
				break;
			}

			if (!currentNotificationsConfig.isUserInChargeSet())
			{
				currentNotificationsConfig = null;
				break;
			}

			currentNotificationsConfig = notificationsService.getUserNotificationsConfig(currentNotificationsConfig.getUserInChargeId());
		}

		return result;
	}

	private void sendNotice(final UserNotificationRequest request)
	{
		try
		{
			final UserNotification notification = notificationsRepo.save(request);

			final Topic topic = Topic.distributed(request.getNotificationGroupName().getValueAsString());

			eventBusFactory
					.getEventBus(topic)
					.enqueueEvent(UserNotificationUtils.toEvent(notification));
		}
		catch (final Exception ex)
		{
			logger.warn("Failed saving and sending notification: {}", request, ex);
		}
	}

	private void sendMail(final UserNotificationRequest request)
	{
		final UserNotificationsConfig notificationsConfig = request.getNotificationsConfig();

		final boolean html = true;
		final String content = extractMailContent(request);
		final String subject = extractMailSubject(request);

		mailService.sendEMail(EMailRequest.builder()
				.mailboxQuery(mailboxQuery(notificationsConfig))
				.to(notificationsConfig.getEmail())
				.subject(subject)
				.message(content)
				.html(html)
				.attachments(request.getAttachments().stream().map(EMailAttachment::of).collect(Collectors.toList()))
				.build());
	}

	private Mailbox findMailbox(@NonNull final UserNotificationsConfig notificationsConfig)
	{
		return mailService.findMailbox(mailboxQuery(notificationsConfig));
	}

	private static MailboxQuery mailboxQuery(final @NonNull UserNotificationsConfig notificationsConfig)
	{
		return MailboxQuery.builder()
				.clientId(notificationsConfig.getClientId())
				.orgId(notificationsConfig.getOrgId())
				.build();
	}

	@VisibleForTesting
	String extractMailSubject(final UserNotificationRequest request)
	{
		final String subject = extractSubjectText(request);

		if (Check.isEmpty(subject, true))
		{
			return extractSubjectFromContent(extractContentText(request, /* html */false));
		}

		return subject;
	}

	@VisibleForTesting
	String extractMailContent(final UserNotificationRequest request)
	{
		final body htmlBody = new body();
		final String htmlBodyString = extractContentText(request, /* html */true);
		if (!Check.isEmpty(htmlBodyString))
		{
			Splitter.on("\n")
					.splitToList(htmlBodyString.trim())
					.forEach(htmlLine -> htmlBody.addElement(new ClearElement(htmlLine)).addElement(new br()));
		}

		return new html().addElement(htmlBody).toString();
	}

	private NotificationMessageFormatter prepareMessageFormatter(final UserNotificationRequest request)
	{
		final NotificationMessageFormatter formatter = NotificationMessageFormatter.newInstance();

		final TargetAction targetAction = request.getTargetAction();
		if (targetAction instanceof TargetRecordAction)
		{
			final TargetRecordAction targetRecordAction = TargetRecordAction.cast(targetAction);
			final TableRecordReference targetRecord = targetRecordAction.getRecord();
			final String targetRecordDisplayText = targetRecordAction.getRecordDisplayText();
			if (!Check.isEmpty(targetRecordDisplayText))
			{
				formatter.recordDisplayText(targetRecord, targetRecordDisplayText);
			}

			final AdWindowId targetWindowId = targetRecordAction.getAdWindowId().orElse(null);
			if (targetWindowId != null)
			{
				formatter.recordWindowId(targetRecord, targetWindowId);
			}
		}
		else if (targetAction instanceof TargetViewAction)
		{
			final TargetViewAction targetViewAction = TargetViewAction.cast(targetAction);
			final String viewURL = WebuiURLs.newInstance().getViewUrl(targetViewAction.getAdWindowId(), targetViewAction.getViewId());
			formatter.bottomUrl(viewURL);
		}

		return formatter;
	}
}
