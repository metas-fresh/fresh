/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.process;

import de.metas.contracts.modular.ModCntrSpecificPrice;
import de.metas.contracts.modular.ModCntrSpecificPriceId;
import de.metas.contracts.modular.ModularContractPriceService;
import de.metas.contracts.modular.log.ModCntrLogPriceUpdateRequest;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerRegistry;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.NumberUtils;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class ModCntr_Specific_Delete_Price extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final ModularContractPriceService modularContractPriceService = SpringContextHolder.instance.getBean(ModularContractPriceService.class);
	@NonNull private final ModularContractLogService contractLogService = SpringContextHolder.instance.getBean(ModularContractLogService.class);
	@NonNull private final ModularContractLogHandlerRegistry logHandlerRegistry = SpringContextHolder.instance.getBean(ModularContractLogHandlerRegistry.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final ModCntrSpecificPriceId contractPriceId = context.getSingleSelectedRecordId(ModCntrSpecificPriceId.class);

		final ModCntrSpecificPrice contractPrice = modularContractPriceService.getById(contractPriceId);
		if (!contractPrice.isScalePrice() && NumberUtils.isZeroOrNull(contractPrice.minValue()))
		{
			return ProcessPreconditionsResolution.reject();
		}

		// do not allow scale price deletion if there is no fallback scale price
		if (!modularContractPriceService.existsSimilarContractSpecificScalePrice(contractPriceId))
		{
			return ProcessPreconditionsResolution.reject();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ModCntrSpecificPriceId contractPriceId = ModCntrSpecificPriceId.ofRepoId(getRecord_ID());
		final ModCntrSpecificPrice contractPrice = modularContractPriceService.getById(contractPriceId);

		// delete price
		modularContractPriceService.deleteById(contractPriceId);

		// the update price by recomputing the price for logs
		// the given price is ingonred in UserElementNumberShipmentLineLog
		contractLogService.updatePriceAndAmount(ModCntrLogPriceUpdateRequest.builder()
						.unitPrice(contractPrice.getProductPrice())
						.flatrateTermId(contractPrice.flatrateTermId())
						.modularContractModuleId(contractPrice.modularContractModuleId())
						.build(),
				logHandlerRegistry);
		
		return MSG_OK;
	}

}
