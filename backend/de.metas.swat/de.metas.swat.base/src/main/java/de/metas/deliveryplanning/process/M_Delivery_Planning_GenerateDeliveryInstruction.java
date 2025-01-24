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

package de.metas.deliveryplanning.process;

import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Delivery_Planning;

public class M_Delivery_Planning_GenerateDeliveryInstruction extends JavaProcess implements IProcessPrecondition
{	private final DeliveryPlanningService deliveryPlanningService = SpringContextHolder.instance.getBean(DeliveryPlanningService.class);

	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter = context.getQueryFilter(I_M_Delivery_Planning.class);

		final boolean isExistsNoShipperDeliveryPlannings = deliveryPlanningService.isExistsNoShipperDeliveryPlannings(selectedDeliveryPlanningsFilter);

		if (isExistsNoShipperDeliveryPlannings)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(DeliveryPlanningService.MSG_M_Delivery_Planning_NoForwarder));
		}
		final boolean isExistsOpenDeliveryPlannings = deliveryPlanningService.isExistsOpenDeliveryPlannings(selectedDeliveryPlanningsFilter);

		if (!isExistsOpenDeliveryPlannings)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(DeliveryPlanningService.MSG_M_Delivery_Planning_AllClosed));
		}
		final boolean isExistDeliveryPlanningsWithoutReleaseNo = deliveryPlanningService.isExistDeliveryPlanningsWithoutReleaseNo(selectedDeliveryPlanningsFilter);

		if (!isExistDeliveryPlanningsWithoutReleaseNo)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(DeliveryPlanningService.MSG_M_Delivery_Planning_AllHaveReleaseNo));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_M_Delivery_Planning> selectedDeliveryPlanningsFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		deliveryPlanningService.generateDeliveryInstructions(selectedDeliveryPlanningsFilter);
		// set of M_Delivery_Plannining IDs
		// document collection, invalidate for each ID

		return MSG_OK;

	}
}
