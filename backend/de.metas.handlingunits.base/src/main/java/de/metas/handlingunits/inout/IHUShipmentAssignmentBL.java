package de.metas.handlingunits.inout;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.util.HUTopLevel;
import de.metas.util.ISingletonService;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import java.util.List;

/**
 * Service used to manage the relation between {@link I_M_HU} and shipment line.
 * <p>
 * NOTE: please always use this service instead of bypassing it and assign HUs directly for example.
 *
 * @author tsa
 */
public interface IHUShipmentAssignmentBL extends ISingletonService
{
	/**
	 * Assign given LU/TU/VHU to shipment line
	 *
	 * @param shipmentLine
	 * @param huToAssign
	 * @param isTransferPackingMaterials
	 */
	void assignHU(I_M_InOutLine shipmentLine, HUTopLevel huToAssign, boolean isTransferPackingMaterials);

	/**
	 * Update HUStatus (i.e. it is setting it to Shipped) for assigned HUs when shipment is completed.
	 *
	 * @param shipment
	 */
	void updateHUsOnShipmentComplete(I_M_InOut shipment);

	/**
	 * Update HUStatus (i.e. it is setting it to Picked) for assigned HUs when shipment is re-activated.
	 *
	 * @param shipment
	 */
	void updateHUsOnShipmentReactivate(I_M_InOut shipment);

	/**
	 * Remove HU Assignments for given Shipment.
	 * <p>
	 * Also, this method restores HU's HUStatus (i.e. it is setting it to Picked).
	 *
	 * @param shipment
	 */
	void removeHUAssignments(I_M_InOut shipment);

	/**
	 * Remove HU Assignments for given Shipment Line.
	 * <p>
	 * Also, this method restores HU's HUStatus (i.e. it is setting it to Picked).
	 *
	 * @param shipmentLine
	 */
	void removeHUAssignments(de.metas.handlingunits.model.I_M_InOutLine shipmentLine);

	IQueryFilter<I_M_HU> createHUsNotAssignedToShipmentsFilter(IContextAware contextProvider);

	List<I_M_ShipmentSchedule_QtyPicked> retrieveAssignedQuantities(I_M_InOut shipment);
}
