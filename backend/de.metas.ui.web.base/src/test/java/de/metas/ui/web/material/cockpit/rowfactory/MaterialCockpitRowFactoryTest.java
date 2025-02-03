package de.metas.ui.web.material.cockpit.rowfactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.dimension.DimensionSpec;
import de.metas.dimension.DimensionSpecGroup;
import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.dimension.model.I_DIM_Dimension_Spec_Attribute;
import de.metas.dimension.model.I_DIM_Dimension_Spec_AttributeValue;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.MaterialCockpitUtil;
import de.metas.ui.web.material.cockpit.QtyConvertorService;
import de.metas.ui.web.material.cockpit.rowfactory.MaterialCockpitRowFactory.CreateRowsRequest;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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

public class MaterialCockpitRowFactoryTest
{
	private static final BigDecimal TWELVE = new BigDecimal("12");
	private static final BigDecimal ELEVEN = new BigDecimal("11");
	private AttributesTestHelper attributesTestHelper;
	private MaterialCockpitRowFactory materialCockpitRowFactory;
	private I_M_Product product;
	private I_M_Attribute attr1;
	private I_M_Attribute attr2;
	private AttributeListValue attr1_value1;
	private AttributeListValue attr2_value1;
	private DimensionSpec dimensionSpec;
	private DimensionSpecGroup dimensionspecGroup_attr1_value1;
	private DimensionSpecGroup dimensionspecGroup_attr2_value1;
	private DimensionSpecGroup dimensionspecGroup_empty;
	private AttributeListValue attr2_value2;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		attributesTestHelper = new AttributesTestHelper();
		SpringContextHolder.registerJUnitBean(new QtyConvertorService());

		final I_M_Product_Category productCategory = newInstance(I_M_Product_Category.class);
		productCategory.setName("productCategoryName");
		save(productCategory);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		product = newInstance(I_M_Product.class);
		product.setValue("productValue");
		product.setName("productName");
		product.setIsStocked(true);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		save(product);

