package de.metas.notification.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.document.references.zoom_into.CustomizedWindowInfo;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMapRepository;
import de.metas.i18n.AdMessageId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationRepository;
import de.metas.notification.UserNotification;
import de.metas.notification.UserNotification.UserNotificationBuilder;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetAction;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.notification.UserNotificationRequest.TargetViewAction;
import de.metas.notification.UserNotificationTargetType;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Note;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

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

@Service
public class NotificationRepository implements INotificationRepository
{
	private static final Logger logger = LogManager.getLogger(NotificationRepository.class);

	/** AD_Message to be used when there was no AD_Message provided */
	private static final AdMessageKey DEFAULT_AD_MESSAGE = AdMessageKey.of("webui.window.notification.caption");

	private final ObjectMapper jsonMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	private final AttachmentEntryService attachmentEntryService;
	private final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;


	public NotificationRepository(
			@NonNull final AttachmentEntryService attachmentEntryService,
			@NonNull final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository)
	{
		this.attachmentEntryService = attachmentEntryService;
		this.customizedWindowInfoMapRepository = customizedWindowInfoMapRepository;
	}

	@Override
	public UserNotification save(@NonNull final UserNotificationRequest request)
	{
		final I_AD_Note notificationPO = InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_Note.class);
		notificationPO.setAD_User_ID(request.getRecipient().getUserId().getRepoId());
		notificationPO.setIsImportant(request.isImportant());

		//
		// contentADMessage -> AD_Message
		AdMessageId adMessageId = null;
		final AdMessageKey detailADMessage = request.getContentADMessage();
		if (detailADMessage != null)
		{
			adMessageId = Services.get(IMsgBL.class).getIdByAdMessage(detailADMessage).orElse(null);
		}
		if (adMessageId == null)
		{
			adMessageId = Services.get(IMsgBL.class).getIdByAdMessage(DEFAULT_AD_MESSAGE).orElse(null);
		}
		notificationPO.setAD_Message_ID(AdMessageId.toRepoId(adMessageId));

		//
		// contentADMessageParams
		final List<Object> detailADMessageParams = request.getContentADMessageParams();
		if (detailADMessageParams != null && !detailADMessageParams.isEmpty())
		{
			try
			{
				final String detailADMessageParamsJSON = jsonMapper.writeValueAsString(detailADMessageParams);
				notificationPO.setAD_Message_ParamsJSON(detailADMessageParamsJSON);
			}
			catch (final JsonProcessingException e)
			{
				throw new AdempiereException("Unable to create JSON from the given request's contentADMessageParams", e)
						.appendParametersToMessage()
						.setParameter("detailADMessageParams", detailADMessageParams)
						.setParameter("request", request);
			}
		}

		//
		// contentPlain
		final String detailPlain = request.getContentPlain();
		notificationPO.setTextMsg(detailPlain);

		//
		// Target action
		final TargetAction targetAction = request.getTargetAction();
		if (targetAction == null)
		{
			// no action
		}
		else if (targetAction instanceof TargetRecordAction)
		{
			final TargetRecordAction targetRecordAction = TargetRecordAction.cast(targetAction);
			final ITableRecordReference targetRecord = targetRecordAction.getRecord();
			notificationPO.setAD_Table_ID(targetRecord.getAD_Table_ID());
			notificationPO.setRecord_ID(targetRecord.getRecord_ID());
			notificationPO.setAD_Window_ID(targetRecordAction.getAdWindowId().map(AdWindowId::getRepoId).orElse(-1));
		}
		else if (targetAction instanceof TargetViewAction)
		{
			final TargetViewAction targetViewAction = TargetViewAction.cast(targetAction);
			notificationPO.setViewId(targetViewAction.getViewId());
			notificationPO.setAD_Window_ID(AdWindowId.toRepoId(targetViewAction.getAdWindowId()));
		}
		else
		{
			throw new AdempiereException("Unknown target action: " + targetAction + " (" + targetAction.getClass() + ")");
		}

		//
		InterfaceWrapperHelper.save(notificationPO);

		final List<Resource> attachments = request.getAttachments();
		if (!attachments.isEmpty())
		{
			attachmentEntryService.createNewAttachments(notificationPO, AttachmentEntryCreateRequest.fromResources(attachments));
		}

