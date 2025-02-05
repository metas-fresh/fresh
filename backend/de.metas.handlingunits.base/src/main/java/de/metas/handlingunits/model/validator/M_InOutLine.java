package de.metas.handlingunits.model.validator;

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.IHUShipmentAssignmentBL;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.inout.IInOutBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.MovementType;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;

import javax.annotation.Nullable;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.save;

@Validator(I_M_InOutLine.class)
public class M_InOutLine
{
	// TODO: delete AD_Message:
	// private static final String MSG_CHANGE_MOVEMENT_QTY_NOT_SUPPORTED = "de.metas.inoutcandidate.modelvalidator.M_InOutLine_Shipment_ChangeMovementQtyNotSupported";

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	})
	public void updateEffectiveValues(final I_M_InOutLine inoutLine)
	{

		final I_M_InOut inOut = inoutLine.getM_InOut();

		final boolean isReturnType = Services.get(IInOutBL.class).isReturnMovementType(inOut.getMovementType());

		if (isReturnType)
		{
			// no nothing in case of returns
			return;
		}

		//
		// Shipment
		if (inoutLine.getM_InOut().isSOTrx())
		{
			final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
			huInOutBL.updateEffectiveValues(inoutLine);
		}
		//
		// Receipt
		else
		{
			// nothing
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void removeHUAssignments(final I_M_InOutLine inoutLine)
	{
		final I_M_InOut inout = inoutLine.getM_InOut();
		final MovementType movementType = MovementType.ofCode(inout.getMovementType());

		//
		// Shipment
		if (movementType.isOutboundTransaction())
		{
			// NOTE: instead of deleting the assignments, it's better to fail
			// because if we delete the assignment which is for an HU which is also assigned on other shipment,
			// that partial HU we will not be able to see it again (as a user).
			if (Services.get(IHUAssignmentDAO.class).hasHUAssignmentsForModel(inoutLine))
			{
				throw new HUException("Cannot delete an shipment line I_M_InOutLine_ID=" + inoutLine.getM_InOutLine_ID() + " because has HUs assigned to it");
			}
			Services.get(IHUShipmentAssignmentBL.class).removeHUAssignments(inoutLine);
		}
	}

	@ModelChange(//
			timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE, ModelValidator.TYPE_AFTER_NEW }, //
			ifColumnsChanged = { I_M_InOutLine.COLUMNNAME_MovementQty, I_M_InOutLine.COLUMNNAME_Catch_UOM_ID, I_M_InOutLine.COLUMNNAME_QtyDeliveredCatch })
	public void onMovementQtyChange(final I_M_InOutLine inOutLine)
	{
		// make sure we are dealing with shipments
		if (!inOutLine.getM_InOut().isSOTrx())
		{
			return;
		}

		final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
		final List<I_M_ShipmentSchedule_QtyPicked> allocs = shipmentScheduleAllocDAO.retrieveAllForInOutLine(
				inOutLine,
				I_M_ShipmentSchedule_QtyPicked.class);
		if (!allocs.isEmpty())
		{
			createOrUpdateShipmentScheduleQtyPicked(inOutLine, allocs);
		}

		final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL = Services.get(IShipmentScheduleInvalidateBL.class);
		shipmentScheduleInvalidateBL.flagForRecompute(inOutLine); // task 08749: the segment invalidation might not invalidate the sched(s) for this line
		shipmentScheduleInvalidateBL.notifySegmentChangedForShipmentLine(inOutLine);
	}

	private void createOrUpdateShipmentScheduleQtyPicked(
			@NonNull final I_M_InOutLine inOutLine,
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> allocs)
	{
		final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);

		final ProductId productId = ProductId.ofRepoId(inOutLine.getM_Product_ID());
		final UomId catchUomId = UomId.ofRepoIdOrNull(inOutLine.getCatch_UOM_ID());
		final StockQtyAndUOMQty qtyPickedSum = computeQtyPickedSum(productId, catchUomId, allocs);

		final StockQtyAndUOMQty shipmentLine_movementQty = StockQtyAndUOMQtys.create(inOutLine.getMovementQty(), productId, inOutLine.getQtyDeliveredCatch(), catchUomId);

		final StockQtyAndUOMQty qtyPickedToAdd = StockQtyAndUOMQtys.subtract(shipmentLine_movementQty, qtyPickedSum);
		if (qtyPickedToAdd.signum() == 0)
		{
			return;
		}

		// * find a M_ShipmentSchedule_QtyPicked record that has no HU, because there where we can add our difference
		I_M_ShipmentSchedule adjustments_shipmentSchedule = null;
		I_M_ShipmentSchedule_QtyPicked adjustments_alloc = null;
		for (final I_M_ShipmentSchedule_QtyPicked alloc : allocs)
		{
			// Select the allocation line on which we can do the adjustments
			adjustments_shipmentSchedule = alloc.getM_ShipmentSchedule();
			if (!huShipmentScheduleBL.isHUAllocation(alloc))
			{
				adjustments_alloc = alloc;
				break;
			}
		}

		if (adjustments_alloc != null)
		{
			final StockQtyAndUOMQty currentAllocQty = createStockQtyAndUomQtyFor(productId, adjustments_alloc);

			final StockQtyAndUOMQty newAllocQty = StockQtyAndUOMQtys.add(currentAllocQty, qtyPickedToAdd);

			// Case: we found a line where to add the difference
			adjustments_alloc.setQtyPicked(newAllocQty.getStockQty().toBigDecimal());
			if (newAllocQty.getUOMQtyOpt().isPresent())
			{
				adjustments_alloc.setCatch_UOM_ID(newAllocQty.getUOMQtyOpt().get().getUomId().getRepoId());
				adjustments_alloc.setQtyDeliveredCatch(newAllocQty.getUOMQtyOpt().get().toBigDecimal());
			}
			else
			{
				adjustments_alloc.setCatch_UOM_ID(0);
				adjustments_alloc.setQtyDeliveredCatch(null);
			}
			save(adjustments_alloc);
		}
		else
		{
			// Case: there is no line were we can add the difference, so we are creating one now
			final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);

			final de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked adjustments_allocNew = //
					shipmentScheduleAllocBL.createNewQtyPickedRecord(adjustments_shipmentSchedule, qtyPickedToAdd);

			adjustments_allocNew.setM_InOutLine(inOutLine);
			adjustments_allocNew.setProcessed(inOutLine.getM_InOut().isProcessed());
			save(adjustments_allocNew);
		}
	}

	private StockQtyAndUOMQty computeQtyPickedSum(
			@NonNull final ProductId productId,
			@Nullable final UomId uomId,
			@NonNull final List<I_M_ShipmentSchedule_QtyPicked> allocs)
	{
		StockQtyAndUOMQty result = StockQtyAndUOMQtys.createZero(productId, uomId);

		// * calculate how much qty was shipped for all shipment schedules (in total)
		for (final I_M_ShipmentSchedule_QtyPicked alloc : allocs)
		{
			final StockQtyAndUOMQty allocQty = createStockQtyAndUomQtyFor(productId, alloc);
			result = StockQtyAndUOMQtys.add(result, allocQty);
		}
		return result;
	}

	private StockQtyAndUOMQty createStockQtyAndUomQtyFor(
			@NonNull final ProductId productId,
			@NonNull final I_M_ShipmentSchedule_QtyPicked alloc)
	{
		return StockQtyAndUOMQtys.create(
				alloc.getQtyPicked(), productId,
				alloc.getQtyDeliveredCatch(), UomId.ofRepoIdOrNull(alloc.getCatch_UOM_ID()));
	}

}
