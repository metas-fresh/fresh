package de.metas.ui.web.material.cockpit;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.dimension.DimensionSpecGroup;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.i18n.IMsgBL;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout.Displayed;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.collections.ListUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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

@ToString
@EqualsAndHashCode(of = "documentId")
public class MaterialCockpitRow implements IViewRow
{
	/**
	 * Please keep its prefix in sync with {@link MaterialCockpitViewFactory#SYSCFG_DisplayIncludedRows}.
	 */
	public static final String SYSCFG_PREFIX = "de.metas.ui.web.material.cockpit.field";

	public static MaterialCockpitRow cast(final IViewRow row)
	{
		return (MaterialCockpitRow)row;
	}

	private static final String SEPARATOR = "-";
	private static final Joiner DOCUMENT_ID_JOINER = Joiner.on(SEPARATOR).skipNulls();

	@Getter
	private final LocalDate date;

	@Getter
	private final int productId;

	public static final String FIELDNAME_QtyStockEstimateSeqNo = I_MD_Cockpit.COLUMNNAME_QtyStockEstimateSeqNo;
	@ViewColumn(fieldName = FIELDNAME_QtyStockEstimateSeqNo, //
			captionKey = FIELDNAME_QtyStockEstimateSeqNo, //
			widgetType = DocumentFieldWidgetType.Integer, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 5, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final Integer qtyStockEstimateSeqNo;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, //
			captionKey = I_MD_Cockpit.COLUMNNAME_ProductValue, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10) })
	private final String productValue;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, //
			captionKey = I_MD_Cockpit.COLUMNNAME_ProductName, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20) })
	private final String productName;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, //
			captionKey = I_M_Product.COLUMNNAME_M_Product_Category_ID, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30, displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	@Getter
	@VisibleForTesting
	private final String productCategoryOrSubRowName;

	@Getter
	private final DimensionSpecGroup dimensionGroupOrNull;

	public static final String FIELDNAME_Manufacturer_ID = I_M_Product.COLUMNNAME_Manufacturer_ID;

	/**
	 * Use supplier in order to make this work with unit tests; getting the LookupValue uses LookupDAO.retrieveLookupDisplayInfo which goes directly to the DB.
	 */
	@ViewColumn(fieldName = FIELDNAME_Manufacturer_ID, //
			captionKey = FIELDNAME_Manufacturer_ID, //
			widgetType = DocumentFieldWidgetType.Lookup, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	private final Supplier<LookupValue> manufacturer;

	public static final String FIELDNAME_PackageSize = I_M_Product.COLUMNNAME_PackageSize;
	@ViewColumn(fieldName = FIELDNAME_PackageSize, //
			captionKey = FIELDNAME_PackageSize, //
			widgetType = DocumentFieldWidgetType.Text, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	private final String packageSize;

	public static final String FIELDNAME_C_UOM_ID = I_M_Product.COLUMNNAME_C_UOM_ID;

	/**
	 * Use supplier in order to make this work with unit tests; getting the LookupValue uses LookupDAO.retrieveLookupDisplayInfo which goes directly to the DB.
	 */
	@ViewColumn(fieldName = FIELDNAME_C_UOM_ID, //
			captionKey = FIELDNAME_C_UOM_ID, //
			widgetType = DocumentFieldWidgetType.Lookup, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	private final Supplier<LookupValue> uom;

	// Zusage Lieferant
	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
			captionKey = I_MD_Cockpit.COLUMNNAME_PMM_QtyPromised_OnDate, //
			layouts = {
					@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70, //
							displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	private final BigDecimal pmmQtyPromised;

	public static final String FIELDNAME_QtyDemand_SalesOrder = I_MD_Cockpit.COLUMNNAME_QtyDemand_SalesOrder;
	@ViewColumn(fieldName = FIELDNAME_QtyDemand_SalesOrder, //
			captionKey = FIELDNAME_QtyDemand_SalesOrder, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 80,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyDemandSalesOrder;

	public static final String FIELDNAME_QtyDemand_DD_Order = I_MD_Cockpit.COLUMNNAME_QtyDemand_DD_Order;
	@ViewColumn(fieldName = FIELDNAME_QtyDemand_DD_Order, //
			captionKey = FIELDNAME_QtyDemand_DD_Order, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 90,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyDemandDDOrder;

	public static final String FIELDNAME_QtyDemandSum = I_MD_Cockpit.COLUMNNAME_QtyDemandSum;
	@ViewColumn(fieldName = FIELDNAME_QtyDemandSum, //
			captionKey = FIELDNAME_QtyDemandSum, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 100,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyDemandSum;

	public static final String FIELDNAME_QtySupplyPPOrder = I_MD_Cockpit.COLUMNNAME_QtySupply_PP_Order;
	@ViewColumn(fieldName = FIELDNAME_QtySupplyPPOrder, //
			captionKey = FIELDNAME_QtySupplyPPOrder, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 110,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplyPPOrder;

	public static final String FIELDNAME_QtySupply_PurchaseOrder = I_MD_Cockpit.COLUMNNAME_QtySupply_PurchaseOrder;
	@ViewColumn(fieldName = FIELDNAME_QtySupply_PurchaseOrder, //
			captionKey = FIELDNAME_QtySupply_PurchaseOrder, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 120) })
	@Getter // note that we use the getter for testing
	private final BigDecimal qtySupplyPurchaseOrder;

	public static final String FIELDNAME_QtySupplyDDOrder = I_MD_Cockpit.COLUMNNAME_QtySupply_DD_Order;
	@ViewColumn(fieldName = FIELDNAME_QtySupplyDDOrder, //
			captionKey = FIELDNAME_QtySupplyDDOrder, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 130,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplyDDOrder;

	public static final String FIELDNAME_QtySupplySum = I_MD_Cockpit.COLUMNNAME_QtySupplySum;
	@ViewColumn(fieldName = FIELDNAME_QtySupplySum, //
			captionKey = FIELDNAME_QtySupplySum, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 140,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplySum;

	public static final String FIELDNAME_QtySupplyRequired = I_MD_Cockpit.COLUMNNAME_QtySupplyRequired;
	@ViewColumn(fieldName = FIELDNAME_QtySupplyRequired, //
			captionKey = FIELDNAME_QtySupplyRequired, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 150,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplyRequired;

	public static final String FIELDNAME_QtySupplyToSchedule = I_MD_Cockpit.COLUMNNAME_QtySupplyToSchedule;
	@ViewColumn(fieldName = FIELDNAME_QtySupplyToSchedule, //
			captionKey = FIELDNAME_QtySupplyToSchedule, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 160,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplyToSchedule;

	public static final String FIELDNAME_QtyMaterialentnahme = I_MD_Cockpit.COLUMNNAME_QtyMaterialentnahme;
	@ViewColumn(fieldName = FIELDNAME_QtyMaterialentnahme, //
			captionKey = FIELDNAME_QtyMaterialentnahme, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 170, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyMaterialentnahme;

	// MRP MEnge
	public static final String FIELDNAME_QtyDemand_PP_Order = I_MD_Cockpit.COLUMNNAME_QtyDemand_PP_Order;
	@ViewColumn(fieldName = FIELDNAME_QtyDemand_PP_Order, //
			captionKey = FIELDNAME_QtyDemand_PP_Order, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 180, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyDemandPPOrder;

	// Zaehlbestand
	public static final String FIELDNAME_QtyStockCurrent = I_MD_Cockpit.COLUMNNAME_QtyStockCurrent;
	@ViewColumn(fieldName = FIELDNAME_QtyStockCurrent, //
			captionKey = FIELDNAME_QtyStockCurrent, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 190, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyStockCurrent;

	public static final String FIELDNAME_QtyStockEstimateCount = I_MD_Cockpit.COLUMNNAME_QtyStockEstimateCount;
	@ViewColumn(fieldName = FIELDNAME_QtyStockEstimateCount, //
			captionKey = FIELDNAME_QtyStockEstimateCount, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 210, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyStockEstimateCount;

	public static final String FIELDNAME_QtyStockEstimateTime = I_MD_Cockpit.COLUMNNAME_QtyStockEstimateTime;
	@ViewColumn(fieldName = FIELDNAME_QtyStockEstimateTime, //
			captionKey = FIELDNAME_QtyStockEstimateTime, //
			widgetType = DocumentFieldWidgetType.Timestamp, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 220, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final Instant qtyStockEstimateTime;

	public static final String FIELDNAME_QtyInventoryCount = I_MD_Cockpit.COLUMNNAME_QtyInventoryCount;
	@ViewColumn(fieldName = FIELDNAME_QtyInventoryCount, //
			captionKey = FIELDNAME_QtyInventoryCount, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 230, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyInventoryCount;

	public static final String FIELDNAME_QtyInventoryTime = I_MD_Cockpit.COLUMNNAME_QtyInventoryTime;
	@ViewColumn(fieldName = FIELDNAME_QtyInventoryTime, //
			captionKey = FIELDNAME_QtyInventoryTime, //
			widgetType = DocumentFieldWidgetType.Timestamp, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 240, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final Instant qtyInventoryTime;

	// zusagbar Zaehlbestand
	public static final String FIELDNAME_QtyExpectedSurplus = I_MD_Cockpit.COLUMNNAME_QtyExpectedSurplus;
	@ViewColumn(fieldName = FIELDNAME_QtyExpectedSurplus, //
			captionKey = FIELDNAME_QtyExpectedSurplus, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 250, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyExpectedSurplus;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
			captionKey = I_MD_Stock.COLUMNNAME_QtyOnHand, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 260) })
	@Getter // note that we use the getter for testing
	private final BigDecimal qtyOnHandStock;

	private final DocumentId documentId;

	private final DocumentPath documentPath;

	private final List<MaterialCockpitRow> includedRows;

	private final IViewRowType rowType;

	@Getter
	private final Set<Integer> allIncludedCockpitRecordIds;

	@Getter
	private final Set<Integer> allIncludedStockRecordIds;

	private final ViewRowFieldNameAndJsonValuesHolder<MaterialCockpitRow> values = ViewRowFieldNameAndJsonValuesHolder.newInstance(MaterialCockpitRow.class);

	@lombok.Builder(builderClassName = "MainRowBuilder", builderMethodName = "mainRowBuilder")
	private MaterialCockpitRow(
			final Quantity qtyDemandSalesOrder,
			final Quantity qtyDemandPPOrder,
			final Quantity qtyDemandDDOrder,
			final Quantity pmmQtyPromised,
			final Quantity qtyDemandSum,
			final Quantity qtySupplyPPOrder,
			final Quantity qtySupplyPurchaseOrder,
			final Quantity qtySupplyDDOrder,
			final Quantity qtySupplySum,
			final Quantity qtySupplyRequired,
			final Quantity qtySupplyToSchedule,
			final Quantity qtyMaterialentnahme,
			final Quantity qtyStockEstimateCount,
			final Instant qtyStockEstimateTime,
			@Nullable final Integer qtyStockEstimateSeqNo,
			final Quantity qtyInventoryCount,
			final Instant qtyInventoryTime,
			final Quantity qtyExpectedSurplus,
			final Quantity qtyStockCurrent,
			final Quantity qtyOnHandStock,
			@NonNull final ProductId productId,
			@NonNull final HuPackingInstructionsVersionId huPackingInstructionsVersionId,
			@NonNull final LocalDate date,
			@Singular final List<MaterialCockpitRow> includedRows,
			@NonNull final Set<Integer> allIncludedCockpitRecordIds,
			@NonNull final Set<Integer> allIncludedStockRecordIds,
			@Nullable final QtyTUConvertor.QtyTUConvertorBuilder qtyConvertorBuilder)
	{
		this.rowType = DefaultRowType.Row;

		this.date = date;

		this.dimensionGroupOrNull = null;

		this.productId = productId.getRepoId();

		this.documentId = DocumentId.of(DOCUMENT_ID_JOINER.join(
				"main",
				date,
				productId.getRepoId()));

		this.documentPath = DocumentPath.rootDocumentPath(
				MaterialCockpitUtil.WINDOWID_MaterialCockpitView,
				documentId);

		final IProductDAO productDAO = Services.get(IProductDAO.class);

		final I_M_Product productRecord = productDAO.getById(productId);
		final I_M_Product_Category productCategoryRecord = productDAO.getProductCategoryById(ProductCategoryId.ofRepoId(productRecord.getM_Product_Category_ID()));

		this.productValue = productRecord.getValue();
		this.productName = productRecord.getName();
		this.productCategoryOrSubRowName = productCategoryRecord.getName();

		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;

		final int uomRepoId = CoalesceUtil.firstGreaterThanZero(productRecord.getPackage_UOM_ID(), productRecord.getC_UOM_ID());
		final I_M_HU_PI_Item_Product huPiItemProduct = getHuPiItemProduct(productId, huPackingInstructionsVersionId);
		final QtyConvertor convertor = qtyConvertorBuilder != null && huPiItemProduct != null
				? qtyConvertorBuilder.packingInstruction(huPiItemProduct).build()
				: QtyConvertor.getNoOp(UomId.ofRepoId(uomRepoId));

		this.uom = () -> lookupFactory
				.searchInTableLookup(I_C_UOM.Table_Name)
				.findById(convertor.getTargetUomId());
		this.manufacturer = () -> lookupFactory
				.searchInTableLookup(I_C_BPartner.Table_Name)
				.findById(productRecord.getManufacturer_ID());

		this.packageSize = productRecord.getPackageSize();

		this.includedRows = includedRows;

		this.pmmQtyPromised = Quantity.toBigDecimal(convertor.convert(pmmQtyPromised));
		this.qtyDemandSalesOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandSalesOrder));
		this.qtyDemandDDOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandDDOrder));
		this.qtyDemandPPOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandPPOrder));
		this.qtyDemandSum = Quantity.toBigDecimal(convertor.convert(qtyDemandSum));
		this.qtySupplyPPOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyPPOrder));
		this.qtySupplyPurchaseOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyPurchaseOrder));
		this.qtySupplyDDOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyDDOrder));
		this.qtySupplySum = Quantity.toBigDecimal(convertor.convert(qtySupplySum));
		this.qtySupplyRequired = Quantity.toBigDecimal(convertor.convert(qtySupplyRequired));
		this.qtySupplyToSchedule = Quantity.toBigDecimal(convertor.convert(qtySupplyToSchedule));
		this.qtyMaterialentnahme = Quantity.toBigDecimal(convertor.convert(qtyMaterialentnahme));
		this.qtyStockCurrent = Quantity.toBigDecimal(convertor.convert(qtyStockCurrent));
		this.qtyExpectedSurplus = Quantity.toBigDecimal(convertor.convert(qtyExpectedSurplus));
		this.qtyOnHandStock = Quantity.toBigDecimal(convertor.convert(qtyOnHandStock));
		this.qtyStockEstimateCount = Quantity.toBigDecimal(convertor.convert(qtyStockEstimateCount));
		this.qtyStockEstimateTime = qtyStockEstimateTime;
		this.qtyStockEstimateSeqNo = qtyStockEstimateSeqNo;
		this.qtyInventoryCount = Quantity.toBigDecimal(qtyInventoryCount);
		this.qtyInventoryTime = qtyInventoryTime;

		final List<Quantity> quantitiesToVerify = Arrays.asList(
				pmmQtyPromised,
				qtyDemandSalesOrder,
				qtySupplyPurchaseOrder,
				qtySupplyPPOrder,
				qtySupplyDDOrder,
				qtySupplySum,
				qtySupplyRequired,
				qtySupplyToSchedule,
				qtyMaterialentnahme,
				qtyDemandPPOrder,
				qtyDemandDDOrder,
				qtyDemandSum,
				qtyStockCurrent,
				qtyExpectedSurplus,
				qtyOnHandStock);
		assertNullOrCommonUomId(quantitiesToVerify);

		this.allIncludedCockpitRecordIds = ImmutableSet.copyOf(allIncludedCockpitRecordIds);
		this.allIncludedStockRecordIds = ImmutableSet.copyOf(allIncludedStockRecordIds);
	}

	private void assertNullOrCommonUomId(@NonNull final List<Quantity> quantitiesToVerify)
	{
		final boolean notOK = CollectionUtils.hasDifferentValues(
				ListUtils.copyAndFilter(quantitiesToVerify, Objects::nonNull),
				Quantity::getUomId);
		Check.errorIf(notOK, "Some of the given quantities have different UOMs; quantities={}", quantitiesToVerify);
	}

	private I_M_HU_PI_Item_Product getHuPiItemProduct(final ProductId productId, final HuPackingInstructionsVersionId huPackingInstructionsVersionId)
	{
		return null;//TODO
	}

	private static LocalDate extractDate(@NonNull final List<MaterialCockpitRow> includedRows)
	{
		return CollectionUtils.extractSingleElement(includedRows, row -> row.date);
	}

	private static int extractProductId(@NonNull final List<MaterialCockpitRow> includedRows)
	{
		return CollectionUtils.extractSingleElement(includedRows, MaterialCockpitRow::getProductId);
	}

	@lombok.Builder(builderClassName = "AttributeSubRowBuilder", builderMethodName = "attributeSubRowBuilder")
	private MaterialCockpitRow(
			final int productId,
			final HuPackingInstructionsVersionId huPackingInstructionsVersionId,
			final LocalDate date,
			@NonNull final DimensionSpecGroup dimensionGroup,
			final Quantity pmmQtyPromised,
			final Quantity qtyDemandSalesOrder,
			final Quantity qtyDemandDDOrder,
			final Quantity qtyDemandSum,
			final Quantity qtySupplyPPOrder,
			final Quantity qtySupplyPurchaseOrder,
			final Quantity qtySupplyDDOrder,
			final Quantity qtySupplySum,
			final Quantity qtySupplyRequired,
			final Quantity qtySupplyToSchedule,
			final Quantity qtyMaterialentnahme,
			final Quantity qtyDemandPPOrder,
			final Quantity qtyStockEstimateCount,
			final Instant qtyStockEstimateTime,
			@Nullable final Integer qtyStockEstimateSeqNo,
			final Quantity qtyInventoryCount,
			final Instant qtyInventoryTime,
			final Quantity qtyExpectedSurplus,
			final Quantity qtyOnHandStock,
			@NonNull final Set<Integer> allIncludedCockpitRecordIds,
			@NonNull final Set<Integer> allIncludedStockRecordIds,
			@Nullable final QtyTUConvertor.QtyTUConvertorBuilder qtyConvertorBuilder)
	{
		this.rowType = DefaultRowType.Line;

		this.dimensionGroupOrNull = dimensionGroup;
		final String dimensionGroupName = dimensionGroup.getGroupName().translate(Env.getAD_Language());

		this.documentId = DocumentId.of(DOCUMENT_ID_JOINER.join(
				"attributes",
				date,
				productId,
				dimensionGroupName));

		this.documentPath = DocumentPath.rootDocumentPath(
				MaterialCockpitUtil.WINDOWID_MaterialCockpitView,
				documentId);

		this.productId = productId;

		final I_M_Product productRecord = loadOutOfTrx(productId, I_M_Product.class);
		this.productValue = productRecord.getValue();
		this.productName = productRecord.getName();
		this.productCategoryOrSubRowName = dimensionGroupName;

		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;

		final int uomRepoId = CoalesceUtil.firstGreaterThanZero(productRecord.getPackage_UOM_ID(), productRecord.getC_UOM_ID());
		final I_M_HU_PI_Item_Product huPiItemProduct = getHuPiItemProduct(ProductId.ofRepoId(productId), huPackingInstructionsVersionId);
		final QtyConvertor convertor = qtyConvertorBuilder != null && huPiItemProduct != null
				? qtyConvertorBuilder.packingInstruction(huPiItemProduct).build()
				: QtyConvertor.getNoOp(UomId.ofRepoId(uomRepoId));
		this.uom = () -> lookupFactory
				.searchInTableLookup(I_C_UOM.Table_Name)
				.findById(convertor.getTargetUomId());

		this.manufacturer = () -> lookupFactory
				.searchInTableLookup(I_C_BPartner.Table_Name)
				.findById(productRecord.getManufacturer_ID());

		this.packageSize = productRecord.getPackageSize();

		this.date = date;

		this.includedRows = ImmutableList.of();

		this.pmmQtyPromised = Quantity.toBigDecimal(convertor.convert(pmmQtyPromised));
		this.qtyDemandSalesOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandSalesOrder));
		this.qtyDemandDDOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandDDOrder));
		this.qtyDemandSum = Quantity.toBigDecimal(convertor.convert(qtyDemandSum));
		this.qtyDemandPPOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandPPOrder));
		this.qtyMaterialentnahme = Quantity.toBigDecimal(convertor.convert(qtyMaterialentnahme));

		this.qtySupplyPurchaseOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyPurchaseOrder));
		this.qtySupplyPPOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyPPOrder));
		this.qtySupplyDDOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyDDOrder));
		this.qtySupplySum = Quantity.toBigDecimal(convertor.convert(qtySupplySum));
		this.qtySupplyRequired = Quantity.toBigDecimal(convertor.convert(qtySupplyRequired));
		this.qtySupplyToSchedule = Quantity.toBigDecimal(convertor.convert(qtySupplyToSchedule));

		this.qtyStockCurrent = null;
		this.qtyOnHandStock = Quantity.toBigDecimal(convertor.convert(qtyOnHandStock));
		this.qtyExpectedSurplus = Quantity.toBigDecimal(convertor.convert(qtyExpectedSurplus));
		this.qtyStockEstimateCount = Quantity.toBigDecimal(convertor.convert(qtyStockEstimateCount));
		this.qtyStockEstimateTime = qtyStockEstimateTime;
		this.qtyStockEstimateSeqNo = qtyStockEstimateSeqNo;
		this.qtyInventoryCount = Quantity.toBigDecimal(convertor.convert(qtyInventoryCount));
		this.qtyInventoryTime = qtyInventoryTime;

		this.allIncludedCockpitRecordIds = ImmutableSet.copyOf(allIncludedCockpitRecordIds);
		this.allIncludedStockRecordIds = ImmutableSet.copyOf(allIncludedStockRecordIds);
	}

	@lombok.Builder(builderClassName = "CountingSubRowBuilder", builderMethodName = "countingSubRowBuilder")
	private MaterialCockpitRow(
			final int productId,
			final HuPackingInstructionsVersionId huPackingInstructionsVersionId,
			final LocalDate date,
			final int plantId,
			@Nullable final Quantity qtyStockEstimateCount,
			@Nullable final Instant qtyStockEstimateTime,
			@Nullable final Integer qtyStockEstimateSeqNo,
			@Nullable final Quantity qtyInventoryCount,
			@Nullable final Instant qtyInventoryTime,
			@Nullable final Quantity qtyStockCurrent,
			@Nullable final Quantity qtyOnHandStock,
			@NonNull final Set<Integer> allIncludedCockpitRecordIds,
			@NonNull final Set<Integer> allIncludedStockRecordIds,
			@Nullable final QtyTUConvertor.QtyTUConvertorBuilder qtyConvertorBuilder)
	{
		this.rowType = DefaultRowType.Line;

		this.dimensionGroupOrNull = null;

		final String plantName;
		if (plantId > 0)
		{
			final I_S_Resource plant = loadOutOfTrx(plantId, I_S_Resource.class);
			plantName = plant.getName();
		}
		else
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			plantName = msgBL.getMsg(Env.getCtx(), "de.metas.ui.web.material.cockpit.MaterialCockpitRow.No_Plant_Info");
		}
		this.documentId = DocumentId.of(DOCUMENT_ID_JOINER.join(
				"countingRow",
				date,
				productId,
				plantName));

		this.documentPath = DocumentPath.rootDocumentPath(
				MaterialCockpitUtil.WINDOWID_MaterialCockpitView,
				documentId);

		this.productId = productId;

		final I_M_Product productRecord = loadOutOfTrx(productId, I_M_Product.class);
		this.productValue = productRecord.getValue();
		this.productName = productRecord.getName();
		this.productCategoryOrSubRowName = plantName;

		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;

		final int uomRepoId = CoalesceUtil.firstGreaterThanZero(productRecord.getPackage_UOM_ID(), productRecord.getC_UOM_ID());
		final I_M_HU_PI_Item_Product huPiItemProduct = getHuPiItemProduct(ProductId.ofRepoId(productId), huPackingInstructionsVersionId);
		final QtyConvertor convertor = qtyConvertorBuilder != null && huPiItemProduct != null
				? qtyConvertorBuilder.packingInstruction(huPiItemProduct).build()
				: QtyConvertor.getNoOp(UomId.ofRepoId(uomRepoId));
		this.uom = () -> lookupFactory
				.searchInTableLookup(I_C_UOM.Table_Name)
				.findById(convertor.getTargetUomId());

		this.manufacturer = () -> lookupFactory
				.searchInTableLookup(I_C_BPartner.Table_Name)
				.findById(productRecord.getManufacturer_ID());

		this.packageSize = productRecord.getPackageSize();

		this.date = date;
		this.includedRows = ImmutableList.of();

		this.pmmQtyPromised = null;
		this.qtyDemandSalesOrder = null;
		this.qtyDemandDDOrder = null;
		this.qtyDemandSum = null;
		this.qtySupplyPPOrder = null;
		this.qtySupplyPurchaseOrder = null;
		this.qtyMaterialentnahme = null;
		this.qtyDemandPPOrder = null;
		this.qtySupplyDDOrder = null;
		this.qtySupplySum = null;
		this.qtySupplyRequired = null;
		this.qtySupplyToSchedule = null;
		this.qtyStockCurrent = Quantity.toBigDecimal(convertor.convert(qtyStockCurrent));
		this.qtyOnHandStock = Quantity.toBigDecimal(convertor.convert(qtyOnHandStock));
		this.qtyExpectedSurplus = null;
		this.qtyStockEstimateCount = Quantity.toBigDecimal(convertor.convert(qtyStockEstimateCount));
		this.qtyStockEstimateTime = qtyStockEstimateTime;
		this.qtyStockEstimateSeqNo = qtyStockEstimateSeqNo;
		this.qtyInventoryCount = Quantity.toBigDecimal(convertor.convert(qtyInventoryCount));
		this.qtyInventoryTime = qtyInventoryTime;

		this.allIncludedCockpitRecordIds = ImmutableSet.copyOf(allIncludedCockpitRecordIds);
		this.allIncludedStockRecordIds = ImmutableSet.copyOf(allIncludedStockRecordIds);
	}

	@Override
	public List<MaterialCockpitRow> getIncludedRows()
	{
		return includedRows;
	}

	@Override
	public DocumentId getId()
	{
		return documentId;
	}

	@Override
	public IViewRowType getType()
	{
		return rowType;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	/**
	 * Return false, because with true, all rows are "grayed" out. This does not mean that the rows are editable.
	 */
	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Override
	public ImmutableSet<String> getFieldNames()
	{
		return values.getFieldNames();
	}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
	{
		return values.get(this);
	}
}