		return toUserNotification(notificationPO);
	}

	private UserNotification toUserNotificationNoFail(@NonNull final I_AD_Note notificationPO)
	{
		try
		{
			return toUserNotification(notificationPO);
		}
		catch (Exception ex)
		{
			logger.warn("Failed creating user notification object from {}", notificationPO, ex);
			return null;
		}
	}

	private UserNotification toUserNotification(@NonNull final I_AD_Note notificationPO)
	{
		final UserNotificationBuilder builder = UserNotification.builder()
				.id(notificationPO.getAD_Note_ID())
				.timestamp(TimeUtil.asInstant(notificationPO.getCreated()))
				.important(notificationPO.isImportant())
				.recipientUserId(notificationPO.getAD_User_ID())
				.read(notificationPO.isProcessed());

		//
		// detailADMessage
		final AdMessageId detailADMessageId = AdMessageId.ofRepoIdOrNull(notificationPO.getAD_Message_ID());
		if (detailADMessageId != null)
		{
			final AdMessageKey detailADMessage = Services.get(IMsgBL.class).getAdMessageKeyById(detailADMessageId).orElse(null);
			builder.detailADMessage(detailADMessage != null ? detailADMessage.toAD_Message() : null);
		}
		else
		{
			builder.detailADMessage(DEFAULT_AD_MESSAGE.toAD_Message());
		}

		//
		// detailADMessageParams
		final String detailADMessageParamsJSON = notificationPO.getAD_Message_ParamsJSON();
		if (!Check.isEmpty(detailADMessageParamsJSON, true))
		{
			try
			{
				@SuppressWarnings("unchecked")
				final List<Object> detailADMessageParams = jsonMapper.readValue(detailADMessageParamsJSON, List.class);
				builder.detailADMessageParams(detailADMessageParams);
			}
			catch (final Exception ex)
			{
				throw AdempiereException.wrapIfNeeded(ex)
						.setParameter("json", detailADMessageParamsJSON);
			}
		}

		//
		// detailPlain
		builder.detailPlain(notificationPO.getTextMsg());

		//
		// Target action
		final int adTableId = notificationPO.getAD_Table_ID();
		if (adTableId > 0)
		{
			builder.targetType(UserNotificationTargetType.Window)
					.targetRecord(TableRecordReference.of(adTableId, notificationPO.getRecord_ID()))
					.targetWindowId(extractAdWindowId(notificationPO));
		}
		else if (!Check.isEmpty(notificationPO.getViewId(), true))
		{
			builder.targetType(UserNotificationTargetType.View)
					.targetViewId(notificationPO.getViewId())
					.targetWindowId(extractAdWindowId(notificationPO));
		}
		else
		{
			builder.targetType(UserNotificationTargetType.None);
		}

		return builder.build();
	}

	@Nullable
	private AdWindowId extractAdWindowId(final I_AD_Note notificationPO)
	{
		AdWindowId adWindowId = AdWindowId.ofRepoIdOrNull(notificationPO.getAD_Window_ID());
		if(adWindowId == null)
		{
			return null;
		}

		return customizedWindowInfoMapRepository.get()
				.getCustomizedWindowInfo(adWindowId)
				.map(CustomizedWindowInfo::getCustomizationWindowId)
				.orElse(adWindowId);
	}

	private IQueryBuilder<I_AD_Note> retrieveNotesByUserId(@NonNull final UserId adUserId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Note.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Note.COLUMN_AD_User_ID, adUserId);
	}

	@Override
	public List<UserNotification> getByUserId(@NonNull final UserId adUserId, @NonNull final QueryLimit limit)
	{
		return retrieveNotesByUserId(adUserId)
				.orderByDescending(I_AD_Note.COLUMNNAME_AD_Note_ID)
				.setLimit(limit)
				.create()
				.stream(I_AD_Note.class)
				.map(this::toUserNotificationNoFail)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	public boolean markAsRead(@NonNull final UserNotification notification)
	{
		final boolean alreadyRead = notification.setRead(true);
		if (alreadyRead)
		{
			logger.debug("Skip marking notification as read because it's already read: {}", notification);
			return false;
		}

		return markAsReadById(notification.getId());
	}

	@Override
	public boolean markAsReadById(final int notificationId)
	{
		final I_AD_Note notificationPO = retrieveAD_Note(notificationId);
		if (notificationPO == null)
		{
			return false;
		}

		return markAsRead(notificationPO);
	}

	private boolean markAsRead(@NonNull final I_AD_Note notificationPO)
	{
		if (notificationPO.isProcessed())
		{
			return false;
		}

		if (!markAsReadNoSave(notificationPO))
		{
			return false;
		}

		InterfaceWrapperHelper.save(notificationPO);
		logger.debug("Marked notification read: {}", notificationPO);
		return true;
	}

	private boolean markAsReadNoSave(@NonNull final I_AD_Note notificationPO)
	{
		if (notificationPO.isProcessed())
		{
			return false;
		}
		notificationPO.setProcessed(true);
		return true;
	}

	@Override
	public void markAllAsReadByUserId(final UserId adUserId)
	{
		retrieveNotesByUserId(adUserId)
				.create()
				.update(this::markAsReadNoSave);
	}

	private I_AD_Note retrieveAD_Note(final int adNoteId)
	{
		Check.assumeGreaterThanZero(adNoteId, "adNoteId");
		return InterfaceWrapperHelper.loadOutOfTrx(adNoteId, I_AD_Note.class);
	}

	@Override
	public boolean deleteById(final int notificationId)
	{
		final I_AD_Note notificationPO = retrieveAD_Note(notificationId);
		if (notificationPO == null)
		{
			return false;
		}

		deleteNotification(notificationPO);
		return true;
	}

	@Override
	public void deleteAllByUserId(final UserId adUserId)
	{
		retrieveNotesByUserId(adUserId)
				.create()
				.list()
				.forEach(this::deleteNotification);
	}

	private void deleteNotification(final I_AD_Note notificationPO)
	{
		notificationPO.setProcessed(false);
		InterfaceWrapperHelper.delete(notificationPO);
	}

	@Override
	public int getUnreadCountByUserId(final UserId adUserId)
	{
		return retrieveNotesByUserId(adUserId)
				.addEqualsFilter(I_AD_Note.COLUMN_Processed, false)
				.create()
				.count();
	}

	@Override
	public int getTotalCountByUserId(final UserId adUserId)
	{
		return retrieveNotesByUserId(adUserId)
				.create()
				.count();
	}
}
