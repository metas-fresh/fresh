package de.metas.tax.api.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ICountryAreaBL;
import de.metas.location.ICountryDAO;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxNotFoundException;
import de.metas.tax.api.TaxQuery;
import de.metas.tax.api.TaxUtils;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.X_C_TaxCategory;
import org.compiere.util.DB;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Properties;

public class TaxBL implements de.metas.tax.api.ITaxBL
{
	private static final Logger log = LogManager.getLogger(TaxBL.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

	@Override
	public Tax getTaxById(final TaxId taxId)
	{
		return taxDAO.getTaxById(taxId);
	}

	/**
	 * Do not attempt to retrieve the C_Tax for an order (i.e invoicing is done at a different time - 1 year - from the order)<br>
	 * Also note that packaging material receipts don't have an order line and if this one had, no IC would be created for it by this handler.<br>
	 * Instead, always rely on taxing BL to bind the tax to the invoice candidate
	 * <p>
	 * Try to rely on the tax category from the pricing result<br>
	 * 07739: If that's not available, then throw an exception; don't attempt to retrieve the German tax because that method proved to return a wrong result
	 */
	@Override
	@NonNull
	public TaxId getTaxNotNull(
			@Nullable final Object model,
			@Nullable final TaxCategoryId taxCategoryId,
			final int productId,
			@NonNull final Timestamp shipDate,
			@NonNull final OrgId orgId,
			@Nullable final WarehouseId warehouseId,
			@NonNull final BPartnerLocationAndCaptureId shipBPartnerLocationId,
			@NonNull final SOTrx soTrx)
	{
		if (taxCategoryId != null)
		{
			final CountryId countryFromId;
			if (warehouseId != null)
			{
				countryFromId = Services.get(IWarehouseBL.class).getCountryId(warehouseId);
			}
			else
			{
				final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
				final CountryId orgCountryId = bPartnerOrgBL.getOrgCountryId(orgId);
				if (orgCountryId != null)
				{
					countryFromId = orgCountryId;
				}
				else
				{
					countryFromId = Services.get(ICountryDAO.class).getDefaultCountryId();
				}
			}

			final Tax tax = taxDAO.getBy(TaxQuery.builder()
					.fromCountryId(countryFromId)
					.orgId(orgId)
					.bPartnerLocationId(shipBPartnerLocationId)
					.dateOfInterest(shipDate)
					.taxCategoryId(taxCategoryId)
					.warehouseId(warehouseId)
					.soTrx(soTrx)
					.build());

			if (tax != null)
			{
				return tax.getTaxId();
			}
		}

		final AdempiereException ex = new AdempiereException(StringUtils.formatMessage(
				"Could not retrieve C_Tax_ID; will return the Tax-Not-Found-C_Tax_ID; Method paratmers:"
						+ "model= {}, taxCategoryId={}, productId={}, shipDate={}, adOrgId={}, "
						+ "warehouse={}, shipBPartnerLocationId={}, isSOTrx={}, trxName={}",
				model,
				taxCategoryId,
				productId,
				shipDate,
				orgId,
				warehouseId,
				shipBPartnerLocationId,
				soTrx.isSales()));
		log.warn("getTax - error: ", ex);

		// 07814
		// If we got here, it means that no tax was found to satisfy the conditions
		// In this case, the Tax_Not_Found placeholder will be returned
		return TaxId.ofRepoId(Tax.C_TAX_ID_NO_TAX_FOUND);
	}

	private int getGermanTax(final Properties ctx,
							 final ProductId productId,
							 final int chargeId,
							 final Timestamp billDate,
							 final Timestamp shipDate,
							 @NonNull final OrgId orgId,
							 final WarehouseId warehouseId,
							 final BPartnerLocationAndCaptureId shipBPLocationId,
							 final boolean isSOTrx)
	{
		//
		// If organization is tax exempted then we will return the Tax Exempt for that organization (03871)
		final I_C_BPartner orgBPartner = Services.get(IBPartnerDAO.class).retrieveOrgBPartner(ctx, orgId.getRepoId(), I_C_BPartner.class, ITrx.TRXNAME_None);
		log.debug("Org BP: {}", orgBPartner);

		final ICountryAreaBL countryAreaBL = Services.get(ICountryAreaBL.class);

		if (taxDAO.retrieveIsTaxExemptSmallBusiness(BPartnerId.ofRepoId(orgBPartner.getC_BPartner_ID()), billDate))
		{
			final int taxExemptId = taxDAO.retrieveExemptTax(orgId).getRepoId();
			log.debug("Org is tax exempted => C_Tax_ID={}", taxExemptId);
			return taxExemptId;
		}

		// Check Partner/Location
		log.debug("Ship BP_Location={}", shipBPLocationId);

		int taxId = 0;

		final CountryId shipToCountryId = Services.get(IBPartnerBL.class).getCountryId(shipBPLocationId);
		final String shipToCountryCode = Services.get(ICountryDAO.class).retrieveCountryCode2ByCountryId(shipToCountryId);
		final boolean isEULocation = countryAreaBL.isMemberOf(ctx,
				ICountryAreaBL.COUNTRYAREAKEY_EU,
				shipToCountryCode,
				billDate);

		final CountryId shipFromCountryId;
		if (warehouseId != null)
		{
			shipFromCountryId = Services.get(IWarehouseBL.class).getCountryId(warehouseId);
		}
		else
		{
			final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
			final CountryId orgCountryId = bPartnerOrgBL.getOrgCountryId(orgId);
			if (orgCountryId != null)
			{
				shipFromCountryId = orgCountryId;
			}
			else
			{
				shipFromCountryId = Services.get(ICountryDAO.class).getDefaultCountryId();
			}
		}

		// bp has tax certificate?
		final I_C_BPartner bp = Services.get(IBPartnerDAO.class).getById(shipBPLocationId.getBpartnerId());
		final boolean hasTaxCertificate = !Check.isEmpty(bp.getVATaxID(), true);

		// String sql = "SELECT DISTINCT t.C_Tax_ID,t.validFrom, t.To_Country_ID FROM C_Tax t,M_Product pr,C_Charge c " +
		// " WHERE t.validFrom < ? AND t.isActive='Y' AND t.C_Country_ID = 101";

		String sql = "SELECT t.C_Tax_ID, t.validFrom, t.To_Country_ID "
				// metas start rc: 03083: retrieve the C_TaxCategory for using it in the error message
				+ ", t.C_TaxCategory_ID " +
				// metas end
				"FROM C_Tax t " +
				"LEFT JOIN M_ProductPrice pp ON pp.C_TaxCategory_ID = t.C_TaxCategory_ID " +
				"LEFT JOIN M_Pricelist_Version plv on pp.M_Pricelist_Version_ID = plv.M_Pricelist_Version_ID " +
				"LEFT JOIN M_Pricelist pl on plv.M_Pricelist_ID = pl.M_Pricelist_ID " +
				"LEFT JOIN C_Charge c ON c.C_TaxCategory_ID = t.C_TaxCategory_ID " +
				"WHERE t.validFrom < ? AND t.isActive='Y' AND t.C_Country_ID = " + shipFromCountryId.getRepoId() + " ";

		if (CountryId.equals(shipToCountryId, shipFromCountryId))
		{
			sql += " AND t.To_Country_ID = " + shipToCountryId.getRepoId() + " ";
		}
		else if (isEULocation)
		{
			// To_Country_ID should be null for all other taxes
			sql += " AND (pl.C_Country_ID IS NULL OR pl.C_Country_ID = " + shipToCountryId.getRepoId() + ") ";
			// metas: Abweichungen zu EU finden wenn definiert
			sql += " AND (t.To_Country_ID IS NULL OR t.To_Country_ID = " + shipToCountryId.getRepoId() + ") ";
			sql += " AND t.IsToEULocation = 'Y' ";
			if (hasTaxCertificate)
			{
				sql += " AND t.RequiresTaxCertificate = 'Y' ";
			}
			else
			{
				sql += " AND t.RequiresTaxCertificate = 'N' ";
			}
		}
		else
		{
			// rest of the world
			sql += " AND (pl.C_Country_ID IS NULL OR pl.C_Country_ID = " + shipToCountryId.getRepoId() + ") ";
			// Abweichungen zu Drittland finden finden wenn definiert
			sql += " AND (t.To_Country_ID IS NULL OR t.To_Country_ID =" + shipToCountryId.getRepoId() + ") ";
			sql += " AND t.IsToEULocation = 'N' ";
		}
		// product or charge
		if (productId != null)
		{
			sql += " AND t.C_TaxCategory_ID = pp.C_TaxCategory_ID AND pp.M_Product_ID = ? ";
		}
		else
		{
			sql += " AND t.C_TaxCategory_ID = c.C_TaxCategory_ID AND c.C_Charge_ID = ? ";
		}

		if (orgId != null)
		{
			sql += " AND (t.AD_Org_ID=0 OR t.AD_Org_ID=" + orgId.getRepoId() + ") ";
		}

		sql += " ORDER BY t.AD_Org_ID DESC, t.To_Country_ID, t.validFrom DESC ";
		log.debug("getGermanTax - sql={}", sql);

		// get tax id
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// metas start: rc: 03083: get the retrieved C_TaxCategory
		TaxCategoryId taxCategoryId = null;
		// metas end: rc: 03083
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setTimestamp(1, billDate);
			if (productId != null)
			{
				pstmt.setInt(2, productId.getRepoId());
			}
			else
			{
				pstmt.setInt(2, chargeId);
			}

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				taxId = rs.getInt(1);
				taxCategoryId = TaxCategoryId.ofRepoId(rs.getInt(4));
			}
		}
		catch (final SQLException e)
		{
			log.error("getGermanTax - error: ", e);
			throw new DBException(e, sql, billDate, productId != null ? productId : chargeId);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		if (taxId <= 0)
		{
			throw TaxNotFoundException.builder()
					.productId(productId).chargeId(chargeId)
					.taxCategoryId(taxCategoryId)
					.isSOTrx(isSOTrx)
					.shipDate(shipDate)
					.shipFromCountryId(shipFromCountryId)
					.shipToCountryId(shipToCountryId)
					.billDate(billDate)
					.billFromC_Location_ID(null)
					.build();
		}
		return taxId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.adempiere.tax.api.ITaxBL#get(java.util.Properties, int, int, java.sql.Timestamp, java.sql.Timestamp, int, int, int, int, boolean)
	 */
	@Override
	public int get(final Properties ctx,
				   final int M_Product_ID,
				   final int C_Charge_ID,
				   final Timestamp billDate,
				   final Timestamp shipDate,
				   final int AD_Org_ID,
				   final int M_Warehouse_ID,
				   final BPartnerLocationAndCaptureId billBPLocationId,
				   final BPartnerLocationAndCaptureId shipBPLocationId,
				   final boolean IsSOTrx)
	{
		if (M_Product_ID > 0 || C_Charge_ID > 0)
		{
			return getGermanTax(
					ctx,
					ProductId.ofRepoIdOrNull(M_Product_ID),
					C_Charge_ID,
					billDate,
					shipDate,
					OrgId.ofRepoId(AD_Org_ID),
					WarehouseId.ofRepoIdOrNull(M_Warehouse_ID),
					shipBPLocationId,
					IsSOTrx);
		}
		else
		{
			return taxDAO.retrieveExemptTax(OrgId.ofRepoId(AD_Org_ID)).getRepoId();
		}
	}

	public BigDecimal calculateTax(final I_C_Tax tax, final BigDecimal amount, final boolean taxIncluded, final int scale)
	{
		return TaxUtils.from(tax).calculateTax(amount, taxIncluded, scale);
	}

	@Override
	public BigDecimal calculateBaseAmt(@NonNull final I_C_Tax tax, @NonNull final BigDecimal amount, final boolean taxIncluded, final int scale)
	{

		return TaxUtils.from(tax).calculateBaseAmt(amount, taxIncluded, scale);
	}

	@Override
	public void setupIfIsWholeTax(final I_C_Tax tax)
	{
		if (!tax.isWholeTax())
		{
			return;
		}

		tax.setRate(BigDecimal.valueOf(100));
		tax.setIsTaxExempt(false);
		tax.setIsDocumentLevel(true);
		// tax.setIsSalesTax(false); // does not matter
	}

	@Override
	public TaxCategoryId retrieveRegularTaxCategoryId()
	{
		final TaxCategoryId taxCategoryId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_TaxCategory.class)
				.addEqualsFilter(I_C_TaxCategory.COLUMN_VATType, X_C_TaxCategory.VATTYPE_RegularVAT)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy(I_C_TaxCategory.COLUMN_Name)
				.create()
				.firstId(TaxCategoryId::ofRepoIdOrNull);

		if (taxCategoryId == null)
		{
			throw new AdempiereException("No tax category found for Regular VATType");
		}

		return taxCategoryId;
	}

	@NonNull
	public Optional<TaxCategoryId> getTaxCategoryIdByInternalName(@NonNull final String internalName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_TaxCategory.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_TaxCategory.COLUMNNAME_InternalName, internalName)
				.create()
				.firstOnlyOptional(I_C_TaxCategory.class)
				.map(I_C_TaxCategory::getC_TaxCategory_ID)
				.map(TaxCategoryId::ofRepoId);
	}

	@Override
	public Tax getDefaultTax(final TaxCategoryId taxCategoryId)
	{
		return taxDAO.getDefaultTax(taxCategoryId);
	}

}