		attr1 = attributesTestHelper.createM_Attribute("test1", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attr1_value1 = attributesTestHelper.createM_AttributeValue(attr1, "test1_value1");

		attr2 = attributesTestHelper.createM_Attribute("test21", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		attr2_value1 = attributesTestHelper.createM_AttributeValue(attr2, "test2_value1");
		attr2_value2 = attributesTestHelper.createM_AttributeValue(attr2, "test2_value2");

		materialCockpitRowFactory = new MaterialCockpitRowFactory();
	}

	private void initDimensionSpec_notEmpty()
	{
		dimensionSpec = createDimensionSpec(attr1, attr1_value1, attr2);
		final List<DimensionSpecGroup> groups = dimensionSpec.retrieveGroups();

		assertThat(groups).hasSize(4);

		dimensionspecGroup_empty = groups.get(0);
		assertThat(dimensionspecGroup_empty.isEmptyGroup()).isTrue();

		dimensionspecGroup_attr1_value1 = groups.get(1);
		assertThat(dimensionspecGroup_attr1_value1.getAttributesKey()).isEqualTo(AttributesKey.ofAttributeValueIds(attr1_value1.getId()));

		dimensionspecGroup_attr2_value1 = groups.get(2);
		assertThat(dimensionspecGroup_attr2_value1.getAttributesKey())
				.as("dimensionspecGroup_attr1_value1 shall be \"test1_value1\", but is %s", dimensionspecGroup_attr1_value1.getAttributesKey())
				.isEqualTo(AttributesKey.ofAttributeValueIds(attr2_value1.getId()));

		final DimensionSpecGroup dimensionspecGroup_attr2_value2 = groups.get(3);
		assertThat(dimensionspecGroup_attr2_value2.getAttributesKey()).isEqualTo(AttributesKey.ofAttributeValueIds(attr2_value2.getId()));
	}

	private DimensionSpec createDimensionSpec(
			final I_M_Attribute attr1,
			final AttributeListValue attr1_value1,
			final I_M_Attribute attr2)
	{
		final I_DIM_Dimension_Spec dimSpec = newInstance(I_DIM_Dimension_Spec.class);
		dimSpec.setInternalName(MaterialCockpitUtil.DEFAULT_DIM_SPEC_INTERNAL_NAME);
		dimSpec.setIsIncludeEmpty(true);
		save(dimSpec);

		final I_DIM_Dimension_Spec_Attribute dimSpecAttr1 = newInstance(I_DIM_Dimension_Spec_Attribute.class);
		dimSpecAttr1.setDIM_Dimension_Spec(dimSpec);
		dimSpecAttr1.setM_Attribute_ID(attr1.getM_Attribute_ID());
		save(dimSpecAttr1);

		final I_DIM_Dimension_Spec_AttributeValue dimSpecAttributeValue = newInstance(I_DIM_Dimension_Spec_AttributeValue.class);
		dimSpecAttributeValue.setDIM_Dimension_Spec_Attribute(dimSpecAttr1);
		dimSpecAttributeValue.setM_AttributeValue_ID(attr1_value1.getId().getRepoId());
		save(dimSpecAttributeValue);

		final I_DIM_Dimension_Spec_Attribute dimSpecAttr2 = newInstance(I_DIM_Dimension_Spec_Attribute.class);
		dimSpecAttr2.setDIM_Dimension_Spec(dimSpec);
		dimSpecAttr2.setIsIncludeAllAttributeValues(true);
		dimSpecAttr2.setM_Attribute_ID(attr2.getM_Attribute_ID());
		save(dimSpecAttr2);

		return DimensionSpec.ofRecord(dimSpec);
	}

	/**
	 * Verifies that the {@link I_MD_Cockpit} and {@link I_MD_Stock} data rows are correctly applied to {@link MaterialCockpitRow}s based on the given dimension spec.
	 * In this test, we have a dimension spec with tree groups, on of them is empty.
	 */
	@Test
	public void createRows_contains_attribute_groups()
	{
		initDimensionSpec_notEmpty();

		final I_M_AttributeSetInstance asi1 = newInstance(I_M_AttributeSetInstance.class);
		save(asi1);
		final AttributeSetInstanceId asiId1 = AttributeSetInstanceId.ofRepoId(asi1.getM_AttributeSetInstance_ID());

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		attributeSetInstanceBL.getCreateAttributeInstance(asiId1, attr1_value1);
		attributeSetInstanceBL.getCreateAttributeInstance(asiId1, attr2_value1);

		final AttributesKey attributesKeyWithAttr1_and_attr2 = AttributesKeys
				.createAttributesKeyFromASIAllAttributes(asiId1)
				.get();

		final I_MD_Cockpit cockpitRecordWithAttributes = newInstance(I_MD_Cockpit.class);
		cockpitRecordWithAttributes.setM_Product_ID(product.getM_Product_ID());
		cockpitRecordWithAttributes.setDateGeneral(de.metas.common.util.time.SystemTime.asTimestamp());
		cockpitRecordWithAttributes.setAttributesKey(attributesKeyWithAttr1_and_attr2.getAsString());
		cockpitRecordWithAttributes.setQtySupply_PurchaseOrder(TEN);
		save(cockpitRecordWithAttributes);

		final AttributesKey attributesKey2 = AttributesKey.NONE;

		final I_MD_Cockpit cockpitRecordWithEmptyAttributesKey = newInstance(I_MD_Cockpit.class);
		cockpitRecordWithEmptyAttributesKey.setM_Product_ID(product.getM_Product_ID());
		cockpitRecordWithEmptyAttributesKey.setDateGeneral(SystemTime.asTimestamp());
		cockpitRecordWithEmptyAttributesKey.setAttributesKey(attributesKey2.getAsString());
		cockpitRecordWithEmptyAttributesKey.setQtySupply_PurchaseOrder(ONE);
		save(cockpitRecordWithEmptyAttributesKey);

		final I_M_Warehouse warehouseWithPlant = createWarehousewithPlant("plantName");

		final I_MD_Stock stockRecordWithAttributes = newInstance(I_MD_Stock.class);
		stockRecordWithAttributes.setM_Product_ID(product.getM_Product_ID());
		stockRecordWithAttributes.setAttributesKey(attributesKeyWithAttr1_and_attr2.getAsString());
		stockRecordWithAttributes.setM_Warehouse_ID(warehouseWithPlant.getM_Warehouse_ID());
		stockRecordWithAttributes.setQtyOnHand(ELEVEN);
		save(stockRecordWithAttributes);

		final I_MD_Stock stockRecordWithEmptyAttributesKey = newInstance(I_MD_Stock.class);
		stockRecordWithEmptyAttributesKey.setM_Product_ID(product.getM_Product_ID());
		stockRecordWithEmptyAttributesKey.setAttributesKey(attributesKey2.getAsString());
		stockRecordWithEmptyAttributesKey.setM_Warehouse_ID(warehouseWithPlant.getM_Warehouse_ID());
		stockRecordWithEmptyAttributesKey.setQtyOnHand(TWELVE);
		save(stockRecordWithEmptyAttributesKey);

		final LocalDate today = de.metas.common.util.time.SystemTime.asLocalDate();

		final CreateRowsRequest request = CreateRowsRequest.builder()
				.date(today)
				.productIdsToListEvenIfEmpty(ImmutableSet.of())
				.cockpitRecords(ImmutableList.of(cockpitRecordWithAttributes, cockpitRecordWithEmptyAttributesKey))
				.stockRecords(ImmutableList.of(stockRecordWithAttributes, stockRecordWithEmptyAttributesKey))
				.includePerPlantDetailRows(true) // without this, we would not get 4 but 3 included rows
				.build();

		// invoke method under test
		final List<MaterialCockpitRow> result = materialCockpitRowFactory.createRows(request);

		assertThat(result).hasSize(1);
		final MaterialCockpitRow mainRow = result.get(0);
		assertThat(mainRow.getQtyOnHandStock()).isEqualByComparingTo(ELEVEN.add(TWELVE));
		assertThat(mainRow.getQtySupplyPurchaseOrder()).isEqualByComparingTo(TEN.add(ONE));

		final List<MaterialCockpitRow> includedRows = mainRow.getIncludedRows();
		// there shall be 4 rows:
		// 2: the cockpitRecordWithAttributes and stockRecordWithAttributes are added to the matching non-empty groups of the dimension spec
		// 1: the cockpit and stock record with the empty attributesKey is added to the empty dimension spec group
		// 1: both stock records are added to a counting record
		assertThat(includedRows).hasSize(4);

		final MaterialCockpitRow emptyGroupRow = extractRowWithDimensionSpecGroup(includedRows, dimensionspecGroup_empty);
		assertThat(emptyGroupRow.getAllIncludedStockRecordIds()).contains(stockRecordWithEmptyAttributesKey.getMD_Stock_ID());
		assertThat(emptyGroupRow.getQtyOnHandStock()).isEqualByComparingTo(TWELVE);
		assertThat(emptyGroupRow.getQtySupplyPurchaseOrder()).isEqualByComparingTo(ONE);

		final MaterialCockpitRow attr1GroupRow = extractRowWithDimensionSpecGroup(includedRows, dimensionspecGroup_attr1_value1);
		assertThat(attr1GroupRow.getQtyOnHandStock()).isEqualByComparingTo(ELEVEN);
		assertThat(attr1GroupRow.getQtySupplyPurchaseOrder()).isEqualByComparingTo(TEN);

		final MaterialCockpitRow attr2GroupRow = extractRowWithDimensionSpecGroup(includedRows, dimensionspecGroup_attr2_value1);
		assertThat(attr2GroupRow.getQtyOnHandStock()).isEqualByComparingTo(ELEVEN);
		assertThat(attr2GroupRow.getQtySupplyPurchaseOrder()).isEqualByComparingTo(TEN);

		final MaterialCockpitRow countingRow = extractSingleCountingRow(includedRows);
		assertThat(countingRow.getQtyOnHandStock()).isEqualByComparingTo(ELEVEN.add(TWELVE));
		assertThat(countingRow.getProductCategoryOrSubRowName()).isEqualTo("plantName");
	}

	private I_M_Warehouse createWarehousewithPlant(@NonNull final String plantName)
	{
		final I_S_Resource plant = newInstance(I_S_Resource.class);
		plant.setName(plantName);
		save(plant);

		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setPP_Plant(plant);
		save(warehouse);
		return warehouse;
	}

	private static MaterialCockpitRow extractRowWithDimensionSpecGroup(
			@NonNull final List<MaterialCockpitRow> rows,
			@NonNull final DimensionSpecGroup dimensionspecGroup)
	{
		final String groupName = dimensionspecGroup.getGroupName().getDefaultValue();
		return rows.stream()
				.filter(row -> Objects.equals(row.getProductCategoryOrSubRowName(), groupName))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No row found matching: " + groupName)
						.appendParametersToMessage()
						.setParameter("dimensionspecGroup", dimensionspecGroup)
						.setParameter("rows", rows));
	}

