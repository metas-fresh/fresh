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

package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.inout.ShipmentScheduleId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Value
@Builder
public class ReopenPickingJobRequest
{
	@NonNull Set<ShipmentScheduleId> shipmentScheduleIds;
	@NonNull List<HUWithPickOnTheFlyStatus> huInfoList;

	@NonNull
	public ImmutableSet<HuId> getHuIds()
	{
		return huInfoList.stream()
				.map(HUWithPickOnTheFlyStatus::getHuId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
