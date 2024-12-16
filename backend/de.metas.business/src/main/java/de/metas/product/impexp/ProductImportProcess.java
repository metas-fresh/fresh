/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.product.impexp;

import com.google.common.collect.ImmutableMap;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.impexp.BPCreditLimitImportRequest;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.logging.LogManager;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.ProductPriceId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.IProductDAO;
import de.metas.product.IProductPlanningSchemaBL;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.I_M_ProductScalePrice;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_I_Product;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.X_I_Product;
import org.compiere.model.X_M_ProductPrice;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/**
 * Import {@link I_I_Product} to {@link I_M_Product}.
 *
 * @author tsa
 */
public class ProductImportProcess extends SimpleImportProcessTemplate<I_I_Product>
{
	private static final Logger log = LogManager.getLogger(ProductImportProcess.class);

	private static final String PARAM_M_PriceList_Version_ID = "M_PriceList_Version_ID";

	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IProductPlanningSchemaBL productPlanningSchemaBL = Services.get(IProductPlanningSchemaBL.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final ProductImportHelper productImporter;

	public ProductImportProcess()
	{
		productImporter = ProductImportHelper.newInstance().setProcess(this);
	}

	@Override
	public Class<I_I_Product> getImportModelClass()
	{
		return I_I_Product.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Product.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return org.compiere.model.I_M_Product.Table_Name;
	}

	@Override
	protected Map<String, Object> getImportTableDefaultValues()
	{
		return ImmutableMap.<String, Object> builder()
				.put(I_I_Product.COLUMNNAME_ProductType, X_I_Product.PRODUCTTYPE_Item)
				.build();
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		MProductImportTableSqlUpdater.builder()
				.selection(selection)
				.ctx(getCtx())
				.tableName(getImportTableName())
				.valueName(I_I_Product.COLUMNNAME_Value)
				.build()
				.updateIProduct();
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Product.COLUMNNAME_ProductCategory_Value;
	}

	@Override
	protected I_I_Product retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_Product(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	private ProductImportContext createNewContext(final boolean insertOnly)
	{
		final ProductsCache productsCache = ProductsCache.builder()
				.productsRepo(productsRepo)
				.build();

		return ProductImportContext.builder()
				.productsCache(productsCache)
				.insertOnly(insertOnly)
				.build();
	}

	private ImportRecordResult importOrUpdateProduct(final ProductImportContext context)
	{
		final ImportRecordResult bpartnerImportResult;

		final boolean productExists = context.isCurrentProductIdSet();

		if (context.isInsertOnly() && productExists)
		{
			//  do not update existing entries
			return ImportRecordResult.Nothing;
		}

		bpartnerImportResult = productExists ? ImportRecordResult.Updated : ImportRecordResult.Inserted;

		productImporter.importRecord(context);
		return bpartnerImportResult;
	}

	@Override
	protected ImportRecordResult importRecord(
			final @NonNull IMutable<Object> state,
			final @NonNull I_I_Product importRecord,
			final boolean isInsertOnly)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		// First line to import or this line does NOT have the same product value
		// => create a new Product or update the existing one
		ProductImportContext context = (ProductImportContext)state.getValue();
		final ImportRecordResult productImportResult;


		if (context == null || !context.isSameProduct(importRecord))
		{
<<<<<<< HEAD
			// #4994 do not update
			return ImportRecordResult.Nothing;
		}
		// Product
		if (newProduct)			// Insert new Product
		{
			final I_M_Product product = createMProduct(importRecord);
			if (Check.isEmpty(product.getName()))
			{
				product.setName(product.getValue());
			}
			save(product);
			M_Product_ID = product.getM_Product_ID();
			importRecord.setM_Product_ID(M_Product_ID);
			log.trace("Insert Product");
=======
			context = createNewContext(isInsertOnly);
			context.setCurrentImportRecord(importRecord);
			state.setValue(context);

			productImportResult = importOrUpdateProduct(context);
>>>>>>> a8efc465e0f (Improve importing products (#19205))
		}
		else
		{
<<<<<<< HEAD
			final String sqlt = DB.convertSqlToNative("UPDATE M_PRODUCT "
					+ "SET (Value,Name,Description,DocumentNote,Help,"
					+ "Package_UOM_ID, PackageSize, IsSold, IsStocked, "
					+ "UPC,SKU,C_UOM_ID,M_Product_Category_ID,Classification,ProductType,"
					+ "Volume,Weight,NetWeight,ShelfWidth,ShelfHeight,ShelfDepth,UnitsPerPallet,"
					+ "Discontinued,DiscontinuedBy,Updated,UpdatedBy, "
					+ "RawMaterialOrigin_ID, M_CustomsTariff_ID"
					+ ", " + I_I_Product.COLUMNNAME_M_ProductPlanningSchema_Selector // #3406
					+ ")= "
					+ "(SELECT Value,coalesce(I_Product.Name, I_Product.Value),Description,DocumentNote,Help,"
					+ "Package_UOM_ID, PackageSize, IsSold, IsStocked, "
					+ "UPC,SKU,C_UOM_ID,M_Product_Category_ID,Classification,ProductType,"
					+ "Volume,Weight,NetWeight,ShelfWidth,ShelfHeight,ShelfDepth,UnitsPerPallet,"
					+ "Discontinued,DiscontinuedBy,now(),UpdatedBy, "
					+ "RawMaterialOrigin_ID, M_CustomsTariff_ID"
					+ ", " + I_M_Product.COLUMNNAME_M_ProductPlanningSchema_Selector // #3406
					+ " FROM I_Product WHERE I_Product_ID=" + I_Product_ID + ") "
					+ "WHERE M_Product_ID=" + M_Product_ID);
			PreparedStatement pstmt_updateProduct = null;
			try
=======
			final ProductId previousProductId = context.getCurrentProductIdOrNull();
			context.setCurrentImportRecord(importRecord);

			final ProductId productId = ProductId.ofRepoIdOrNull(importRecord.getM_Product_ID());
			if (previousProductId == null)
>>>>>>> a8efc465e0f (Improve importing products (#19205))
			{
				productImportResult = importOrUpdateProduct(context);
			}
			else if (productId == null || ProductId.equals(productId,previousProductId))
			{
				importRecord.setM_Product_ID(previousProductId.getRepoId());
				productImportResult = ImportRecordResult.Nothing;
			}
			else
			{
				throw new AdempiereException("Same Product value as previous line but not same Product linked");
			}
		}


		final ProductId productId = ProductId.ofRepoIdOrNull(importRecord.getM_Product_ID());
		if (productId != null)
		{
			// Price List
			createUpdateProductPrice(importRecord);

			// #3404 Create default product planning
			productPlanningSchemaBL.createDefaultProductPlanningsForAllProducts();
		}

		return productImportResult;
	}

	private void createUpdateProductPrice(final I_I_Product imp)
	{
		//
		// Get M_PriceList_Version_ID
		int priceListVersionId = imp.getM_PriceList_Version_ID();
		if (priceListVersionId <= 0)
		{
			priceListVersionId = getParameters().getParameterAsInt(PARAM_M_PriceList_Version_ID, -1);
		}
		if (priceListVersionId <= 0)
		{
			return;
		}

		//
		// Get C_TaxCategory
		final int taxCategoryId = imp.getC_TaxCategory_ID();
		if (taxCategoryId <= 0)
		{
			return;
		}

		//
		// Get UOM
		final int uomId = imp.getC_UOM_ID();
		if (uomId <= 0)
		{
			return;
		}

		//
		// Get M_Product_ID
		final ProductId productId = ProductId.ofRepoIdOrNull(imp.getM_Product_ID());
		Check.assumeNotNull(productId, "M_Product shall be set in {}", imp);

		//
		// Get prices
		final BigDecimal priceList = imp.getPriceList();
		final BigDecimal priceStd = imp.getPriceStd();
		final BigDecimal priceLimit = imp.getPriceLimit();

		//
		// Get/Create Product Price record
		final PriceListVersionId plvId = PriceListVersionId.ofRepoId(priceListVersionId);
		final I_M_ProductPrice pp = Optional
				.ofNullable(ProductPrices.retrieveMainProductPriceOrNull(plvId, productId, TaxCategoryId.ofRepoIdOrNull(taxCategoryId)))
				.orElseGet(() -> newInstance(I_M_ProductPrice.class));

		pp.setM_PriceList_Version_ID(priceListVersionId);    // FK
		pp.setM_Product_ID(productId.getRepoId()); // FK
		pp.setC_TaxCategory_ID(taxCategoryId);
		pp.setC_UOM_ID(uomId);

		save(pp);

		if (imp.isScalePrice())
		{
			pp.setUseScalePrice(X_M_ProductPrice.USESCALEPRICE_UseScalePriceStrict);

			final BigDecimal scalePriceBreak = imp.getQty();

			final I_M_ProductScalePrice productScalePrice = Optional
					.ofNullable(priceListDAO.retrieveScalePriceForExactBreak(ProductPriceId.ofRepoId(pp.getM_ProductPrice_ID()),
							scalePriceBreak))
					.orElseGet(() -> newInstance(I_M_ProductScalePrice.class));

			productScalePrice.setM_ProductPrice_ID(pp.getM_ProductPrice_ID());
			productScalePrice.setQty(scalePriceBreak);
			productScalePrice.setPriceLimit(priceLimit);
			productScalePrice.setPriceList(priceList);
			productScalePrice.setPriceStd(priceStd);

			save(productScalePrice);
		}

		else
		{
			pp.setPriceLimit(priceLimit);
			pp.setPriceList(priceList);
			pp.setPriceStd(priceStd);
		}

		save(pp);
	}
<<<<<<< HEAD

	private I_M_Product createMProduct(@NonNull final I_I_Product importRecord)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setAD_Org_ID(importRecord.getAD_Org_ID());
		//
		product.setValue(importRecord.getValue());
		product.setName(importRecord.getName());
		product.setDescription(importRecord.getDescription());
		product.setDocumentNote(importRecord.getDocumentNote());
		product.setHelp(importRecord.getHelp());
		product.setUPC(importRecord.getUPC());
		product.setExternalId(importRecord.getExternalId());
		product.setSKU(importRecord.getSKU());
		product.setC_UOM_ID(importRecord.getC_UOM_ID());
		product.setPackage_UOM_ID(importRecord.getPackage_UOM_ID());
		product.setPackageSize(importRecord.getPackageSize());
		product.setManufacturer_ID(importRecord.getManufacturer_ID());
		product.setM_Product_Category_ID(importRecord.getM_Product_Category_ID());
		product.setProductType(importRecord.getProductType());
		product.setImageURL(importRecord.getImageURL());
		product.setDescriptionURL(importRecord.getDescriptionURL());
		product.setIsSold(importRecord.isSold());
		product.setIsStocked(importRecord.isStocked());
		product.setNetWeight(importRecord.getNetWeight());
		product.setM_CustomsTariff_ID(importRecord.getM_CustomsTariff_ID());
		product.setRawMaterialOrigin_ID(importRecord.getRawMaterialOrigin_ID());
		product.setWeight(importRecord.getWeight());
		product.setM_ProductPlanningSchema_Selector(importRecord.getM_ProductPlanningSchema_Selector()); // #3406
		product.setTrademark(importRecord.getTrademark());
		product.setPZN(importRecord.getPZN());
		product.setIsCommissioned(importRecord.isCommissioned());
		product.setIsPurchased(importRecord.isPurchased());

		return product;
	}	// MProduct
=======
>>>>>>> a8efc465e0f (Improve importing products (#19205))
}
