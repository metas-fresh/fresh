/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobStep;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickFrom;
import de.metas.handlingunits.picking.job.model.PickingJobStepPickedTo;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job.service.HUWithPickOnTheFlyStatus;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.util.CatchWeightHelper;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUFactory;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUSupportingServices;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Value
public class PickingJobReopenCommand
{
	@NonNull ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull PickingJobRepository pickingJobRepository;
	@NonNull PickingJobSlotService pickingSlotService;
	@NonNull IHUShipmentScheduleBL huShipmentScheduleBL;
	@NonNull IShipmentScheduleBL shipmentScheduleBL;
	@NonNull IHandlingUnitsBL handlingUnitsBL;
	@NonNull IHUContextFactory huContextFactory;
	@NonNull PickingJob jobToReopen;
	@NonNull Map<HuId, HUWithPickOnTheFlyStatus> huIdsToPick;

	@Builder
	public PickingJobReopenCommand(
			@NonNull final PickingJobRepository pickingJobRepository,
			@NonNull final PickingJobSlotService pickingSlotService,
			@NonNull final IHUShipmentScheduleBL huShipmentScheduleBL,
			@NonNull final IShipmentScheduleBL shipmentScheduleBL,
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final IHUContextFactory huContextFactory,
			@NonNull final PickingJob jobToReopen,
			@NonNull final List<HUWithPickOnTheFlyStatus> huIdsToPick)
	{
		this.pickingJobRepository = pickingJobRepository;
		this.pickingSlotService = pickingSlotService;
		this.huShipmentScheduleBL = huShipmentScheduleBL;
		this.shipmentScheduleBL = shipmentScheduleBL;
		this.handlingUnitsBL = handlingUnitsBL;
		this.huContextFactory = huContextFactory;
		this.jobToReopen = jobToReopen;
		this.huIdsToPick = huIdsToPick.stream()
				.collect(ImmutableMap.toImmutableMap(HUWithPickOnTheFlyStatus::getHuId, Function.identity()));
	}

	public void execute()
	{
		trxManager.runInThreadInheritedTrx(this::reopenPickingJob);
	}

	private void reopenPickingJob()
	{
		if (!jobToReopen.getDocStatus().isCompleted())
		{
			return;
		}

		final PickingJob reopenedJob = jobToReopen
				.withDocStatus(PickingJobDocStatus.Drafted)
				.withLockedBy(null);

		pickingJobRepository.save(reservePickingSlotIfPossible(reopenedJob));

		jobToReopen.getLines().forEach(this::reactivateLine);
	}

	@NonNull
	private PickingJob reservePickingSlotIfPossible(@NonNull final PickingJob pickingJob)
	{
		final PickingSlotId slotId = pickingJob.getPickingSlotId()
				.orElse(null);

		if (slotId == null)
		{
			return pickingJob;
		}

		return PickingJobAllocatePickingSlotCommand.builder()
				.pickingJobRepository(pickingJobRepository)
				.pickingSlotService(pickingSlotService)
				.pickingJob(pickingJob.withPickingSlot(null))
				.pickingSlotId(slotId)
				.failIfNotAllocated(false)
				.build()
				.execute();
	}

	private void reactivateLine(@NonNull final PickingJobLine line)
	{
		line.getSteps().forEach(this::reactivateStepIfNeeded);
	}

	private void reactivateStepIfNeeded(@NonNull final PickingJobStep step)
	{
		final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing();
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleBL.getById(step.getShipmentScheduleId());

		final ShipmentScheduleWithHUFactory shipmentScheduleWithHUFactory = ShipmentScheduleWithHUFactory.builder()
				.supportingServices(ShipmentScheduleWithHUSupportingServices.getInstance())
				.huContext(huContext)
				.build();

		step.getPickFroms().getKeys()
				.stream()
				.map(key -> step.getPickFroms().getPickFrom(key))
				.map(PickingJobStepPickFrom::getPickedTo)
				.filter(pickedTo -> pickedTo != null)
				.map(PickingJobStepPickedTo::getActualPickedHUs)
				.flatMap(List::stream)
				.filter(pickStepHu -> huIdsToPick.containsKey(pickStepHu.getActualPickedHU().getId()))
				.forEach(pickStepHU -> {
					final I_M_HU hu = handlingUnitsBL.getById(pickStepHU.getActualPickedHU().getId());
					huShipmentScheduleBL.addQtyPickedAndUpdateHU(
							shipmentSchedule,
							CatchWeightHelper.extractQtys(
									huContext,
									step.getProductId(),
									pickStepHU.getQtyPicked(),
									hu),
							hu,
							shipmentScheduleWithHUFactory,
							huIdsToPick.get(pickStepHU.getActualPickedHU().getId()).isAnonymousHuPickedOnTheFly());
				});
	}
}
