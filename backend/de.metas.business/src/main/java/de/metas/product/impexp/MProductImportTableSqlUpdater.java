package de.metas.product.impexp;

import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.logging.LogManager;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_I_Product;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.Properties;

import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_ErrorMsg;
import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_IsImported;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * A helper class for {@link ProductImportProcess} that performs the "dirty" but efficient SQL updates on the {@link I_I_Product} table.
 * Those updates complements the data from existing metasfresh records and flag those import records that can't yet be imported.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class MProductImportTableSqlUpdater
{
	private static final Logger logger = LogManager.getLogger(MProductImportTableSqlUpdater.class);

	private final ImportRecordsSelection selection;
	private final Properties ctx;
	private final String targetTableName;
	private final String valueColumnName;

	private static final String priceName_KAEP = "KAEP";
	private static final String priceName_APU = "APU";
	private static final String priceName_AEP = "AEP";
	private static final String priceName_AVP = "AVP";
	private static final String priceName_UVP = "UVP";
	private static final String priceName_ZBV = "ZBV";

	@Builder
	private MProductImportTableSqlUpdater(
			@NonNull final ImportRecordsSelection selection,
			@NonNull final Properties ctx,
			@NonNull final String tableName,
			@NonNull final String valueName)
	{
		this.selection = selection;
		this.ctx = ctx;
		this.targetTableName = tableName;
		this.valueColumnName = valueName;
	}

	public void updateIProduct()
	{
		dbUpdateBPartners(selection);

		dbUpdateManufacturers(selection);

		dbUpdateProducts(selection);

		dbUpdateProductCategories(selection, ctx);

		dbUpdatePharmaProductCategory(selection);

		dbUpdateIProductFromProduct(selection);

		dbUpdateUOM(selection);

		dbUpdatePackageUOM(selection);

		dbUpdateCurrency(selection);

		dbUpdateVendorProductNo(selection);

		dbUpdateTaxCategories(selection, ctx);

		dbUpdatePriceListVersion(selection, ctx);

		dbUpdatePriceLists(selection, ctx, priceName_APU);

		dbUpdatePriceLists(selection, ctx, priceName_AEP);

		dbUpdateDosageForm(selection);

		dbUpdateIndication(selection);

		dbUpdateCustomsTarrif(selection);

		dbUpdateRawMaterialOrignCountry(selection);

		dbUpdateErrorMessages(selection);
	}

	public void updateIPharmaProduct()
	{
		dbUpdateProductsByValue(selection);
		dbUpdateProductsByExternalId(selection);
		dbUpdateProductCategoryForIFAProduct(selection);

		dbUpdatePackageUOM(selection);
		dbUpdateDosageForm(selection);
		dbUpdateManufacturersIFA(selection);

		dbUpdatePriceLists(selection, ctx, priceName_KAEP);
		dbUpdatePriceLists(selection, ctx, priceName_APU);
		dbUpdatePriceLists(selection, ctx, priceName_AEP);
		dbUpdatePriceLists(selection, ctx, priceName_AVP);
		dbUpdatePriceLists(selection, ctx, priceName_UVP);
		dbUpdatePriceLists(selection, ctx, priceName_ZBV);

		dbUpdateErrorMessagesIFA(selection);
	}

	private void dbUpdateBPartners(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p")
				.append(" WHERE i.BPartner_Value=p.Value AND i.AD_Client_ID=p.AD_Client_ID) ")
				.append("WHERE C_BPartner_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName)
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid BPartner,' ")
				.append("WHERE C_BPartner_ID IS NULL AND BPartner_Value IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause());
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateManufacturers(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET Manufacturer_ID=(SELECT C_BPartner_ID FROM C_BPartner p")
				.append(" WHERE i.ProductManufacturer ilike '%'||p.companyname||'%' AND i.AD_Client_ID=p.AD_Client_ID LIMIT 1) ")
				.append("WHERE Manufacturer_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateManufacturersIFA(@NonNull final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET Manufacturer_ID =(SELECT C_BPartner_ID FROM C_BPartner bp")
				.append(" WHERE bp.AD_Client_ID=i.AD_Client_ID AND i.A00ANBNR5::varchar = bp.IFA_Manufacturer) ")
				.append("WHERE Manufacturer_ID IS NULL AND A00ANBNR5 IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateProducts(@NonNull final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_Product_ID=(SELECT M_Product_ID FROM M_Product p")
				.append(" WHERE i.UPC=p.UPC AND i.AD_Client_ID=p.AD_Client_ID) ")
				.append("WHERE M_Product_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'")
				.append(selection.toSqlWhereClause("i"));
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Product Existing UPC={}", no);

		dbUpdateProductsByValue(selection);

		dbUpdateProductsByExternalId(selection);
	}

	private void dbUpdateProductsByValue(@NonNull final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_Product_ID=(SELECT M_Product_ID FROM M_Product p")
				.append(" WHERE i.").append(valueColumnName).append("=p.Value AND i.AD_Client_ID=p.AD_Client_ID) ")
				.append("WHERE M_Product_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'")
				.append(selection.toSqlWhereClause("i"));
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Product Existing Value={}", no);
	}

	private void dbUpdateProductsByExternalId(@NonNull final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_Product_ID=(SELECT M_Product_ID FROM M_Product p")
				.append(" WHERE i." + I_I_Product.COLUMNNAME_ExternalId + "=p.ExternalId AND i.AD_Client_ID=p.AD_Client_ID) ")
				.append("WHERE M_Product_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'")
				.append(selection.toSqlWhereClause("i"));
		final int no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Product Existing ExternalId={}", no);
	}

	private void dbUpdateProductCategories(@NonNull final ImportRecordsSelection selection, @NonNull final Properties ctx)
	{
		StringBuilder sql;
		int no;
		final int adClientId = Env.getAD_Client_ID(ctx);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET ProductCategory_Value=(SELECT MAX(Value) FROM M_Product_Category")
				.append(" WHERE IsDefault='Y' AND AD_Client_ID=").append(adClientId).append(") ")
				.append("WHERE ProductCategory_Value IS NULL AND M_Product_Category_ID IS NULL")
				.append(" AND M_Product_ID IS NULL")    // set category only if product not found
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Category Default Value={}", no);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_Product_Category_ID=(SELECT M_Product_Category_ID FROM M_Product_Category c")
				.append(" WHERE i.ProductCategory_Value=c.Value AND i.AD_Client_ID=c.AD_Client_ID) ")
				.append("WHERE ProductCategory_Value IS NOT NULL AND M_Product_Category_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Set Category={}", no);
	}

	private void dbUpdateIProductFromProduct(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;

		// Copy From Product if Import does not have value
		final String[] strFields = new String[] { "Value", "Name", "Description", "DocumentNote", "Help",
				"UPC", "SKU", "Classification", "ProductType",
				"Discontinued", "DiscontinuedBy", "ImageURL", "DescriptionURL" };
		for (final String strField : strFields)
		{
			sql = new StringBuilder("UPDATE ")
					.append(targetTableName + " i SET ")
					.append(strField).append(" = (SELECT ").append(strField).append(" FROM M_Product p"
							+ " WHERE i.M_Product_ID=p.M_Product_ID AND i.AD_Client_ID=p.AD_Client_ID) "
							+ "WHERE M_Product_ID IS NOT NULL"
							+ " AND ")
					.append(strField).append(" IS NULL"
							+ " AND " + COLUMNNAME_I_IsImported + "='N'")
					.append(selection.toSqlWhereClause("i"));
			no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			if (no != 0)
			{
				logger.debug(strField + " - default from existing Product=" + no);
			}
		}
		final String[] numFields = new String[] { "C_UOM_ID", "M_Product_Category_ID",
				"Volume", "Weight", "ShelfWidth", "ShelfHeight", "ShelfDepth", "UnitsPerPallet" };
		for (final String numField : numFields)
		{
			sql = new StringBuilder("UPDATE ")
					.append(targetTableName + " i SET ")
					.append(numField).append(" = (SELECT ").append(numField).append(" FROM M_Product p"
							+ " WHERE i.M_Product_ID=p.M_Product_ID AND i.AD_Client_ID=p.AD_Client_ID) "
							+ "WHERE M_Product_ID IS NOT NULL"
							+ " AND (")
					.append(numField).append(" IS NULL OR ").append(numField).append("=0)"
							+ " AND " + COLUMNNAME_I_IsImported + "='N'")
					.append(selection.toSqlWhereClause("i"));
			no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			if (no != 0)
			{
				logger.debug("{} default from existing Product={}", numField, no);
			}
		}
	}

	private void dbUpdateUOM(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;
		// Set UOM (System/own)
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET X12DE355 = ")
				.append("(SELECT MAX(X12DE355) FROM C_UOM u WHERE u.IsDefault='Y' AND u.AD_Client_ID IN (0,i.AD_Client_ID)) ")
				.append("WHERE X12DE355 IS NULL AND C_UOM_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set UOM Default={}", no);
		//
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET C_UOM_ID = (SELECT C_UOM_ID FROM C_UOM u WHERE upper(u.X12DE355)=upper(i.X12DE355) AND u.AD_Client_ID IN (0,i.AD_Client_ID) AND u.IsActive='Y' ORDER BY u.AD_Client_ID DESC, u.C_UOM_ID ASC LIMIT 1) ")
				.append("WHERE C_UOM_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Set UOM={}", no);
		//
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid UOM, ' ")
				.append("WHERE C_UOM_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdatePackageUOM(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET Package_UOM_ID = (SELECT C_UOM_ID FROM C_UOM u WHERE lower(u.uomsymbol)=lower(i.A00PGEINH) AND u.AD_Client_ID IN (0,i.AD_Client_ID) AND u.IsActive='Y' ORDER BY u.AD_Client_ID DESC, u.C_UOM_ID ASC LIMIT 1) ")
				.append("WHERE Package_UOM_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("Set Package_UOM ={}", no);
	}

	private void dbUpdateCurrency(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET ISO_Code=(SELECT ISO_Code FROM C_Currency c")
				.append(" INNER JOIN C_AcctSchema a ON (a.C_Currency_ID=c.C_Currency_ID)")
				.append(" INNER JOIN AD_ClientInfo ci ON (a.C_AcctSchema_ID=ci.C_AcctSchema1_ID)")
				.append(" WHERE ci.AD_Client_ID=i.AD_Client_ID) ")
				.append("WHERE C_Currency_ID IS NULL AND ISO_Code IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.debug("Set Currency Default={}", no);
		//
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET C_Currency_ID=(SELECT C_Currency_ID FROM C_Currency c")
				.append(" WHERE i.ISO_Code=c.ISO_Code AND c.AD_Client_ID IN (0,i.AD_Client_ID)) ")
				.append("WHERE C_Currency_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("doIt- Set Currency={}", no);
		//
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Currency,' ")
				.append("WHERE C_Currency_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateVendorProductNo(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		int no;

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET VendorProductNo=Value ")
				.append("WHERE C_BPartner_ID IS NOT NULL AND VendorProductNo IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "='N'")
				.append(selection.toSqlWhereClause("i"));
		no = DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		logger.info("VendorProductNo Set to Value={}", no);
	}

	private void dbUpdateTaxCategories(@NonNull final ImportRecordsSelection selection, @NonNull final Properties ctx)
	{
		StringBuilder sql;
		final int adClientId = Env.getAD_Client_ID(ctx);
		// Resolve C_TaxCategory_Name -> C_TaxCategory_ID
		{
			sql = new StringBuilder("UPDATE ")
					.append(targetTableName + " i ")
					.append(" set C_TaxCategory_ID=(select tc.C_TaxCategory_ID from C_TaxCategory tc "
									+ "where (tc.Name = i.C_TaxCategory_Name or tc.c_taxcategory_id = i.c_taxcategory_id) "
									+ "and tc.AD_Client_ID=")
					.append(adClientId)
					.append(" and tc.IsActive='Y' order by tc.C_TaxCategory_ID limit 1)")
					.append(" where true")
					.append(" and ").append(COLUMNNAME_I_IsImported).append("<>'Y'")
					.append(" and i.C_TaxCategory_Name is not null")
					.append(selection.toSqlWhereClause("i"));
			DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}

		// Set default C_TaxCategory_ID where it was not set
		{
			final TaxCategoryId taxCategoryId = Services.get(ITaxBL.class).retrieveRegularTaxCategoryId();
			sql = new StringBuilder("UPDATE ")
					.append(targetTableName + " i ")
					.append(" set C_TaxCategory_ID = ")
					.append(taxCategoryId.getRepoId())
					.append(" where true")
					.append(" and " + COLUMNNAME_I_IsImported + "<>'Y'")
					.append(" and i.C_TaxCategory_ID is null")
					.append(selection.toSqlWhereClause("i"));
			DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		}
	}

	private void dbUpdatePriceListVersion(@NonNull final ImportRecordsSelection selection, @NonNull final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" set M_PriceList_Version_ID=(select plv.M_PriceList_Version_ID from M_PriceList_Version plv ")
				.append(" where plv.Name=i.M_PriceList_Version_Name and plv.AD_Client_ID=")
				.append(adClientId)
				.append(" and plv.IsActive='Y' order by plv.M_PriceList_Version_ID limit 1)")
				.append(" where true")
				.append(" and " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(" and i.M_PriceList_Version_Name is not null and i.M_PriceList_Version_ID is null")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateDosageForm(@NonNull final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_DosageForm_ID =(SELECT M_DosageForm_ID FROM M_DosageForm d")
				.append(" WHERE d.AD_Client_ID=i.AD_Client_ID AND i.A00DARFO = d.Name) ")
				.append("WHERE M_DosageForm_ID IS NULL AND A00DARFO IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdatePharmaProductCategory(@NonNull final ImportRecordsSelection selection)
	{

		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_PharmaProductCategory_ID=(SELECT M_PharmaProductCategory_ID FROM M_PharmaProductCategory c")
				.append(" WHERE i.PharmaProductCategory_Name=c.Name AND i.AD_Client_ID=c.AD_Client_ID) ")
				.append("WHERE PharmaProductCategory_Name IS NOT NULL AND M_PharmaProductCategory_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));

		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateProductCategoryForIFAProduct(@NonNull final ImportRecordsSelection selection)
	{

		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_Product_Category_ID=(SELECT M_Product_Category_ID FROM M_Product_Category c")
				.append(" WHERE i.A00WGA=c.Value AND i.AD_Client_ID=c.AD_Client_ID) ")
				.append("WHERE A00WGA IS NOT NULL AND M_Product_Category_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));

		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateIndication(@NonNull final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_Indication_ID =(SELECT M_Indication_ID FROM M_Indication ind")
				.append(" WHERE ind.AD_Client_ID=i.AD_Client_ID AND i.M_Indication_Name = ind.Name) ")
				.append("WHERE M_Indication_ID IS NULL AND M_Indication_Name IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdatePriceLists(@NonNull final ImportRecordsSelection selection, @NonNull final Properties ctx, @NonNull final String nameToMatch)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i set ")
				.append(nameToMatch)
				.append("_Price_List_ID=(select pl.M_PriceList_ID from M_PriceList pl ")
				.append(" where pl.InternalName=?")
				.append(" and pl.AD_Client_ID=?")
				.append(" and pl.IsActive='Y' order by pl.M_PriceList_ID limit 1)")
				.append(" where true")
				.append(" and " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		final Object[] params = new Object[] { nameToMatch, adClientId };
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), params, ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateCustomsTarrif(@NonNull final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET M_CustomsTariff_ID =(SELECT M_CustomsTariff_ID FROM M_CustomsTariff tf")
				.append(" WHERE tf.AD_Client_ID=i.AD_Client_ID AND i.CustomsTariff = tf.value) ")
				.append("WHERE M_CustomsTariff_ID IS NULL AND i.CustomsTariff IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateRawMaterialOrignCountry(@NonNull final ImportRecordsSelection selection)
	{
		final StringBuilder sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET RawMaterialOrigin_ID =(SELECT C_Country_id FROM C_Country tf")
				.append(" WHERE i.RawMaterialOriginCountryCode = tf.CountryCode) ")
				.append("WHERE RawMaterialOrigin_ID IS NULL AND i.RawMaterialOriginCountryCode IS NOT NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateErrorMessages(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql;

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid ProdCategory,' ")
				.append("WHERE M_Product_Category_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid ProductType,' ")
				.append("WHERE ProductType NOT IN ('E','I','R','S')")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Value not unique,' ")
				.append("WHERE " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(" AND ").append(I_I_Product.COLUMNNAME_IsScalePrice).append(" <>'Y'")
<<<<<<< HEAD
				.append(" AND Value IN (SELECT Value FROM I_Product ii WHERE i.AD_Client_ID=ii.AD_Client_ID AND I_IsImported <> 'Y' GROUP BY Value, M_PriceList_Version_Name, C_TaxCategory_Name HAVING COUNT(*) > 1)")
=======
				.append(" AND Value IN (SELECT Value FROM I_Product ii WHERE i.AD_Client_ID=ii.AD_Client_ID GROUP BY Value, m_pricelist_version_name HAVING COUNT(*) > 1)")
>>>>>>> a8efc465e0f (Improve importing products (#19205))
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Scale Price not unique,' ")
				.append("WHERE " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(" AND ").append(I_I_Product.COLUMNNAME_IsScalePrice).append(" ='Y'")
				.append(" AND ").append(I_I_Product.COLUMNNAME_Qty).append(" IN (SELECT "
						+ I_I_Product.COLUMNNAME_Qty
						+ " FROM "
						+ I_I_Product.Table_Name
						+ " ii WHERE ii."
						+ I_I_Product.COLUMNNAME_Value
						+ " = i."
						+ I_I_Product.COLUMNNAME_Value
						+ " AND i."
						+ I_I_Product.COLUMNNAME_AD_Client_ID
						+ "=ii."
						+ I_I_Product.COLUMNNAME_AD_Client_ID
						+ " AND ii."
						+ I_I_Product.COLUMNNAME_IsScalePrice
						+ " = 'Y' AND ii."
						+ I_I_Product.COLUMNNAME_I_IsImported
						+ " <>'Y'"
						+ " AND ii."
						+ I_I_Product.COLUMNNAME_I_Product_ID
						+ " <> i."
						+ I_I_Product.COLUMNNAME_I_Product_ID
						+ ")"
				)
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
		//
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=UPC not unique,' ")
				.append("WHERE " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(" AND ").append(I_I_Product.COLUMNNAME_IsScalePrice).append(" <>'Y'")
				.append(" AND UPC IN (SELECT UPC FROM I_Product ii WHERE i.AD_Client_ID=ii.AD_Client_ID GROUP BY UPC, value, m_pricelist_version_name HAVING COUNT(*) > 1)")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=No Mandatory Value,' ")
				.append("WHERE Value IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);


		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=VendorProductNo not unique,' ")
				.append("WHERE " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(" AND C_BPartner_ID IS NOT NULL")
				.append(" AND (C_BPartner_ID, VendorProductNo) IN ")
				.append(" (SELECT C_BPartner_ID, VendorProductNo FROM I_Product ii WHERE i.AD_Client_ID=ii.AD_Client_ID GROUP BY C_BPartner_ID, VendorProductNo, value, m_pricelist_version_name HAVING COUNT(*) > 1)")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=No mandatory Pharma product category Name,' ")
				.append("WHERE PharmaProductCategory_Name IS NOT NULL ")
				.append("AND M_PharmaProductCategory_ID IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	private void dbUpdateErrorMessagesIFA(@NonNull final ImportRecordsSelection selection)
	{
		StringBuilder sql;
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid ProdCategory,' ")
				.append("WHERE M_Product_Category_ID IS NULL AND A00SSATZ = '1'") // category shall be mandatory only when is new product
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=A00PZN is mandatory,' ")
				.append("WHERE A00PZN IS NULL")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Package UOM,' ")
				.append("WHERE Package_UOM_ID IS NULL and i.A00PGEINH IS NOT NULL ")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);

		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + COLUMNNAME_I_IsImported + "='E', " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||'ERR=Invalid Dosage Form,' ")
				.append("WHERE M_DosageForm_ID IS NULL and (i.A00DARFO IS NOT NULL and i.A00DARFO <> '---')")
				.append(" AND " + COLUMNNAME_I_IsImported + "<>'Y'")
				.append(selection.toSqlWhereClause("i"));
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

	public static void dbUpdateIsPriceCopiedToYes(@NonNull final String targetTableName, @NonNull final String columnName)
	{
		StringBuilder sql;
		sql = new StringBuilder("UPDATE ")
				.append(targetTableName + " i ")
				.append(" SET " + columnName + " = 'Y' ")
				.append(" WHERE coalesce(" + columnName + ", 'N') != 'Y'");
		DB.executeUpdateAndThrowExceptionOnFail(sql.toString(), ITrx.TRXNAME_ThreadInherited);
	}

}