	private static MaterialCockpitRow extractSingleCountingRow(
			@NonNull final List<MaterialCockpitRow> rows)
	{
		final MaterialCockpitRow emptyGroupRow = rows.stream()
				.filter(row -> row.getId().toString().startsWith("countingRow")

				)
				.findFirst().get();
		return emptyGroupRow;
	}

	@Test
	public void createEmptyRowBuckets()
	{
		initDimensionSpec_notEmpty();

		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		// invoke method under test
		final Map<MainRowBucketId, MainRowWithSubRows> result = materialCockpitRowFactory.createEmptyRowBuckets(
				ImmutableSet.of(productId),
				true);

		assertThat(result).hasSize(1);
		final MainRowBucketId productIdAndDate = MainRowBucketId.createPlainInstance(productId);
		assertThat(result).containsKey(productIdAndDate);
		final MainRowWithSubRows mainRowBucket = result.get(productIdAndDate);
		assertThat(mainRowBucket.getProductIdAndDate()).isEqualTo(productIdAndDate);

		final Map<DimensionSpecGroup, DimensionGroupSubRowBucket> subRowBuckets = mainRowBucket.getDimensionGroupSubRows();
		assertThat(subRowBuckets).hasSize(dimensionSpec.retrieveGroups().size());
		assertThat(subRowBuckets.keySet()).containsOnlyElementsOf(dimensionSpec.retrieveGroups());
	}

