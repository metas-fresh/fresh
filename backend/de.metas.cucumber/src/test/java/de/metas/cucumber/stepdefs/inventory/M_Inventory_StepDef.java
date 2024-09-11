/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.inventory;

import de.metas.common.util.time.SystemTime;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.cucumber.stepdefs.docType.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class M_Inventory_StepDef
{
	private final InventoryService inventoryService = SpringContextHolder.instance.getBean(InventoryService.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final M_Inventory_StepDefData inventoryTable;
	private final M_InventoryLine_StepDefData inventoryLineTable;
	private final M_Product_StepDefData productTable;
	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	private final M_HU_StepDefData huTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	private final C_Flatrate_Term_StepDefData flatrateTermTable;
	private final C_DocType_StepDefData docTypeTable;

	@Given("metasfresh contains M_Inventories:")
	public void addNewInventory(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		for (final Map<String, String> tableRow : tableRows)
		{
			addNewInventory(tableRow);
		}
	}

	@Given("metasfresh contains M_InventoriesLines:")
	public void addNewInventoryLine(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			addNewInventoryLine(tableRow);
		}
	}

	@Given("^the inventory identified by (.*) is (completed|reversed)")
	public void inventory_is_completed(@NonNull final String inventoryIdentifier, @NonNull final String action)
	{
		final I_M_Inventory inventory = inventoryTable.get(inventoryIdentifier);

		switch (StepDefDocAction.valueOf(action))
		{
			case completed ->
			{
				inventory.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(inventory, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
			}
			case reversed ->
			{
				inventory.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(inventory, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
			}
			default -> throw new AdempiereException("Unhandled M_Inventory action")
					.appendParametersToMessage()
					.setParameter("action:", action);
		}
	}

	@And("the following virtual inventory is created")
	public void createVirtualInventory(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU.COLUMNNAME_M_HU_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentScheduleRecord = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		assertThat(productRecord).isNotNull();

		final I_C_UOM productUOM = uomDAO.getById(productRecord.getC_UOM_ID());

		final int qtyToBeAddedParam = DataTableUtil.extractIntForColumnName(row, "QtyToBeAdded");

		final Quantity qtyToBeAdded = Quantity.of(qtyToBeAddedParam, productUOM);

		final WarehouseId warehouseId = WarehouseId.ofRepoId(shipmentScheduleRecord.getM_Warehouse_ID());
		final OrgId orgId = OrgId.ofRepoId(shipmentScheduleRecord.getAD_Org_ID());
		final ClientId clientId = ClientId.ofRepoId(shipmentScheduleRecord.getAD_Client_ID());
		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNull(shipmentScheduleRecord.getM_AttributeSetInstance_ID());

		final CreateVirtualInventoryWithQtyReq req = CreateVirtualInventoryWithQtyReq.builder()
				.clientId(clientId)
				.orgId(orgId)
				.warehouseId(warehouseId)
				.productId(productId)
				.qty(qtyToBeAdded)
				.movementDate(SystemTime.asZonedDateTime())
				.attributeSetInstanceId(attributeSetInstanceId)
				.build();

		final HuId huId = inventoryService.createInventoryForMissingQty(req);

		huTable.put(huIdentifier, InterfaceWrapperHelper.load(huId, I_M_HU.class));
	}

	@And("metasfresh initially has M_Inventory data")
	public void setupM_Inventory_Data(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> row = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : row)
		{
			createM_Inventory(dataTableRow);
		}
	}

	@And("metasfresh initially has M_InventoryLine data")
	public void setupM_InventoryLine_Data(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> row = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : row)
		{
			createM_InventoryLine(dataTableRow);
		}
	}

	@And("complete inventory with inventoryIdentifier {string}")
	public void complete_inventory(@NonNull final String inventoryIdentifier)
	{
		final I_M_Inventory inventory = inventoryTable.get(inventoryIdentifier);

		inventoryService.completeDocument(InventoryId.ofRepoId(inventory.getM_Inventory_ID()));
	}

	private void addNewInventory(@NonNull final Map<String, String> tableRow)
	{
		final String warehouseIdOrIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Inventory.COLUMNNAME_M_Warehouse_ID);

		final int warehouseId = warehouseTable.getOptional(warehouseIdOrIdentifier)
				.map(I_M_Warehouse::getM_Warehouse_ID)
				.orElseGet(() -> Integer.parseInt(warehouseIdOrIdentifier));

		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);

		inventoryRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		inventoryRecord.setM_Warehouse_ID(WarehouseId.ofRepoId(warehouseId).getRepoId());
		inventoryRecord.setMovementDate(TimeUtil.asTimestamp(DataTableUtil.extractLocalDateForColumnName(tableRow, I_M_Inventory.COLUMNNAME_MovementDate)));

		final String documentNo = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_Inventory.COLUMNNAME_DocumentNo);
		if (Check.isNotBlank(documentNo))
		{
			inventoryRecord.setDocumentNo(documentNo);
		}
		else
		{
			inventoryRecord.setDocumentNo("not_important");
		}

		final String docTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_Inventory.COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(docTypeIdentifier))
		{
			final I_C_DocType docTypeRecord = docTypeTable.get(docTypeIdentifier);
			inventoryRecord.setC_DocType_ID(docTypeRecord.getC_DocType_ID());
		}

		saveRecord(inventoryRecord);

		final String inventoryIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_M_Inventory.COLUMNNAME_M_Inventory_ID, "M_Inventory");
		inventoryTable.put(inventoryIdentifier, inventoryRecord);
	}

	private void addNewInventoryLine(@NonNull final Map<String, String> tableRow)
	{
		final de.metas.invoicecandidate.model.I_M_InventoryLine inventoryLine = newInstance(de.metas.invoicecandidate.model.I_M_InventoryLine.class);

		final String inventoryIdentifier = DataTableUtil.extractStringForColumnName(tableRow, de.metas.invoicecandidate.model.I_M_InventoryLine.COLUMNNAME_M_Inventory_ID + ".Identifier");
		final I_M_Inventory inventory = inventoryTable.get(inventoryIdentifier);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(inventory.getM_Warehouse_ID());

		inventoryLine.setM_Inventory_ID(inventory.getM_Inventory_ID());

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, de.metas.invoicecandidate.model.I_M_InventoryLine.COLUMNNAME_M_Product_ID + ".Identifier");
		final Integer productId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));

		inventoryLine.setM_Locator_ID(warehouseBL.getOrCreateDefaultLocatorId(warehouseId).getRepoId());
		inventoryLine.setM_Product_ID(productId);

		inventoryLine.setQtyCount(DataTableUtil.extractBigDecimalForColumnName(tableRow, "QtyCount"));
		inventoryLine.setQtyBook(DataTableUtil.extractBigDecimalForColumnName(tableRow, "QtyBook"));
		inventoryLine.setIsCounted(true);
		inventoryLine.setHUAggregationType(HUAggregationType.SINGLE_HU.getCode());

		final String uomX12DE355Code = DataTableUtil.extractStringForColumnName(tableRow, "UOM.X12DE355");
		final X12DE355 uom = X12DE355.ofCode(uomX12DE355Code);

		final I_C_UOM uomRecord = uomDAO.getByX12DE355(uom);

		inventoryLine.setC_UOM_ID(uomRecord.getC_UOM_ID());

		final String attributeSetInstanceIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_OrderLine.COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (de.metas.common.util.Check.isNotBlank(attributeSetInstanceIdentifier))
		{
			final I_M_AttributeSetInstance attributeSetInstance = attributeSetInstanceTable.get(attributeSetInstanceIdentifier);
			assertThat(attributeSetInstance).isNotNull();

			inventoryLine.setM_AttributeSetInstance_ID(attributeSetInstance.getM_AttributeSetInstance_ID());
		}

		final String modularFlatrateTermIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_InventoryLine.COLUMNNAME_Modular_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(modularFlatrateTermIdentifier))
		{
			final I_C_Flatrate_Term flatrateTerm = flatrateTermTable.get(modularFlatrateTermIdentifier);

			inventoryLine.setModular_Flatrate_Term_ID(flatrateTerm.getC_Flatrate_Term_ID());
		}

		final BigDecimal qtyInternalUse = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_M_InventoryLine.COLUMNNAME_QtyInternalUse);
		if (qtyInternalUse != null)
		{
			inventoryLine.setQtyInternalUse(qtyInternalUse);
		}

		saveRecord(inventoryLine);

		final String huIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + de.metas.handlingunits.model.I_M_InventoryLine.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(huIdentifier))
		{
			final I_M_HU hu = huTable.get(huIdentifier);
			final de.metas.handlingunits.model.I_M_InventoryLine inventoryLineHuRecord = InterfaceWrapperHelper.create(inventoryLine, de.metas.handlingunits.model.I_M_InventoryLine.class);

			inventoryLineHuRecord.setM_HU_ID(hu.getM_HU_ID());
			saveRecord(inventoryLineHuRecord);
			createM_InventoryLine_HU(hu,uomRecord, inventoryLine);
		}

		inventoryLineTable.put(DataTableUtil.extractRecordIdentifier(tableRow, I_M_InventoryLine.COLUMNNAME_M_InventoryLine_ID, "M_InventoryLine"), inventoryLine);
	}

	private void createM_InventoryLine_HU(@NonNull final I_M_HU hu, @NonNull final I_C_UOM uomRecord, @NonNull final de.metas.invoicecandidate.model.I_M_InventoryLine inventoryLine)
	{
		final I_M_InventoryLine_HU lineHU = newInstance(I_M_InventoryLine_HU.class, inventoryLine);

		lineHU.setM_Inventory_ID(inventoryLine.getM_Inventory_ID());
		lineHU.setM_InventoryLine_ID(inventoryLine.getM_InventoryLine_ID());
		lineHU.setM_HU_ID(hu.getM_HU_ID());
		lineHU.setC_UOM_ID(uomRecord.getC_UOM_ID());

		saveRecord(lineHU);
	}

	private void createM_Inventory(@NonNull final Map<String, String> row)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(row, "M_Inventory_ID.Identifier");
		final Timestamp movementDate = DataTableUtil.extractDateTimestampForColumnName(row, "MovementDate");
		final String documentNo = DataTableUtil.extractStringForColumnName(row, "DocumentNo");

		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);
		inventoryRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		inventoryRecord.setC_DocType_ID(StepDefConstants.DOC_TYPE_ID_MMI.getRepoId());
		inventoryRecord.setDocStatus(DocStatus.Drafted.getCode());

		inventoryRecord.setMovementDate(movementDate);
		inventoryRecord.setDocumentNo(documentNo);

		saveRecord(inventoryRecord);

		inventoryTable.put(identifier, inventoryRecord);
	}

	private void createM_InventoryLine(@NonNull final Map<String, String> row)
	{
		final String inventoryIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Inventory_ID.Identifier");
		final String inventoryLineIdentifier = DataTableUtil.extractStringForColumnName(row, "M_InventoryLine_ID.Identifier");
		final BigDecimal qtyBook = DataTableUtil.extractBigDecimalForColumnName(row, "QtyBook");
		final BigDecimal qtyCount = DataTableUtil.extractBigDecimalForColumnName(row, "QtyCount");
		final Integer productId = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT.M_Product_ID");
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID.Identifier");

		final I_M_Inventory mInventory = inventoryTable.get(inventoryIdentifier);

		final I_M_InventoryLine inventoryLineRecord = newInstance(I_M_InventoryLine.class);
		inventoryLineRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		inventoryLineRecord.setC_UOM_ID(UomId.toRepoId(UomId.EACH));
		inventoryLineRecord.setM_Locator_ID(StepDefConstants.LOCATOR_ID.getRepoId());
		inventoryLineRecord.setM_AttributeSetInstance(null);
		inventoryLineRecord.setHUAggregationType(HUAggregationType.SINGLE_HU.getCode());
		inventoryLineRecord.setIsCounted(true);

		inventoryLineRecord.setM_Inventory_ID(mInventory.getM_Inventory_ID());
		inventoryLineRecord.setQtyBook(qtyBook);
		inventoryLineRecord.setQtyCount(qtyCount);

		if (productId == null)
		{
			final I_M_Product product = productTable.get(productIdentifier);
			inventoryLineRecord.setM_Product_ID(product.getM_Product_ID());
		}
		else
		{
			final I_M_Product productById = InterfaceWrapperHelper.load(productId, I_M_Product.class);
			productTable.put(productIdentifier, productById);

			inventoryLineRecord.setM_Product_ID(productById.getM_Product_ID());
		}

		saveRecord(inventoryLineRecord);

		inventoryLineTable.put(inventoryLineIdentifier, inventoryLineRecord);
	}
}
