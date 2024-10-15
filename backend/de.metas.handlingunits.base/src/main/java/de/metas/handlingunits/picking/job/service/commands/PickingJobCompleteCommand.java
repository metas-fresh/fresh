package de.metas.handlingunits.picking.job.service.commands;

import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.handlingunits.picking.job.service.PickingJobHUReservationService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.shipmentschedule.api.GenerateShipmentsForSchedulesRequest;
import de.metas.handlingunits.shipmentschedule.api.IShipmentService;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

import static de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse.TYPE_PICKED_QTY;

public class PickingJobCompleteCommand
{
	private final static AdMessageKey PICKING_ON_ALL_STEPS_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG");
	private final static AdMessageKey ALL_STEPS_SHALL_BE_PICKED_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.job.service.commands.ALL_STEPS_SHALL_BE_PICKED_ERROR_MSG");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PickingJobService pickingJobService;
	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final PickingJobLockService pickingJobLockService;
	@NonNull private final PickingJobSlotService pickingSlotService;
	@NonNull private final PickingJobHUReservationService pickingJobHUReservationService;
	@NonNull private final IShipmentService shipmentService;

	@NonNull private final PickingJob initialPickingJob;
	@NonNull private final CreateShipmentPolicy createShipmentPolicy;

	@Builder
	private PickingJobCompleteCommand(
			final @NonNull PickingJobService pickingJobService,
			final @NonNull PickingJobRepository pickingJobRepository,
			final @NonNull PickingJobLockService pickingJobLockService,
			final @NonNull PickingJobSlotService pickingSlotService,
			final @NonNull PickingJobHUReservationService pickingJobHUReservationService,
			final @NonNull IShipmentService shipmentService,
			//
			final @NonNull PickingJob pickingJob,
			final @Nullable CreateShipmentPolicy createShipmentPolicy)
	{
		this.pickingJobService = pickingJobService;
		this.pickingJobRepository = pickingJobRepository;
		this.pickingJobLockService = pickingJobLockService;
		this.pickingSlotService = pickingSlotService;
		this.pickingJobHUReservationService = pickingJobHUReservationService;
		this.shipmentService = shipmentService;

		this.initialPickingJob = pickingJob;
		this.createShipmentPolicy = createShipmentPolicy != null ? createShipmentPolicy : CreateShipmentPolicy.DO_NOT_CREATE;
	}

	public static class PickingJobCompleteCommandBuilder
	{
		public PickingJob execute() {return build().execute();}
	}

	public PickingJob execute()
	{
		initialPickingJob.assertNotProcessed();
		if (!initialPickingJob.getProgress().isDone())
		{
			throw new AdempiereException(PICKING_ON_ALL_STEPS_ERROR_MSG);
		}

		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private PickingJob executeInTrx()
	{
		if (!initialPickingJob.getProgress().isDone())
		{
			throw new AdempiereException(ALL_STEPS_SHALL_BE_PICKED_ERROR_MSG);
		}

		PickingJob pickingJob = initialPickingJob.withDocStatus(PickingJobDocStatus.Completed);
		pickingJobRepository.save(pickingJob);

		pickingJob = pickingJobService.closeLUPickTarget(pickingJob);

		final PickingJobId pickingJobId = pickingJob.getId();
		pickingJob.getPickingSlotId()
				.ifPresent(pickingSlotId -> pickingSlotService.release(pickingSlotId, pickingJobId));

		pickingJobHUReservationService.releaseAllReservations(pickingJob);

		pickingJobLockService.unlockShipmentSchedules(pickingJob);

		createShipmentIfNeeded(pickingJob);

		return pickingJob;
	}

	private void createShipmentIfNeeded(final PickingJob pickingJob)
	{
		if (createShipmentPolicy.isCreateShipment())
		{
			shipmentService.generateShipmentsForScheduleIds(GenerateShipmentsForSchedulesRequest.builder()
					.scheduleIds(pickingJob.getShipmentScheduleIds())
					.quantityTypeToUse(TYPE_PICKED_QTY)
					.onTheFlyPickToPackingInstructions(true)
					.isCompleteShipment(createShipmentPolicy.isCreateAndCompleteShipment())
					.isCloseShipmentSchedules(createShipmentPolicy.isCloseShipmentSchedules())
					// since we are not going to immediately create invoices, we want to move on and to wait for shipments
					.waitForShipments(false)
					.build());
		}
	}
}