	/**
	 * Tests the scenario that there is no plant etc and that the dimension spec is "empty", i.e. does not specify and attribute or default group.
	 */
	@Test
	public void createRows_empty_dimension_spec_includePerPlantDetailRows()
	{
		final CreateRowsRequest request = setup(true);

		// when
		final List<MaterialCockpitRow> result = materialCockpitRowFactory.createRows(request);

		// then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getAllIncludedCockpitRecordIds()).contains(request.getCockpitRecords().get(0).getMD_Cockpit_ID());
		assertThat(result.get(0).getQtySupplyPurchaseOrder()).isEqualByComparingTo(TEN);
		assertThat(result.get(0).getQtyOnHandStock()).isEqualByComparingTo(ELEVEN);

		// this is the stockrecord-row. it's a dedicated subrow because of the includePerPlantDetailRows (even though the plant-id is zero)
		assertThat(result.get(0).getIncludedRows().get(0).getDimensionGroupOrNull()).isNull();
		assertThat(result.get(0).getIncludedRows().get(0).getQtySupplyPurchaseOrder()).isNull();
		assertThat(result.get(0).getIncludedRows().get(0).getQtyOnHandStock()).isEqualByComparingTo(ELEVEN);

		assertThat(result.get(0).getIncludedRows().get(1).getDimensionGroupOrNull()).isEqualTo(DimensionSpecGroup.OTHER_GROUP);
		assertThat(result.get(0).getIncludedRows().get(1).getQtySupplyPurchaseOrder()).isEqualByComparingTo(TEN);
		assertThat(result.get(0).getIncludedRows().get(1).getQtyOnHandStock()).isEqualByComparingTo(ZERO);
	}

