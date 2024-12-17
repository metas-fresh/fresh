package de.metas.ui.web.material.cockpit.rowfactory;

import de.metas.dimension.DimensionSpecGroup;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.QtyConvertorService;
import de.metas.util.Services;
import lombok.Data;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;

import java.util.HashSet;
import java.util.Set;

import static de.metas.quantity.Quantity.addToNullable;
import static de.metas.util.Check.assumeNotNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Mutable row representation that is used during the rows' loading
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Data
public class DimensionGroupSubRowBucket
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final QtyConvertorService qtyConvertorService = SpringContextHolder.instance.getBean(QtyConvertorService.class);

	public static DimensionGroupSubRowBucket create(@NonNull final DimensionSpecGroup dimensionSpecGroup)
	{
		return new DimensionGroupSubRowBucket(dimensionSpecGroup);
	}

	private final DimensionSpecGroup dimensionSpecGroup;

	// Zusage Lieferant
	private Quantity pmmQtyPromised;

	private Quantity qtyDemandSalesOrder;

	private Quantity qtyDemandDDOrder;

	private Quantity qtyDemandSum;
	// MRP MEnge
	private Quantity qtyDemandPPOrder;

	private Quantity qtySupplyPPOrder;

	private Quantity qtySupplyPurchaseOrder;

	private Quantity qtySupplyDDOrder;

	private Quantity qtySupplySum;

	private Quantity qtySupplyRequired;

	private Quantity qtySupplyToSchedule;

	private Quantity qtyMaterialentnahme;

	// zusagbar Zaehlbestand
	private Quantity qtyExpectedSurplus;

	private Quantity qtyOnHandStock;

	private final Set<Integer> cockpitRecordIds = new HashSet<>();

	private final Set<Integer> stockRecordIds = new HashSet<>();

	public DimensionGroupSubRowBucket(@NonNull final DimensionSpecGroup dimensionSpecGroup)
	{
		this.dimensionSpecGroup = dimensionSpecGroup;
	}

	public void addCockpitRecord(@NonNull final I_MD_Cockpit cockpitRecord)
	{
		final I_C_UOM uom = productBL.getStockUOM(cockpitRecord.getM_Product_ID());

		pmmQtyPromised = addToNullable(pmmQtyPromised, cockpitRecord.getPMM_QtyPromised_OnDate(), uom);
		qtyMaterialentnahme = addToNullable(qtyMaterialentnahme, cockpitRecord.getQtyMaterialentnahme(), uom);

		qtyDemandPPOrder = addToNullable(qtyDemandPPOrder, cockpitRecord.getQtyDemand_PP_Order(), uom);
		qtyDemandSalesOrder = addToNullable(qtyDemandSalesOrder, cockpitRecord.getQtyDemand_SalesOrder(), uom);
		qtyDemandDDOrder = addToNullable(qtyDemandDDOrder, cockpitRecord.getQtyDemand_DD_Order(), uom);
		qtyDemandSum = addToNullable(qtyDemandSum, cockpitRecord.getQtyDemandSum(), uom);

		qtySupplyPPOrder = addToNullable(qtySupplyPPOrder, cockpitRecord.getQtySupply_PP_Order(), uom);
		qtySupplyPurchaseOrder = addToNullable(qtySupplyPurchaseOrder, cockpitRecord.getQtySupply_PurchaseOrder(), uom);
		qtySupplyDDOrder = addToNullable(qtySupplyDDOrder, cockpitRecord.getQtySupply_DD_Order(), uom);
		qtySupplySum = addToNullable(qtySupplySum, cockpitRecord.getQtySupplySum(), uom);
		qtySupplyRequired = addToNullable(qtySupplyRequired, cockpitRecord.getQtySupplyRequired(), uom);
		qtySupplyToSchedule = addToNullable(qtySupplyToSchedule, cockpitRecord.getQtySupplyToSchedule(), uom);

		qtyExpectedSurplus = addToNullable(qtyExpectedSurplus, cockpitRecord.getQtyExpectedSurplus(), uom);

		cockpitRecordIds.add(cockpitRecord.getMD_Cockpit_ID());
	}

	public void addStockRecord(@NonNull final I_MD_Stock stockRecord)
	{
		final I_C_UOM uom = productBL.getStockUOM(stockRecord.getM_Product_ID());

		qtyOnHandStock = addToNullable(qtyOnHandStock, stockRecord.getQtyOnHand(), uom);

		stockRecordIds.add(stockRecord.getMD_Stock_ID());
	}

	public MaterialCockpitRow createIncludedRow(@NonNull final MainRowWithSubRows mainRowBucket)
	{
		final MainRowBucketId productIdAndDate = assumeNotNull(
				mainRowBucket.getProductIdAndDate(),
				"productIdAndDate may not be null; mainRowBucket={}", mainRowBucket);

		return MaterialCockpitRow.attributeSubRowBuilder()
				.productId(productIdAndDate.getProductId().getRepoId())

				.dimensionGroup(dimensionSpecGroup)
				.pmmQtyPromised(getPmmQtyPromised())
				.qtyMaterialentnahme(getQtyMaterialentnahme())
				.qtyDemandPPOrder(getQtyDemandPPOrder())
				.qtySupplyPurchaseOrder(getQtySupplyPurchaseOrder())
				.qtyDemandSalesOrder(getQtyDemandSalesOrder())
				.qtyDemandDDOrder(getQtyDemandDDOrder())
				.qtyDemandSum(getQtyDemandSum())
				.qtySupplyPPOrder(getQtySupplyPPOrder())
				.qtySupplyDDOrder(getQtySupplyDDOrder())
				.qtySupplySum(getQtySupplySum())
				.qtySupplyRequired(getQtySupplyRequired())
				.qtySupplyToSchedule(getQtySupplyToSchedule())
				.qtyOnHandStock(getQtyOnHandStock())
				.qtyExpectedSurplus(getQtyExpectedSurplus())
				.allIncludedCockpitRecordIds(cockpitRecordIds)
				.allIncludedStockRecordIds(stockRecordIds)
				.qtyConvertorBuilder(qtyConvertorService.getQtyConvertorIfConfigured(productIdAndDate))
				.build();
	}
}
