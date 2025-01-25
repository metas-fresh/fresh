package de.metas.tax.api.impl;

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.cache.annotation.CacheCtx;
import de.metas.common.util.StringUtils;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.CountryId;
import de.metas.location.ICountryAreaBL;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.organization.IFiscalRepresentationBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxQuery;
import de.metas.tax.api.TaxUtils;
import de.metas.tax.api.TypeOfDestCountry;
import de.metas.tax.model.I_C_VAT_SmallBusiness;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.ExemptTaxNotFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.X_C_Tax;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;
import static de.metas.tax.api.TypeOfDestCountry.DOMESTIC;
import static de.metas.tax.api.TypeOfDestCountry.OUTSIDE_COUNTRY_AREA;
import static de.metas.tax.api.TypeOfDestCountry.WITHIN_COUNTRY_AREA;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class TaxDAO implements ITaxDAO
{
	private final static Logger logger = LogManager.getLogger(TaxDAO.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final ICountryAreaBL countryAreaBL = Services.get(ICountryAreaBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IFiscalRepresentationBL fiscalRepresentationBL = Services.get(IFiscalRepresentationBL.class);

	@Override
	public Tax getTaxById(final int taxRepoId)
	{
		return getTaxById(TaxId.ofRepoId(taxRepoId));
	}

	@Override
	public Tax getTaxById(@NonNull final TaxId taxId)
	{
		return TaxUtils.from(loadOutOfTrx(taxId, I_C_Tax.class));
	}

	@Override
	@Nullable
	public Tax getTaxByIdOrNull(final int taxRepoId)
	{
		if (taxRepoId <= 0)
		{
			return null;
		}

		return TaxUtils.from(loadOutOfTrx(taxRepoId, I_C_Tax.class));
	}

	@Override
	@Cached(cacheName = I_C_VAT_SmallBusiness.Table_Name + "#By#C_BPartner_ID#Date")
	public boolean retrieveIsTaxExemptSmallBusiness(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final Timestamp date)
	{
		return queryBL.createQueryBuilder(I_C_VAT_SmallBusiness.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_VAT_SmallBusiness.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.addCompareFilter(I_C_VAT_SmallBusiness.COLUMNNAME_ValidFrom, Operator.LESS_OR_EQUAL, date)
				.addCompareFilter(I_C_VAT_SmallBusiness.COLUMNNAME_ValidTo, Operator.GREATER_OR_EQUAL, date)
				.create()
				.anyMatch();
	}

	@Override
	public TaxId retrieveExemptTax(@NonNull final OrgId orgId)
	{
		final int C_Tax_ID = queryBL.createQueryBuilder(I_C_Tax.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Tax.COLUMNNAME_IsTaxExempt, true)
				.addEqualsFilter(I_C_Tax.COLUMNNAME_AD_Org_ID, orgId)
				.orderByDescending(I_C_Tax.COLUMNNAME_Rate)
				.create()
				.firstId();
		if (C_Tax_ID <= 0)
		{
			throw new ExemptTaxNotFoundException(orgId.getRepoId());
		}
		return TaxId.ofRepoId(C_Tax_ID);
	}

	@Override
	public Tax getDefaultTax(@NonNull final TaxCategoryId taxCategoryId)
	{
		final List<I_C_Tax> list = queryBL.createQueryBuilderOutOfTrx(I_C_Tax.class)
				.addEqualsFilter(I_C_Tax.COLUMNNAME_C_TaxCategory_ID, taxCategoryId)
				.addEqualsFilter(I_C_Tax.COLUMNNAME_IsDefault, true)
				.create()
				.list();
		if (list.size() == 1)
		{
			return TaxUtils.from(list.get(0));
		}
		else
		{
			// Error - should only be one default
			throw new AdempiereException("TooManyDefaults");
		}
	}

	@Override
	@Cached(cacheName = I_C_Tax.Table_Name + "#NoTaxFound")
	public TaxId retrieveNoTaxFoundId(@CacheCtx final Properties ctx)
	{
		final I_C_Tax tax = queryBL.createQueryBuilder(I_C_Tax.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Tax.COLUMNNAME_C_Tax_ID, Tax.C_TAX_ID_NO_TAX_FOUND)
				.create()
				.firstOnlyNotNull(I_C_Tax.class);

		return TaxId.ofRepoId(tax.getC_Tax_ID());
	}

	@Override
	@Cached(cacheName = I_C_TaxCategory.Table_Name + "#NoTaxFound")
	public I_C_TaxCategory retrieveNoTaxCategoryFound(@CacheCtx final Properties ctx)
	{
		return queryBL.createQueryBuilder(I_C_TaxCategory.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID, TaxCategoryId.NOT_FOUND)
				.create()
				.firstOnlyNotNull(I_C_TaxCategory.class);
	}

	@Override
	public int findTaxCategoryId(@NonNull final TaxCategoryQuery query)
	{
		final IQueryBL queryBL = this.queryBL;

		final IQueryBuilder<I_C_TaxCategory> queryBuilder = queryBL.createQueryBuilder(I_C_TaxCategory.class);

		final IQuery<I_C_Tax> querytaxes = queryBL.createQueryBuilder(I_C_Tax.class)
				.addInArrayFilter(I_C_Tax.COLUMNNAME_C_Country_ID, null, query.getCountryId())
				.create();

		return queryBuilder
				.addInArrayFilter(I_C_TaxCategory.COLUMN_VATType, query.getType().getValue(), null)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addInSubQueryFilter(I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID, I_C_Tax.COLUMNNAME_C_TaxCategory_ID, querytaxes)
				.orderBy(I_C_TaxCategory.COLUMNNAME_Name)
				.create()
				.firstId();
	}

	@Override
	public I_C_TaxCategory getTaxCategoryById(@NonNull final TaxCategoryId id)
	{
		return loadOutOfTrx(id, I_C_TaxCategory.class);
	}

	@Override
	public ITranslatableString getTaxCategoryNameById(@Nullable final TaxCategoryId id)
	{
		if (id == null)
		{
			return TranslatableStrings.anyLanguage("?");
		}

		final I_C_TaxCategory taxCategory = getTaxCategoryById(id);
		if (taxCategory == null)
		{
			return TranslatableStrings.anyLanguage("<" + id.getRepoId() + ">");
		}

		return InterfaceWrapperHelper.getModelTranslationMap(taxCategory)
				.getColumnTrl(I_C_TaxCategory.COLUMNNAME_Name, taxCategory.getName());
	}

	@Override
	public Optional<TaxCategoryId> getTaxCategoryIdByName(@NonNull final String name)
	{
		final TaxCategoryId taxCategoryId = queryBL.createQueryBuilder(I_C_TaxCategory.class)
				.addEqualsFilter(I_C_TaxCategory.COLUMN_Name, name)
				.create()
				.firstId(TaxCategoryId::ofRepoIdOrNull);

		return Optional.ofNullable(taxCategoryId);
	}

	@Override
	public Percent getRateById(@NonNull final TaxId taxId)
	{
		final Tax tax = getTaxById(taxId);
		return Percent.of(tax.getRate());
	}

	@Override
	@Nullable
	public Tax getBy(@NonNull final TaxQuery taxQuery)
	{
		final List<Tax> taxes = getTaxesFromQuery(taxQuery);

		if (taxes.size() > 1)
		{
			final Tax firstTax = taxes.get(0);
			final Tax secondTax = taxes.get(1);
			final boolean multipleTaxesWithSameSeq = Objects.equals(firstTax.getSeqNo(), secondTax.getSeqNo());
			if (multipleTaxesWithSameSeq)
			{
				throw new AdempiereException("Multiple taxes have the same seqNo: C_Tax_ID=" + TaxId.toRepoId(firstTax.getTaxId()) + " and C_Tax_ID=" + TaxId.toRepoId(secondTax.getTaxId()))
						.appendParametersToMessage()
						.setParameter("taxQuery", taxQuery);
			}
			Loggables.withLogger(logger, Level.INFO).addLog("Multiple C_Tax records {} match the search criteria. Returning the first record based on seqNo.", getTaxIds(taxes));
		}
		else if (taxes.size() == 1)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Exact match found: C_Tax_ID={}", taxes.get(0).getTaxId().getRepoId());
		}
		return taxes.isEmpty() ? null : taxes.get(0);
	}

	@NonNull
	private List<Tax> getTaxesFromQuery(@NonNull final TaxQuery taxQuery)
	{
		final IQueryBuilder<I_C_Tax> queryBuilder = getTaxQueryBuilder(taxQuery);
		return queryBuilder.create()
				.stream()
				.map(TaxUtils::from)
				.collect(Collectors.toList());
	}

	@NonNull
	private String getTaxIds(@NonNull final List<Tax> taxes)
	{
		return taxes.stream()
				.map(Tax::getTaxId)
				.map(TaxId::getRepoId)
				.map(Object::toString)
				.collect(Collectors.joining(", "));
	}

	@NonNull
	private IQueryBuilder<I_C_Tax> getTaxQueryBuilder(@NonNull final TaxQuery taxQuery)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		loggable.addLog("Using query {}", taxQuery);
		final IQueryBuilder<I_C_Tax> queryBuilder = queryBL.createQueryBuilder(I_C_Tax.class)
				.addOnlyActiveRecordsFilter();

		OrgId orgId = taxQuery.getOrgId();
		CountryId countryId = taxQuery.getFromCountryId();

		if (countryId != null)
		{
			loggable.addLog("C_Country_ID={} provided in query", countryId.getRepoId());
		}
		else
		{
			final WarehouseId warehouseId = taxQuery.getWarehouseId();
			if (warehouseId != null)
			{
				final OrgId warehouseOrgId = warehouseBL.getWarehouseOrgId(warehouseId);
				if (!Objects.equals(orgId, warehouseOrgId))
				{
					loggable.addLog("AD_Org_ID={} based on warehouse", warehouseOrgId.getRepoId());
					orgId = warehouseOrgId;
				}

				final CountryId warehouseCountryId = warehouseBL.getCountryId(warehouseId);
				if (warehouseCountryId != null)
				{
					loggable.addLog("C_Country_ID={} based on warehouse", warehouseCountryId.getRepoId());
					countryId = warehouseCountryId;
				}
			}
			else
			{
				loggable.addLog("Effective AD_Org_ID={} (or any)", orgId.getRepoId());
				queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY);
				countryId = bPartnerOrgBL.getOrgCountryId(orgId);
				loggable.addLog("C_Country_ID={} based on AD_Org_ID={}", countryId.getRepoId(), orgId.getRepoId());
			}
			if (countryId == null)
			{
				throw new AdempiereException("No country could be identified for AD_Org_ID=" + OrgId.toRepoId(orgId) + " and M_Warehouse_ID=" + WarehouseId.toRepoId(warehouseId))
						.appendParametersToMessage()
						.setParameter("taxQuery", taxQuery);
			}
		}

		final CountryId destCountryId = getCountryId(taxQuery);
		final TypeOfDestCountry typeOfDestCountry = getTypeOfDestCountry(countryId, destCountryId);

		final boolean euOneStopShop = orgDAO.isEUOneStopShop(orgId);
		if (euOneStopShop && WITHIN_COUNTRY_AREA.equals(typeOfDestCountry))
		{
			loggable.addLog("AD_Org_ID={} has IsEUOneStopShop=Y and typeOfDestCountry=WITHIN_COUNTRY_AREA; -> don't filter by origin C_Country_ID", orgId.getRepoId());
		}
		else
		{
			queryBuilder.addEqualsFilter(I_C_Tax.COLUMNNAME_C_Country_ID, countryId);
		}

		loggable.addLog("Effective AD_Org_ID={} (or any)", orgId.getRepoId());
		queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY);

		final Timestamp dateOfInterest = taxQuery.getDateOfInterest();
		queryBuilder.addCompareFilter(I_C_Tax.COLUMNNAME_ValidFrom, Operator.LESS_OR_EQUAL, dateOfInterest);
		queryBuilder.addCompareFilter(I_C_Tax.COLUMNNAME_ValidTo, Operator.GREATER_OR_EQUAL, dateOfInterest);
		loggable.addLog("Date of Interest<= {}", dateOfInterest);

		final TaxCategoryId taxCategoryId = taxQuery.getTaxCategoryId();
		if (taxCategoryId != null)
		{
			queryBuilder.addEqualsFilter(I_C_Tax.COLUMNNAME_C_TaxCategory_ID, taxCategoryId);
			loggable.addLog("C_TaxCategory_ID={}", taxCategoryId.getRepoId());
		}

		if (taxQuery.getSoTrx().isSales())
		{
			queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_SOPOType, X_C_Tax.SOPOTYPE_Both, X_C_Tax.SOPOTYPE_SalesTax);
			loggable.addLog("SOPOType is either {} or {}", X_C_Tax.SOPOTYPE_Both, X_C_Tax.SOPOTYPE_SalesTax);
		}
		else
		{
			queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_SOPOType, X_C_Tax.SOPOTYPE_Both, X_C_Tax.SOPOTYPE_PurchaseTax);
			loggable.addLog("SOPOType is either {} or {}", X_C_Tax.SOPOTYPE_Both, X_C_Tax.SOPOTYPE_PurchaseTax);
		}

		final BPartnerId bpartnerId = taxQuery.getBPartnerId();

		final I_C_BPartner bpartner = bPartnerDAO.getById(bpartnerId);

		final boolean bPartnerHasTaxCertificate = !Check.isBlank(bpartner.getVATaxID());
		loggable.addLog("BPartner has tax certificate={}", bPartnerHasTaxCertificate);
		queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_RequiresTaxCertificate, StringUtils.ofBoolean(bPartnerHasTaxCertificate), null);

		final boolean bpartnerIsSmallbusiness = retrieveIsTaxExemptSmallBusiness(bpartnerId, dateOfInterest);
		loggable.addLog("BPartner is a small business={}", bpartnerIsSmallbusiness);
		queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_IsSmallbusiness, StringUtils.ofBoolean(bpartnerIsSmallbusiness), null);

		// if (euOneStopShop && WITHIN_COUNTRY_AREA.equals(typeOfDestCountry) && !bPartnerHasTaxCertificate)
		// {
		// 	loggable.addLog("AD_Org_ID={} has IsEUOneStopShop=Y, typeOfDestCountry=WITHIN_COUNTRY_AREA and C_BPartner_ID={} has no tax certificate; -> filter by To_Country_ID={}",
		// 					orgId.getRepoId(), destCountryId.getRepoId(), bpartnerId.getRepoId());
		// 	queryBuilder.addEqualsFilter(I_C_Tax.COLUMNNAME_To_Country_ID, destCountryId);
		// }
		// else
		// {
		// 	loggable.addLog("AD_Org_ID={} has IsEUOneStopShop=N OR typeOfDestCountry!=WITHIN_COUNTRY_AREA OR C_BPartner_ID={} has a tax certificate; -> filter by To_Country_ID={} or NULL",
		// 					orgId.getRepoId(), destCountryId.getRepoId(), bpartnerId.getRepoId());
		loggable.addLog("Filter by To_Country_ID={} or NULL", destCountryId.getRepoId());
		queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_To_Country_ID, destCountryId, null);
		//}

		loggable.addLog("Type of dest country: {} or NULL", typeOfDestCountry);
		if (typeOfDestCountry != null)
		{
			queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_TypeOfDestCountry, typeOfDestCountry.getCode(), null);
		}

		final Timestamp fiscalRepresentationFromDate = coalesceNotNull(taxQuery.getDateOfInterest(), Env.getDate());
		final boolean hasFiscalRepresentation = fiscalRepresentationBL.hasFiscalRepresentation(destCountryId, orgId, fiscalRepresentationFromDate);
		loggable.addLog("BPartner has fiscal Representation = {}", hasFiscalRepresentation);
		queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_IsFiscalRepresentation, StringUtils.ofBoolean(hasFiscalRepresentation), null);

		queryBuilder.orderBy(I_C_Tax.COLUMNNAME_SeqNo);
		return queryBuilder;
	}

	@Nullable
	private TypeOfDestCountry getTypeOfDestCountry(@NonNull final CountryId countryId, @Nullable final CountryId toCountryId)
	{
		final TypeOfDestCountry typeOfDestCountry;
		if (countryId.equals(toCountryId))
		{
			typeOfDestCountry = DOMESTIC;
		}
		else if (toCountryId == null)
		{
			return null;
		}
		else
		{
			final String countryCode = countryDAO.retrieveCountryCode2ByCountryId(toCountryId);
			final boolean isEULocation = countryAreaBL.isMemberOf(Env.getCtx(),
					ICountryAreaBL.COUNTRYAREAKEY_EU,
					countryCode,
					Env.getDate());
			typeOfDestCountry = isEULocation ? WITHIN_COUNTRY_AREA : OUTSIDE_COUNTRY_AREA;
		}
		return typeOfDestCountry;
	}

	@NonNull
	private CountryId getCountryId(@NonNull final TaxQuery taxQuery)
	{
		if (taxQuery.getShippingCountryId() != null)
		{
			return taxQuery.getShippingCountryId();
		}

		final BPartnerLocationAndCaptureId bpartnerLocationId = taxQuery.getBPartnerLocationId();

		if (bpartnerLocationId == null)
		{
			throw new AdempiereException("Invalid TaxQuery! None of TaxQuery.ShippingCountryId or TaxQuery.BPartnerLocationId set!")
					.appendParametersToMessage()
					.setParameter("TaxQuery", taxQuery);
		}

		if (bpartnerLocationId.getLocationCaptureId() != null)
		{
			return locationDAO.getCountryIdByLocationId(bpartnerLocationId.getLocationCaptureId());
		}

		final I_C_BPartner_Location bpartnerLocation = bPartnerDAO.getBPartnerLocationByIdEvenInactive(bpartnerLocationId.getBpartnerLocationId());
		if (bpartnerLocation == null)
		{
			throw new AdempiereException("No location found for bpartnerLocationId: " + bpartnerLocationId);
		}

		return locationDAO.getCountryIdByLocationId(LocationId.ofRepoId(bpartnerLocation.getC_Location_ID()));
	}
}
