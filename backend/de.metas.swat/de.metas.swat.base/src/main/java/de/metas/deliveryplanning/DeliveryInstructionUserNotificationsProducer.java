/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.deliveryplanning;

import com.google.common.collect.ImmutableList;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;

import java.util.Collection;
import java.util.List;

public class DeliveryInstructionUserNotificationsProducer
{
	public static final DeliveryInstructionUserNotificationsProducer newInstance()
	{
		return new DeliveryInstructionUserNotificationsProducer();
	}

	/**
	 * Topic used to send notifications about shipments/receipts that were generated/reversed asynchronously
	 */
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.deliveryInstruction.UserNotifications")
			.type(Type.REMOTE)
			.build();

	private static final AdWindowId WINDOW_DELIVERY_INSTRUCTION = AdWindowId.ofRepoId(541657); // FIXME: HARDCODED
	private static final AdMessageKey MSG_Event_DeliveryInstructionGenerated = AdMessageKey.of("de.metas.deliveryplanning.Event_DeliveryInstructionGenerated");

	private DeliveryInstructionUserNotificationsProducer()
	{
	}

	public DeliveryInstructionUserNotificationsProducer notifyGenerated(final Collection<I_M_ShipperTransportation> deliveryInstructions)
	{
		if (deliveryInstructions == null || deliveryInstructions.isEmpty())
		{
			return this;
		}

		postNotifications(deliveryInstructions.stream()
								  .map(this::createUserNotification)
								  .collect(ImmutableList.toImmutableList()));

		return this;
	}

	public final DeliveryInstructionUserNotificationsProducer notifyGenerated(@NonNull final I_M_ShipperTransportation deliveryInstruction)
	{
		notifyGenerated(ImmutableList.of(deliveryInstruction));
		return this;
	}

	private final UserNotificationRequest createUserNotification(@NonNull final I_M_ShipperTransportation deliveryInstruction)
	{
		final TableRecordReference deliveryInstructionRef = toTableRecordRef(deliveryInstruction);

		return newUserNotificationRequest()
				.recipientUserId(Env.getLoggedUserId())
				.contentADMessage(MSG_Event_DeliveryInstructionGenerated)
				.contentADMessageParam(deliveryInstructionRef)
				.targetAction(UserNotificationRequest.TargetRecordAction.ofRecordAndWindow(deliveryInstructionRef, WINDOW_DELIVERY_INSTRUCTION))
				.build();

	}

	private static TableRecordReference toTableRecordRef(@NonNull final I_M_ShipperTransportation deliveryInstruction)
	{
		return TableRecordReference.of(I_M_ShipperTransportation.Table_Name, deliveryInstruction.getM_ShipperTransportation_ID());
	}

	private final UserNotificationRequest.UserNotificationRequestBuilder newUserNotificationRequest()
	{
		return UserNotificationRequest.builder()
				.topic(EVENTBUS_TOPIC);
	}

	private void postNotifications(final List<UserNotificationRequest> notifications)
	{
		Services.get(INotificationBL.class).sendAfterCommit(notifications);
	}

}
