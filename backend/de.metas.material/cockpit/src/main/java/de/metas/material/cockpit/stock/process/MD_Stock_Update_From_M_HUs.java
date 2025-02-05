package de.metas.material.cockpit.stock.process;

import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.model.I_MD_Stock_From_HUs_V;
import de.metas.material.cockpit.stock.StockChangeSourceInfo;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockDataUpdateRequest;
import de.metas.material.cockpit.stock.StockDataUpdateRequestHandler;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;

import java.util.List;

import static java.math.BigDecimal.ZERO;

/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * Reset the {@link I_MD_Stock} table.
 * May be run in parallel to "normal" production stock changes.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class MD_Stock_Update_From_M_HUs extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final StockDataUpdateRequestHandler dataUpdateRequestHandler = SpringContextHolder.instance.getBean(StockDataUpdateRequestHandler.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final List<I_MD_Stock_From_HUs_V> huBasedDataRecords = retrieveHuData();

		addLog("Retrieved {} MD_Stock_From_HUs_V records", huBasedDataRecords.size());

		createAndHandleDataUpdateRequests(huBasedDataRecords);
		addLog("Created and handled DataUpdateRequests for all MD_Stock_From_HUs_V records");

		return MSG_OK;
	}

	private List<I_MD_Stock_From_HUs_V> retrieveHuData()
	{
		addLog("Performing a select for Records to correct on MD_Stock_From_HUs_V");
		return queryBL
				.createQueryBuilder(I_MD_Stock_From_HUs_V.class)
				.addNotEqualsFilter(I_MD_Stock_From_HUs_V.COLUMNNAME_QtyOnHandChange, ZERO)
				.create()
				.list();
	}

	private void createAndHandleDataUpdateRequests(
			@NonNull final List<I_MD_Stock_From_HUs_V> huBasedDataRecords)
	{
		final ResetStockPInstanceId resetStockPInstanceId = ResetStockPInstanceId.ofPInstanceId(getProcessInfo().getPinstanceId());
		final StockChangeSourceInfo info = StockChangeSourceInfo.ofResetStockPInstanceId(resetStockPInstanceId);

		for (final I_MD_Stock_From_HUs_V huBasedDataRecord : huBasedDataRecords)
		{
			final StockDataUpdateRequest dataUpdateRequest = createDataUpdatedRequest(
					huBasedDataRecord,
					info);
			addLog("Handling corrective dataUpdateRequest={}", dataUpdateRequest);
			dataUpdateRequestHandler.handleDataUpdateRequest(dataUpdateRequest);
		}
	}

	private StockDataUpdateRequest createDataUpdatedRequest(
			@NonNull final I_MD_Stock_From_HUs_V huBasedDataRecord,
			@NonNull final StockChangeSourceInfo stockDataUpdateRequestSourceInfo)
	{
		final StockDataRecordIdentifier recordIdentifier = toStockDataRecordIdentifier(huBasedDataRecord);

		final ProductId productId = ProductId.ofRepoId(huBasedDataRecord.getM_Product_ID());
		final Quantity qtyInStorageUOM = Quantitys.of(huBasedDataRecord.getQtyOnHandChange(), UomId.ofRepoId(huBasedDataRecord.getC_UOM_ID()));
		final Quantity qtyInProductUOM = uomConversionBL.convertToProductUOM(qtyInStorageUOM, productId);

		return StockDataUpdateRequest.builder()
				.identifier(recordIdentifier)
				.onHandQtyChange(qtyInProductUOM.toBigDecimal())
				.sourceInfo(stockDataUpdateRequestSourceInfo)
				.build();
	}

	private static StockDataRecordIdentifier toStockDataRecordIdentifier(@NonNull final I_MD_Stock_From_HUs_V huBasedDataRecord)
	{
		try
		{
			return StockDataRecordIdentifier.builder()
					.clientId(ClientId.ofRepoId(huBasedDataRecord.getAD_Client_ID()))
					.orgId(OrgId.ofRepoId(huBasedDataRecord.getAD_Org_ID()))
					.warehouseId(WarehouseId.ofRepoId(huBasedDataRecord.getM_Warehouse_ID()))
					.productId(ProductId.ofRepoId(huBasedDataRecord.getM_Product_ID()))
					.storageAttributesKey(AttributesKey.ofString(huBasedDataRecord.getAttributesKey()))
					.build();
		}
		catch (final RuntimeException e)
		{
			throw new AdempiereException("Can't create StockDataRecordIdentifier for invalid MD_Stock_From_HUs_V record", e)
					.appendParametersToMessage()
					.setParameter("AD_Client_ID", huBasedDataRecord.getAD_Client_ID())
					.setParameter("AD_Org_ID", huBasedDataRecord.getAD_Org_ID())
					.setParameter("M_Product_ID", huBasedDataRecord.getM_Product_ID())
					.setParameter("M_Warehouse_ID", huBasedDataRecord.getM_Warehouse_ID())
					.setParameter("StorageAttributesKey", huBasedDataRecord.getAttributesKey());
		}
	}
}
