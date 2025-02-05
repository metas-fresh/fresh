package de.metas.handlingunits.inout.impl;

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

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.inout.IHUShipmentAssignmentBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.util.HUTopLevel;
import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.InSubQueryFilter;
import org.adempiere.ad.dao.impl.NotQueryFilter;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOut;

import java.util.List;
import java.util.Properties;

public class HUShipmentAssignmentBL implements IHUShipmentAssignmentBL
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

	@Override
	public final void assignHU(
			@NonNull final org.compiere.model.I_M_InOutLine shipmentLine,
			@NonNull final HUTopLevel huToAssign,
			final boolean isTransferPackingMaterials)
	{
		//
		// Create LU/TU assignment
		final Properties ctx = InterfaceWrapperHelper.getCtx(shipmentLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(shipmentLine);
		final I_M_HU topLevelHU = huToAssign.getM_HU_TopLevel();
		final I_M_HU luHU = huToAssign.getM_LU_HU();
		final I_M_HU tuHU = huToAssign.getM_TU_HU();
		final I_M_HU vhu = huToAssign.getVHU();

		huAssignmentBL.createTradingUnitDerivedAssignmentBuilder(ctx, shipmentLine, topLevelHU, luHU, tuHU, trxName)
				.setVHU(vhu)
				.setIsTransferPackingMaterials(isTransferPackingMaterials)
				.build();
	}

	private void assertShipment(@NonNull final I_M_InOut inout)
	{
		Check.assume(inout.isSOTrx(), "inout shall be a shipment: {}", inout);
	}

	@Override
	public void updateHUsOnShipmentComplete(final I_M_InOut shipment)
	{
		assertShipment(shipment);

		final List<I_M_InOutLine> shipmentLines = inOutDAO.retrieveLines(shipment, I_M_InOutLine.class);

		// Iterate each shipment line, get assigned HUs and change HUStatus to Shipped
		for (final I_M_InOutLine shipmentLine : shipmentLines)
		{
			updateHUsOnShipmentComplete(shipmentLine);
		}
	}

	private void updateHUsOnShipmentComplete(final I_M_InOutLine shipmentLine)
	{
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(shipmentLine);
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(contextProvider);
		final int locatorId = shipmentLine.getM_Locator_ID();

		final List<I_M_HU> hus = huAssignmentDAO.retrieveTopLevelHUsForModel(shipmentLine);
		for (final I_M_HU hu : hus)
		{
			// Make sure their are on the right locator
			hu.setM_Locator_ID(locatorId);

			// Change HU's status to Shipped
			final boolean shipped = true;
			setHUStatus(huContext, hu, shipped);
		}
	}

	@Override
	public void removeHUAssignments(final I_M_InOut shipment)
	{
		assertShipment(shipment);

		final List<I_M_InOutLine> shipmentLines = inOutDAO.retrieveLines(shipment, I_M_InOutLine.class);

		// Iterate each shipment line, get assigned HUs and change HUStatus to Shipped
		for (final I_M_InOutLine shipmentLine : shipmentLines)
		{
			removeHUAssignments(shipmentLine);
		}
	}

	@Override
	public void removeHUAssignments(@NonNull final I_M_InOutLine shipmentLine)
	{
		Check.assume(shipmentLine.getM_InOutLine_ID() > 0, "shipment line is saved");
		//
		// Remove HU Assignments and update HU Status
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(shipmentLine);
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(contextProvider);

		final List<I_M_HU> hus = huAssignmentDAO.retrieveTopLevelHUsForModel(shipmentLine);
		for (final I_M_HU hu : hus)
		{
			// Change HU's status to Picked
			final boolean shipped = false;
			setHUStatus(huContext, hu, shipped);
		}
		//
		huAssignmentBL.unassignAllHUs(shipmentLine);

		//
		// Unlink shipment line from shipment schedule allocations
		for (final I_M_ShipmentSchedule_QtyPicked alloc : shipmentScheduleAllocDAO.retrieveAllForInOutLine(shipmentLine, I_M_ShipmentSchedule_QtyPicked.class))
		{
			alloc.setM_InOutLine(null);
			alloc.setIsActive(false); // NOTE: deactivating the line because we assume this method was called when a shipment was voided/reversed.
			alloc.setDescription("Deactivated because the shipment line "
					+ shipmentLine
					+ " was voided or reversed. ");
			InterfaceWrapperHelper.save(alloc);
		}
	}

	@Override
	public IQueryFilter<I_M_HU> createHUsNotAssignedToShipmentsFilter(final IContextAware contextProvider)
	{
		final int inoutLineTableId = tableDAO.retrieveTableId(org.compiere.model.I_M_InOutLine.Table_Name);

		final IQuery<I_M_InOut> queryShipments = queryBL.createQueryBuilder(I_M_InOut.class, contextProvider)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_IsSOTrx, true)
				.create();

		final IQuery<I_M_InOutLine> queryShipmentLines = queryBL.createQueryBuilder(I_M_InOutLine.class, contextProvider)
				.addInSubQueryFilter(org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOut_ID, I_M_InOut.COLUMNNAME_M_InOut_ID, queryShipments)
				.create();

		final IQuery<I_M_HU_Assignment> queryHUAssignments = queryBL.createQueryBuilder(I_M_HU_Assignment.class, contextProvider)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, inoutLineTableId)
				.addInSubQueryFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, queryShipmentLines)
				.create();

		final IQueryFilter<I_M_HU> assignedQueryFilter = InSubQueryFilter.of(I_M_HU.COLUMN_M_HU_ID, I_M_HU_Assignment.COLUMNNAME_M_HU_ID, queryHUAssignments);

		return NotQueryFilter.of(assignedQueryFilter);
	}

	private void setHUStatus(final IHUContext huContext, final I_M_HU hu, final boolean shipped)
	{
		//
		// HU was shipped
		if (shipped)
		{
			// Change HU's status to Shipped

			// task 07617: Note: the HU context is not needed here, because the status Shipped
			// does not trigger a movement to/from the gebindelager
			// Calling this method with huContext null
			// To Be Changed in case the requirements change

			huStatusBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Shipped);
			hu.setIsActive(false); // deactivate it because it shall not be available in our system anymore
		}

		//
		// HU was not shipped (i.e. it was shipped before shipment was reversed)
		else
		{
			huStatusBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Active);
			hu.setIsActive(true);

			final I_M_Locator locator = InterfaceWrapperHelper.create(warehouseBL.getLocatorByRepoId(hu.getM_Locator_ID()), I_M_Locator.class);

			if (locator.isAfterPickingLocator())
			{
				final WarehouseId warehouseId = WarehouseId.ofRepoId(locator.getM_Warehouse_ID());

				// Restore default locator
				hu.setM_Locator_ID(warehouseBL.getOrCreateDefaultLocatorId(warehouseId).getRepoId());
			}
		}

		handlingUnitsDAO.saveHU(hu);
	}

	@NonNull
	public List<I_M_ShipmentSchedule_QtyPicked> retrieveAssignedQuantities(@NonNull final I_M_InOut shipment)
	{
		final List<I_M_InOutLine> lines = inOutDAO.retrieveLines(shipment, I_M_InOutLine.class);
		return lines
				.stream()
				.map(line -> shipmentScheduleAllocDAO.retrieveAllForInOutLine(line, I_M_ShipmentSchedule_QtyPicked.class))
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList());
	}
}
