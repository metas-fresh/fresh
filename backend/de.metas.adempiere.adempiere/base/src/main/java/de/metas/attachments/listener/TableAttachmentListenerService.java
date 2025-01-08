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

package de.metas.attachments.listener;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Table_AttachmentListener;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.listener.AttachmentListenerConstants.ListenerWorkStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.javaclasses.IJavaClassBL;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.util.Services;
import lombok.NonNull;

@Service
public class TableAttachmentListenerService
{
	private static final Logger logger = LogManager.getLogger(TableAttachmentListenerService.class);

	private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	private final IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final TableAttachmentListenerRepository tableAttachmentListenerRepository;

	public TableAttachmentListenerService(@NonNull final TableAttachmentListenerRepository tableAttachmentListenerRepository)
	{
		this.tableAttachmentListenerRepository = tableAttachmentListenerRepository;
	}

	public ImmutableList<AttachmentListenerActionResult> fireAfterRecordLinked(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final TableRecordReference tableRecordReference)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(tableRecordReference))
		{
			final ImmutableList<AttachmentListenerSettings> settings = tableAttachmentListenerRepository.getById(tableRecordReference.getAdTableId());
			logger.debug("There are {} AttachmentListenerSettings for AD_Table_ID={}", settings.size(), tableRecordReference.getAD_Table_ID());

			ImmutableList.Builder<AttachmentListenerActionResult> results = ImmutableList.builder();
			for (final AttachmentListenerSettings setting : settings)
			{
				final AttachmentListenerActionResult result = invokeListener(setting, tableRecordReference, attachmentEntry);
				results.add(result);
			}

			return results.build();
		}
	}

	private AttachmentListenerActionResult invokeListener(
			@NonNull final AttachmentListenerSettings listenerSettings,
			@NonNull final TableRecordReference tableRecordReference,
			@NonNull final AttachmentEntry attachmentEntry)
	{
		final AttachmentListener attachmentListener = javaClassBL.newInstance(listenerSettings.getListenerJavaClassId());

		try (final MDCCloseable mdc = MDC.putCloseable("attachmentListener", attachmentListener.getClass().getSimpleName()))
		{
			final ListenerWorkStatus status = attachmentListener.afterRecordLinked(attachmentEntry, tableRecordReference);
			logger.debug("attachmentListener returned status={}", status);
			if (!status.equals(ListenerWorkStatus.NOT_APPLIED))
			{
				notifyUser(listenerSettings, tableRecordReference, status);
			}
			return new AttachmentListenerActionResult(attachmentListener, status, tableRecordReference);
		}
	}

	/**
	 * Notifies the user about the process finalizing work if {@link AttachmentListenerSettings#isSendNotification()}
	 *
	 * @param attachmentListenerSettings data from {@link I_AD_Table_AttachmentListener}
	 * @param tableRecordReference       reference of the table
	 */
	@VisibleForTesting
	void notifyUser(
			@NonNull final AttachmentListenerSettings attachmentListenerSettings,
			@NonNull final TableRecordReference tableRecordReference,
			@NonNull final ListenerWorkStatus listenerWorkStatus)
	{
		if (attachmentListenerSettings.isSendNotification())
		{
			final AdMessageKey adMessageContent = msgBL.getAdMessageKeyById(attachmentListenerSettings.getAdMessageId()).orElse(null);

			final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
					.contentADMessage(adMessageContent != null ? adMessageContent : null)
					.contentADMessageParam(listenerWorkStatus.getValue())
					.recipientUserId(Env.getLoggedUserId())
					.targetAction(UserNotificationRequest.TargetRecordAction.of(tableRecordReference))
					.build();
			notificationBL.send(userNotificationRequest);
		}
	}
<<<<<<< HEAD
=======

	private void invokeBeforeRecordLinked(
			@NonNull final AttachmentListenerSettings listenerSettings,
			@NonNull final TableRecordReference tableRecordReference,
			@NonNull final AttachmentEntry attachmentEntry)
	{
		final AttachmentListener attachmentListener = javaClassBL.newInstance(listenerSettings.getListenerJavaClassId());

		try (final MDCCloseable mdc = MDC.putCloseable("attachmentListener", attachmentListener.getClass().getSimpleName()))
		{
			final ListenerWorkStatus status = attachmentListener.beforeRecordLinked(attachmentEntry, tableRecordReference);
			logger.debug("attachmentListener returned status={}", status);

			if (status.equals(ListenerWorkStatus.FAILURE))
			{
				throw new AdempiereException(retrieveFailureMessage(listenerSettings))
						.markAsUserValidationError();
			}
		}
	}

	@NonNull
	private AdMessageKey retrieveFailureMessage(@NonNull final AttachmentListenerSettings listenerSettings)
	{
		return msgBL.getAdMessageKeyById(listenerSettings.getAdMessageId()).orElse(Msg_AttachmentNotImportedFAILURE);
	}
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
}
