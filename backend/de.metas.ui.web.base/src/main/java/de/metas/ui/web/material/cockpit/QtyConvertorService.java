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
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.organization.IOrgDAO;
import de.metas.ui.web.material.cockpit.rowfactory.MainRowBucketId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class QtyConvertorService
{
	private final static String SYS_CONFIG_SHOW_QTY_IN_TU_UOM = "de.metas.ui.web.material.cockpit.SYS_CONFIG_SHOW_QTY_IN_TU_UOM";

	private final IHUCapacityBL capacityBL = Services.get(IHUCapacityBL.class);
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IHUPIItemProductBL itemProductBL = Services.get(IHUPIItemProductBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Nullable
	public QtyTUConvertor.QtyTUConvertorBuilder getQtyConvertorIfConfigured(@NonNull final MainRowBucketId productId)
	{
		if (!sysConfigBL.getBooleanValue(SYS_CONFIG_SHOW_QTY_IN_TU_UOM, false))
		{
			return null;
		}

		return QtyTUConvertor.builder()
				.capacityBL(capacityBL)
				.productId(productId.getProductId())
				.tuUOM(uomDao.getByX12DE355(X12DE355.TU));
	}
}
