package de.metas.handlingunits.model.validator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUPackageBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.document.IHUDocumentFactoryService;
import de.metas.handlingunits.empties.IHUEmptiesService;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.inout.IHUShipmentAssignmentBL;
import de.metas.handlingunits.inout.impl.MInOutHUDocumentFactory;
import de.metas.handlingunits.inout.impl.ReceiptInOutLineHUAssignmentListener;
import de.metas.handlingunits.inout.returns.ReturnsServiceFacade;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.job.service.HUWithPickOnTheFlyStatus;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.ReopenPickingJobRequest;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.util.HUByIdComparator;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.ShipmentScheduleId;
import de.metas.material.MovementType;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Interceptor(I_M_InOut.class)
@Component
public class M_InOut
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IHUShipmentAssignmentBL huShipmentAssignmentBL = Services.get(IHUShipmentAssignmentBL.class);
	private final IHUInOutDAO inOutDAO = Services.get(IHUInOutDAO.class);
	private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final IHUEmptiesService huEmptiesService = Services.get(IHUEmptiesService.class);
	private final IHUPackageBL huPackageBL = Services.get(IHUPackageBL.class);
	private final IInOutDAO inoutDao = Services.get(IInOutDAO.class);
	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final IHUSnapshotDAO snapshotDAO = Services.get(IHUSnapshotDAO.class);
	private final ReturnsServiceFacade returnsServiceFacade;
	private final PickingJobService pickingJobService;

	public M_InOut(
			@NonNull final ReturnsServiceFacade returnsServiceFacade,
			@NonNull final PickingJobService pickingJobService)
	{
		this.returnsServiceFacade = returnsServiceFacade;
		this.pickingJobService = pickingJobService;
	}

	@Init
	public void init()
	{
		Services.get(IHUDocumentFactoryService.class)
				.registerHUDocumentFactory(I_M_InOut.Table_Name, new MInOutHUDocumentFactory());

		Services.get(IHUAssignmentBL.class)
				.registerHUAssignmentListener(ReceiptInOutLineHUAssignmentListener.instance);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_REVERSEACCRUAL })
	public void destroyHandlingUnitsForReceipt(final I_M_InOut inout)
	{
		final MovementType movementType = MovementType.ofCode(inout.getMovementType());
		if(!movementType.isOutboundTransaction())
		{
			// the incoming HU created from this M_InOut needs to be destroyed
			huInOutBL.copyAssignmentsToReversal(inout);
			huInOutBL.destroyHUs(inout);
		}
	}

	/**
	 * Generates additional shipment lines for the packaging materials of currently assigned HUs.
	 *
	 * @see IHUInOutBL#createPackingMaterialLines(I_M_InOut)
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_PREPARE })
	public void addPackingMaterialLinesForShipment(final I_M_InOut shipment)
	{
		final MovementType movementType = MovementType.ofCode(shipment.getMovementType());
		if (movementType.isOutboundTransaction() && shipment.isSOTrx())
		{
			huInOutBL.createPackingMaterialLines(shipment);
		}
		else
		{
			// nothing to do in case of receipts because packing materials lines are already created
			// see de.metas.inoutcandidate.spi.impl.InOutProducerFromReceiptScheduleHU.createPackingMaterialReceiptLine(HUPackingMaterialDocumentLineCandidate)
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(@NonNull final I_M_InOut shipment)
	{
		setHUStatusShippedForShipment(shipment);
		emptyPickingSlots(shipment);
		generateEmptiesMovementForEmptiesInOut(shipment);
		updateAttributes(shipment);
	}

	private void updateAttributes(@NonNull final I_M_InOut shipment)
	{
		// Make sure we deal with a shipment
		if (!shipment.isSOTrx())
		{
			return;
		}

		// make sure we are not dealing with a customer return
		if (returnsServiceFacade.isCustomerReturn(shipment))
		{
			return;
		}

		final List<I_M_HU> hus = inOutDAO.retrieveHandlingUnits(shipment);
		for (final I_M_HU hu : hus)
		{
			huAttributesBL.updateHUAttributeRecursive(HuId.ofRepoId(hu.getM_HU_ID()), AttributeConstants.WarrantyStartDate, shipment.getMovementDate(), null);
		}
	}

	private void setHUStatusShippedForShipment(final I_M_InOut shipment)
	{
		// Make sure we deal with a shipment
		if (!shipment.isSOTrx())
		{
			return;
		}

		// make sure we are not dealing with a customer return
		if (returnsServiceFacade.isCustomerReturn(shipment))
		{
			return;
		}

		huShipmentAssignmentBL.updateHUsOnShipmentComplete(shipment);
	}

	private void emptyPickingSlots(final I_M_InOut shipment)
	{
		// Make sure we deal with a shipment
		if (!shipment.isSOTrx())
		{
			return;
		}

		//
		// Retrieve all HUs which we need to remove from their picking slots
		final Set<I_M_HU> husToRemove = new TreeSet<>(HUByIdComparator.instance);

		final List<I_M_HU> handlingUnits = inOutDAO.retrieveHandlingUnits(shipment);

		husToRemove.addAll(handlingUnits);

		//
		// Remove collected HUs from their picking slots
		for (final I_M_HU hu : husToRemove)
		{
			huPickingSlotBL.removeFromPickingSlotQueueRecursivelly(hu);
		}
	}

	private void generateEmptiesMovementForEmptiesInOut(final I_M_InOut inout)
	{
		// do nothing if completing the reversal document
		if (inout.getReversal_ID() > 0)
		{
			return;
		}

		// task #1306: Do not generate empties movements for customer returns
		if (returnsServiceFacade.isCustomerReturn(inout))
		{
			return;
		}

		// do nothing if this is not an empties shipment/receipt
		if (!huEmptiesService.isEmptiesInOut(inout))
		{
			return;
		}

		// Actually generate the empties movements
		huEmptiesService.generateMovementFromEmptiesInout(inout);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_VOID, ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void removeHUAssignmentsForShipment(final I_M_InOut shipment)
	{
		final MovementType movementType = MovementType.ofCode(shipment.getMovementType());
		
		// Make sure we deal with a shipment (and not a customer-return)
		final boolean isShipment = shipment.isSOTrx() && movementType.isOutboundTransaction();
		if (!isShipment)
		{
			return;
		}

		final List<I_M_ShipmentSchedule_QtyPicked> assignedQuantities =
				huShipmentAssignmentBL.retrieveAssignedQuantities(shipment);
		//
		// Remove all HU Assignments
		huShipmentAssignmentBL.removeHUAssignments(shipment);

		//
		// Unassign shipment from M_Packages (if any)
		huPackageBL.unassignShipmentFromPackages(shipment);

		final Set<ShipmentScheduleId> allShipmentSchedulesInvolved = assignedQuantities.stream()
				.filter(qtyPicked -> qtyPicked.getVHU_ID() > 0)
				.map(I_M_ShipmentSchedule_QtyPicked::getM_ShipmentSchedule_ID)
				.map(ShipmentScheduleId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
		final List<HUWithPickOnTheFlyStatus> allHusInvolved = assignedQuantities.stream()
				.filter(qtyPicked -> qtyPicked.getVHU_ID() > 0)
				.map(qtyPicked -> HUWithPickOnTheFlyStatus.builder()
						.anonymousHuPickedOnTheFly(qtyPicked.isAnonymousHuPickedOnTheFly())
						.huId(HuId.ofRepoId(qtyPicked.getVHU_ID()))
						.build())
				.collect(ImmutableList.toImmutableList());

		final ReopenPickingJobRequest request = ReopenPickingJobRequest.builder()
				.shipmentScheduleIds(allShipmentSchedulesInvolved)
				.huInfoList(allHusInvolved)
				.build();

		pickingJobService.reopenPickingJobs(request);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void assertReActivationAllowed(final I_M_InOut inout)
	{
		//
		// Services

		final boolean hasHUAssignments = huAssignmentDAO.hasHUAssignmentsForModel(inout);
		if (!hasHUAssignments) // reactivation is allowed if there are no HU assignments
		{
			return;
		}

		//
		// Shipment
		if (inout.isSOTrx())
		{
			// TODO: change HUStatus from Shipped back to Picked
			// check the calls of de.metas.handlingunits.inout.impl.HUShipmentAssignmentBL.setHUStatus(IHUContext, I_M_HU, boolean)
		}
		//
		// Receipt
		else
		{
			// TODO: destroy the HUs
			throw new UnsupportedOperationException();
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void unassignShipmentFromMPackages(final I_M_InOut shipment)
	{
		// Make sure we are dealing with a shipment
		if (!shipment.isSOTrx())
		{
			return;
		}

		huPackageBL.unassignShipmentFromPackages(shipment);
	}

	/**
	 * Note: the reverse-timings are only fired on the M_InOut that is actually reversed (and not on the reversal).
	 * <p>
	 * @implSpec  <a href="http://dewiki908/mediawiki/index.php/09592_Rechnung_Gebinde_und_Packvorschrift_Detail_falsch_%28105577823398%29">issue</a>
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_REVERSEACCRUAL })
	public void updateReversedQtys(final I_M_InOut inout)
	{

		final I_M_InOut reversal = inout.getReversal();

		// shall never happen
		if (reversal == null)
		{
			return;
		}

		final List<I_M_InOutLine> reversalLines = inoutDao.retrieveLines(reversal, I_M_InOutLine.class);
		for (final I_M_InOutLine reversalLine : reversalLines)
		{
			final BigDecimal qtyTuReversed = reversalLine.getQtyEnteredTU().negate();

			reversalLine.setQtyTU_Override(qtyTuReversed);
			reversalLine.setQtyEnteredTU(qtyTuReversed);

			InterfaceWrapperHelper.save(reversalLine);
		}

	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void generateHUsForCustomerReturn(final I_M_InOut customerReturn)
	{
		if (!returnsServiceFacade.isCustomerReturn(customerReturn))
		{
			return; // do nothing if the inout is not a customer return
		}

		if (inOutBL.isReversal(customerReturn))
		{
			return; // nothing to do
		}

		if (returnsServiceFacade.isEmptiesReturn(customerReturn))
		{
			return; // no HUs to generate if the whole InOut is about HUs
		}

		final List<I_M_HU> assignedHUs = inOutDAO.retrieveHandlingUnits(customerReturn);
		if (assignedHUs.isEmpty())
		{
			throw new AdempiereException("No HUs to return assigned");
		}

		// make sure all assigned HUs are active
		handlingUnitsBL.setHUStatus(assignedHUs, X_M_HU.HUSTATUS_Active);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void reverseReturn(final de.metas.handlingunits.model.I_M_InOut returnInOut)
	{
		if (!(returnsServiceFacade.isVendorReturn(returnInOut) || returnsServiceFacade.isCustomerReturn(returnInOut)))
		{
			return; // nothing to do
		}

		final String snapshotId = returnInOut.getSnapshot_UUID();
		if (Check.isEmpty(snapshotId, true))
		{
			throw new HUException("@NotFound@ @Snapshot_UUID@ (" + returnInOut + ")");
		}

		final List<I_M_HU> hus = huAssignmentDAO.retrieveTopLevelHUsForModel(returnInOut);

		if (hus.isEmpty())
		{
			// nothing to do.
			return;
		}

		if (returnsServiceFacade.isCustomerReturn(returnInOut))
		{
			huMovementBL.moveHUsToWarehouse(hus, WarehouseId.ofRepoId(returnInOut.getM_Warehouse_ID()));
		}

		final IContextAware context = InterfaceWrapperHelper.getContextAware(returnInOut);
		snapshotDAO.restoreHUs()
				.setContext(context)
				.setSnapshotId(snapshotId)
				.setDateTrx(returnInOut.getMovementDate())
				.setReferencedModel(returnInOut)
				.addModels(hus)
				.restoreFromSnapshot();

	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void validateAttributesOnShipmentCompletion(final I_M_InOut shipment)
	{
		if (!shipment.isSOTrx())
		{
			// nothing to do
			return;
		}

		huInOutBL.validateMandatoryOnShipmentAttributes(shipment);
	}

}
