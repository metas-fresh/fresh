package de.metas.costing;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
<<<<<<< HEAD
=======
import lombok.Value;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

<<<<<<< HEAD
@Builder
public class CostDetailAdjustment
{
	@NonNull
	CostDetailId costDetailId;

	@NonNull
	CostAmount amt;
	@NonNull
	Quantity qty;

	@NonNull
	CostDetailPreviousAmounts previousAmounts;
=======
@Value
@Builder
public class CostDetailAdjustment
{
	@NonNull CostDetailId costDetailId;

	@NonNull Quantity qty;
	@NonNull CostAmount oldCostPrice;
	@NonNull CostAmount oldCostAmount;
	@NonNull CostAmount newCostPrice;
	@NonNull CostAmount newCostAmount;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