	/**
	 * Tests the scenario that there is no plant etc and that the dimension spec is "empty", i.e. does not specify and attribute or default group.
	 */
	@Test
	public void createRows_empty_dimension_spec_dontIncludePerPlantDetailRows()
	{
		final CreateRowsRequest request = setup(false);

		// when
		final List<MaterialCockpitRow> result = materialCockpitRowFactory.createRows(request);

		// then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getAllIncludedCockpitRecordIds()).contains(request.getCockpitRecords().get(0).getMD_Cockpit_ID());
		assertThat(result.get(0).getQtySupplyPurchaseOrder()).isEqualByComparingTo(TEN);
		assertThat(result.get(0).getQtyOnHandStock()).isEqualByComparingTo(ELEVEN);

		assertThat(result.get(0).getIncludedRows().get(0).getDimensionGroupOrNull()).isEqualTo(DimensionSpecGroup.OTHER_GROUP);
		assertThat(result.get(0).getIncludedRows().get(0).getQtySupplyPurchaseOrder()).isEqualByComparingTo(TEN);
		assertThat(result.get(0).getIncludedRows().get(0).getQtyOnHandStock()).isEqualByComparingTo(ELEVEN);
	}

	private CreateRowsRequest setup(boolean includePerPlantDetailRows)
	{
		// given
		final I_DIM_Dimension_Spec dimSpec = newInstance(I_DIM_Dimension_Spec.class);
		dimSpec.setInternalName(MaterialCockpitUtil.DEFAULT_DIM_SPEC_INTERNAL_NAME);
		dimSpec.setIsIncludeEmpty(false);
		dimSpec.setIsIncludeOtherGroup(false);
		save(dimSpec);

		final I_M_AttributeSetInstance asi1 = newInstance(I_M_AttributeSetInstance.class);
		save(asi1);
		final AttributeSetInstanceId asiId1 = AttributeSetInstanceId.ofRepoId(asi1.getM_AttributeSetInstance_ID());

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		attributeSetInstanceBL.getCreateAttributeInstance(asiId1, attr1_value1);
		attributeSetInstanceBL.getCreateAttributeInstance(asiId1, attr2_value1);

		final AttributesKey attributesKeyWithAttr1_and_attr2 = AttributesKeys
				.createAttributesKeyFromASIAllAttributes(asiId1)
				.get();

		final I_MD_Cockpit cockpitRecordWithAttributes = newInstance(I_MD_Cockpit.class);
		cockpitRecordWithAttributes.setM_Product_ID(product.getM_Product_ID());
		cockpitRecordWithAttributes.setDateGeneral(de.metas.common.util.time.SystemTime.asTimestamp());
		cockpitRecordWithAttributes.setAttributesKey(attributesKeyWithAttr1_and_attr2.getAsString());
		cockpitRecordWithAttributes.setQtySupply_PurchaseOrder(TEN);
		save(cockpitRecordWithAttributes);

		final I_M_Warehouse warehouseWithoutPlant = newInstance(I_M_Warehouse.class);
		save(warehouseWithoutPlant);

		final I_MD_Stock stockRecordWithAttributes = newInstance(I_MD_Stock.class);
		stockRecordWithAttributes.setM_Product_ID(product.getM_Product_ID());
		stockRecordWithAttributes.setAttributesKey(attributesKeyWithAttr1_and_attr2.getAsString());
		stockRecordWithAttributes.setM_Warehouse_ID(warehouseWithoutPlant.getM_Warehouse_ID());
		stockRecordWithAttributes.setQtyOnHand(ELEVEN);
		save(stockRecordWithAttributes);

		final LocalDate today = de.metas.common.util.time.SystemTime.asLocalDate();

		return CreateRowsRequest.builder()
				.date(today)
				.productIdsToListEvenIfEmpty(ImmutableSet.of())
				.cockpitRecords(ImmutableList.of(cockpitRecordWithAttributes))
				.stockRecords(ImmutableList.of(stockRecordWithAttributes))
				.includePerPlantDetailRows(includePerPlantDetailRows)
				.build();
	}
}
