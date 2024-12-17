/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.material.cockpit;

import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Value
@Builder(toBuilder = true)
public class QtyTUConvertor implements QtyConvertor
{
	@NonNull IHUCapacityBL capacityBL;
	@NonNull ProductId productId;
	@NonNull I_M_HU_PI_Item_Product packingInstruction;
	@NonNull I_C_UOM tuUOM;
	@NonNull Map<UomId, Capacity> uomId2Capacity = new HashMap<>();

	@Nullable
	@Override
	public Quantity convert(@Nullable final Quantity quantity)
	{
		if (quantity == null)
		{
			return null;
		}

		final Capacity capacity = getCapacityForUomId(quantity.getUOM());

		final BigDecimal qtyTUQty = quantity.toBigDecimal()
				.divide(capacity.toBigDecimal(), tuUOM.getStdPrecision(), RoundingMode.UP);

		return Quantity.of(qtyTUQty, tuUOM);
	}

	@Override
	public @NonNull UomId getTargetUomId()
	{
		return UomId.ofRepoId(tuUOM.getC_UOM_ID());
	}

	@NonNull
	private Capacity getCapacityForUomId(@NonNull final I_C_UOM uom)
	{
		return uomId2Capacity.computeIfAbsent(UomId.ofRepoId(uom.getC_UOM_ID()),
											  (ignore) -> capacityBL.getCapacity(packingInstruction, productId, uom));
	}
}
