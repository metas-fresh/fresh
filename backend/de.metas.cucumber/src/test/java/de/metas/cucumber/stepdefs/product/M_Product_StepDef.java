/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNEcleanupSS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.product;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.handlingunits.ClearanceStatus;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.rest_api.v2.product.ProductRestService;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.ORG_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.PRODUCT_CATEGORY_STANDARD_ID;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_C_Order.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_C_Order.COLUMNNAME_M_Product_ID;
import static org.compiere.model.I_M_Product.COLUMNNAME_IsStocked;
import static org.compiere.model.I_M_Product.COLUMNNAME_M_Product_Category_ID;
import static org.compiere.model.I_M_Product.COLUMNNAME_Value;

public class M_Product_StepDef
{
	private final M_Product_StepDefData productTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final M_Product_Category_StepDefData productCategoryTable;
	private final AD_Org_StepDefData orgTable;
	private final TestContext restTestContext;

	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final ProductRestService productRestService = SpringContextHolder.instance.getBean(ProductRestService.class);

	public M_Product_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final M_Product_Category_StepDefData productCategoryTable,
			@NonNull final AD_Org_StepDefData orgTable,
			@NonNull final TestContext restTestContext)
	{
		this.productTable = productTable;
		this.bpartnerTable = bpartnerTable;
		this.productCategoryTable = productCategoryTable;
		this.orgTable = orgTable;
		this.restTestContext = restTestContext;
	}

	@Given("metasfresh contains M_Products:")
	public void metasfresh_contains_m_product(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_Product.COLUMNNAME_M_Product_ID)
				.forEach(this::createM_Product);
	}

	@And("no product with value {string} exists")
	public void noProductWithCodeCodeExists(final String value)
	{
		final Optional<I_M_Product> product = Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMNNAME_Value, value)
				.create()
				.firstOnlyOptional(I_M_Product.class);

		if (product.isPresent())
		{
			Services.get(IQueryBL.class).createQueryBuilder(I_C_BPartner_Product.class)
					.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_M_Product_ID, product.get().getM_Product_ID())
					.create()
					.delete();

			Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class)
					.addEqualsFilter(I_M_Product.COLUMNNAME_Value, value)
					.create()
					.delete();
		}
	}

	@And("metasfresh contains C_BPartner_Products:")
	public void metasfreshContainsC_BPartner_Product(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final boolean isExcludedFromSale = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_BPartner_Product.COLUMNNAME_IsExcludedFromSale, false);
			final boolean isExcludedFromPurchase = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_BPartner_Product.COLUMNNAME_IsExcludedFromPurchase, false);
			final String exclusionFromSaleReason = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Product.COLUMNNAME_ExclusionFromSaleReason);
			final String exclusionFromPurchaseReason = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Product.COLUMNNAME_ExclusionFromPurchaseReason);
			final String productNumber = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Product.COLUMNNAME_ProductNo);
			final String upc = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_BPartner_Product.COLUMNNAME_UPC);

			final I_C_BPartner_Product bPartnerProduct = InterfaceWrapperHelper.newInstance(I_C_BPartner_Product.class);
			bPartnerProduct.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			bPartnerProduct.setM_Product_ID(productTable.get(productIdentifier).getM_Product_ID());
			bPartnerProduct.setC_BPartner_ID(bpartnerTable.get(bpartnerIdentifier).getC_BPartner_ID());
			bPartnerProduct.setUsedForVendor(true);
			bPartnerProduct.setUsedForCustomer(true);
			bPartnerProduct.setShelfLifeMinPct(0);
			bPartnerProduct.setShelfLifeMinDays(0);
			bPartnerProduct.setIsExcludedFromSale(isExcludedFromSale);
			bPartnerProduct.setIsExcludedFromPurchase(isExcludedFromPurchase);

			if (Check.isNotBlank(exclusionFromSaleReason))
			{
				bPartnerProduct.setExclusionFromSaleReason(exclusionFromSaleReason);
			}

			if (Check.isNotBlank(exclusionFromPurchaseReason))
			{
				bPartnerProduct.setExclusionFromPurchaseReason(exclusionFromPurchaseReason);
			}

			if (Check.isNotBlank(productNumber))
			{
				bPartnerProduct.setProductNo(productNumber);
			}

			if (Check.isNotBlank(upc))
			{
				bPartnerProduct.setUPC(upc);
			}

			final Boolean isCurrentVendor = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_BPartner_Product.COLUMNNAME_IsCurrentVendor, true);
			bPartnerProduct.setIsCurrentVendor(isCurrentVendor);

			InterfaceWrapperHelper.saveRecord(bPartnerProduct);
		}
	}

	@And("locate product by external identifier")
	public void locate_product_by_external_identifier(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			locate_product_by_external_identifier(tableRow);
		}
	}

	@And("verify product info")
	public void verify_product_info(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			verify_product_info(tableRow);
		}
	}

	@And("taxCategory {string} is updated to work with all productTypes")
	public void update_tax_category(@NonNull final String taxCategoryInternalName)
	{
		final Optional<TaxCategoryId> taxCategoryId = taxBL.getTaxCategoryIdByInternalName(taxCategoryInternalName);
		assertThat(taxCategoryId).as("Missing taxCategory for internalName=%s", taxCategoryInternalName).isPresent();

		final I_C_TaxCategory taxCategory = InterfaceWrapperHelper.loadOutOfTrx(taxCategoryId.get(), I_C_TaxCategory.class);
		taxCategory.setProductType(null);

		InterfaceWrapperHelper.saveRecord(taxCategory);
	}

	@And("load product by value:")
	public void load_product_by_value(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String value = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_Value);

			final I_M_Product loadedProduct = queryBL.createQueryBuilder(I_M_Product.class)
					.addEqualsFilter(COLUMNNAME_Value, value)
					.create()
					.firstOnlyNotNull(I_M_Product.class);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			productTable.put(productIdentifier, loadedProduct);
		}
	}

	@Given("update M_Product:")
	public void update_M_Product(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> productTableList = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : productTableList)
		{
			updateMProduct(dataTableRow);
		}
	}

	private void createM_Product(@NonNull DataTableRow tableRow)
	{
		final ValueAndName valueAndName = tableRow.suggestValueAndName();
		final I_M_Product productRecord = CoalesceUtil.coalesceSuppliersNotNull(
				() -> productDAO.retrieveProductByValue(valueAndName.getValue()),
				() -> newInstanceOutOfTrx(I_M_Product.class));

		final OrgId orgId = tableRow.getAsOptionalIdentifier(I_M_Product.COLUMNNAME_AD_Org_ID).map(orgTable::getId).orElse(StepDefConstants.ORG_ID);
		productRecord.setAD_Org_ID(orgId.getRepoId());

		productRecord.setValue(valueAndName.getValue());
		productRecord.setName(valueAndName.getName());

		final UomId uomId = tableRow.getAsOptionalString(I_C_UOM.COLUMNNAME_X12DE355)
				.map(X12DE355::ofNullableCode)
				.map(uomDAO::getUomIdByX12DE355)
				.orElse(UomId.EACH);
		productRecord.setC_UOM_ID(UomId.toRepoId(uomId));

		final ProductType productType = tableRow.getAsOptionalEnum(I_M_Product.COLUMNNAME_ProductType, ProductType.class).orElse(ProductType.Item);
		productRecord.setProductType(productType.getCode());

		productRecord.setIsStocked(tableRow.getAsOptionalBoolean(I_M_Product.COLUMNNAME_IsStocked).orElseTrue());
		tableRow.getAsOptionalEnum(I_M_Product.COLUMNNAME_HUClearanceStatus, ClearanceStatus.class)
				.ifPresent(clearanceStatus -> productRecord.setHUClearanceStatus(clearanceStatus.getCode()));

		final ProductCategoryId productCategoryId = tableRow.getAsOptionalIdentifier(COLUMNNAME_M_Product_Category_ID)
				.map(productCategoryTable::getId)
				.orElse(PRODUCT_CATEGORY_STANDARD_ID);
		productRecord.setM_Product_Category_ID(productCategoryId.getRepoId());

		tableRow.getAsOptionalString(I_M_Product.COLUMNNAME_Description).ifPresent(productRecord::setDescription);

		tableRow.getAsOptionalBigDecimal(I_M_Product.COLUMNNAME_Weight).ifPresent(productRecord::setWeight);

		final boolean isSold = tableRow.getAsOptionalBoolean(I_M_Product.COLUMNNAME_IsSold).orElseTrue();
		final boolean isPurchased = tableRow.getAsOptionalBoolean(I_M_Product.COLUMNNAME_IsPurchased).orElseTrue();

		productRecord.setIsSold(isSold);
		productRecord.setIsPurchased(isPurchased);

		InterfaceWrapperHelper.saveRecord(productRecord);

		productTable.putOrReplace(tableRow.getAsIdentifier(), productRecord);

		restTestContext.setIntVariableFromRow(tableRow, productRecord::getM_Product_ID);
	}

	private void locate_product_by_external_identifier(@NonNull final Map<String, String> tableRow)
	{
		final String externalIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "externalIdentifier");

		final Optional<ProductId> productIdOptional = productRestService.resolveProductExternalIdentifier(ExternalIdentifier.of(externalIdentifier), ORG_ID);

		assertThat(productIdOptional).isPresent();

		final I_M_Product productRecord = productDAO.getById(productIdOptional.get());

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		productTable.putOrReplace(productIdentifier, productRecord);
	}

	private void verify_product_info(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final String value = DataTableUtil.extractStringOrNullForColumnName(row, I_M_Product.COLUMNNAME_Value);
		final String name = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_Name);
		final String productType = DataTableUtil.extractStringForColumnName(row, I_M_Product.COLUMNNAME_ProductType);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(row, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId productUomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final String ean = DataTableUtil.extractStringOrNullForColumnName(row, I_M_Product.COLUMNNAME_UPC);
		final String gtin = DataTableUtil.extractStringOrNullForColumnName(row, I_M_Product.COLUMNNAME_GTIN);
		final String description = DataTableUtil.extractStringOrNullForColumnName(row, I_M_Product.COLUMNNAME_Description);
		final boolean isActive = DataTableUtil.extractBooleanForColumnName(row, I_M_Product.COLUMNNAME_IsActive);

		final I_M_Product productRecord = productTable.get(productIdentifier);

		assertThat(productRecord.getValue()).isEqualTo(value);
		assertThat(productRecord.getName()).isEqualTo(name);
		assertThat(productRecord.getProductType()).isEqualTo(getProductType(productType));
		assertThat(productRecord.getC_UOM_ID()).isEqualTo(productUomId.getRepoId());
		assertThat(productRecord.getUPC()).isEqualTo(ean);
		assertThat(productRecord.getGTIN()).isEqualTo(gtin);
		assertThat(productRecord.getDescription()).isEqualTo(description);
		assertThat(productRecord.isActive()).isEqualTo(isActive);
	}

	@NonNull
	private String getProductType(@NonNull final String type)
	{
		final String productType;
		switch (type)
		{
			case "SERVICE":
				productType = X_M_Product.PRODUCTTYPE_Service;
				break;
			case "ITEM":
				productType = X_M_Product.PRODUCTTYPE_Item;
				break;
			default:
				throw Check.fail("Unexpected type={}", type);
		}
		return productType;
	}

	private void updateMProduct(@NonNull final Map<String, String> tableRow)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);
		assertThat(productRecord).isNotNull();

		final String gtin = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_M_Product.COLUMNNAME_GTIN);

		if (Check.isNotBlank(gtin))
		{
			productRecord.setGTIN(gtin);
		}

		final Boolean isStocked = DataTableUtil.extractBooleanForColumnNameOrNull(tableRow, COLUMNNAME_IsStocked);
		if (isStocked != null)
		{
			productRecord.setIsStocked(isStocked);
		}

		saveRecord(productRecord);
	}
}
