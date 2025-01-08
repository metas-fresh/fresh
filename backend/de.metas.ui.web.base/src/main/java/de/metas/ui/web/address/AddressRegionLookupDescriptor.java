package de.metas.ui.web.address;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.cache.CCacheStats;
import de.metas.location.ICountryDAO;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlForFetchingLookups;
import de.metas.ui.web.window.model.lookup.IdsToFilter;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext.Builder;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Region;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Env;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class AddressRegionLookupDescriptor implements LookupDescriptor, LookupDataSourceFetcher
{
	public static AddressRegionLookupDescriptor newInstance()
	{
		return new AddressRegionLookupDescriptor();
	}

	private static final Optional<String> LookupTableName = Optional.of(I_C_Region.Table_Name);
	private static final String CACHE_PREFIX = I_C_Region.Table_Name;
	private static final String CONTEXT_LookupTableName = I_C_Region.Table_Name;

	private static final Set<CtxName> PARAMETERS = ImmutableSet.of(
			CtxNames.parse(I_C_Location.COLUMNNAME_C_Country_ID),
			LookupDataSourceContext.PARAM_Filter,
			SqlForFetchingLookups.PARAM_Offset,
			SqlForFetchingLookups.PARAM_Limit);

	private final CCache<Integer, LookupValuesList> regionsByCountryId = CCache.newLRUCache(CACHE_PREFIX + "RegionLookupValues", 100, 0);

	private AddressRegionLookupDescriptor()
	{
	}

	@Override
	public Optional<String> getLookupTableName()
	{
		return LookupTableName;
	}

	@Override
	public String getCachePrefix()
	{
		return CACHE_PREFIX;
	}

	@Override
	public boolean isCached()
	{
		return true;
	}

	@Override
	public void cacheInvalidate()
	{
		regionsByCountryId.reset();
	}

	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.of(regionsByCountryId.stats());
	}

	@Override
	public boolean isHighVolume()
	{
		return true;
	}

	@Override
	public LookupSource getLookupSourceType()
	{
		return LookupSource.lookup;
	}

	@Override
	public boolean hasParameters()
	{
		return true;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return CtxNames.toNames(PARAMETERS);
	}

	@Override
	public boolean isNumericKey()
	{
		return true;
	}

	@Override
	public LookupDataSourceFetcher getLookupDataSourceFetcher()
	{
		return this;
	}

	@Override
	public Builder newContextForFetchingById(final Object id)
	{
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName)
				.putFilterById(IdsToFilter.ofSingleValue(id));
	}

	@Override
	public LookupValue retrieveLookupValueById(final @NonNull LookupDataSourceContext evalCtx)
	{
		final int id = evalCtx.getIdToFilterAsInt(-1);
		if (id <= 0)
		{
			throw new IllegalStateException("No ID provided in " + evalCtx);
		}

		final LookupValue region = regionsByCountryId.values()
				.stream()
				.map(regions -> regions.getById(id))
				.filter(Objects::nonNull)
				.findFirst()
				.orElse(null);
		if (region != null)
		{
			return region;
		}

		final I_C_Region regionRecord = InterfaceWrapperHelper.create(Env.getCtx(), id, I_C_Region.class, ITrx.TRXNAME_None);
		if (regionRecord == null)
		{
			return LOOKUPVALUE_NULL;
		}

		return createLookupValue(regionRecord);
	}

	@Override
	public Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName)
				.setRequiredParameters(PARAMETERS);
	}

	@Override
	public LookupValuesPage retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		final int countryId = evalCtx.get_ValueAsInt(I_C_Location.COLUMNNAME_C_Country_ID, -1);
		if (countryId <= 0)
		{
			return LookupValuesPage.EMPTY;
		}

		//
		// Determine what we will filter
		final String filter = evalCtx.getFilter();
		final boolean matchAll;
		final String filterUC;
		final int limit;
		final int offset = evalCtx.getOffset(0);
		if (filter == LookupDataSourceContext.FILTER_Any)
		{
			matchAll = true;
			filterUC = null; // N/A
			limit = evalCtx.getLimit(Integer.MAX_VALUE);
		}
		else if (Check.isEmpty(filter, true))
		{
			return LookupValuesPage.EMPTY;
		}
		else
		{
			matchAll = false;
			filterUC = filter.trim().toUpperCase();
			limit = evalCtx.getLimit(100);
		}

		//
		// Get, filter, return
		return getRegionLookupValues(countryId)
				.getValues()
				.stream()
				.filter(region -> matchAll || matchesFilter(region, filterUC))
				.collect(LookupValuesList.collect())
				.pageByOffsetAndLimit(offset, limit);
	}

	private boolean matchesFilter(final LookupValue region, final String filterUC)
	{
		final String displayName = region.getDisplayName();
		if (Check.isEmpty(displayName))
		{
			return false;
		}

		final String displayNameUC = displayName.trim().toUpperCase();

		return displayNameUC.contains(filterUC);
	}

	private LookupValuesList getRegionLookupValues(final int countryId)
	{
		return regionsByCountryId.getOrLoad(countryId, () -> retrieveRegionLookupValues(countryId));
	}

	private LookupValuesList retrieveRegionLookupValues(final int countryId)
	{
		return Services.get(ICountryDAO.class)
				.retrieveRegions(Env.getCtx(), countryId)
				.stream()
				.map(this::createLookupValue)
				.collect(LookupValuesList.collect());
	}

	private IntegerLookupValue createLookupValue(final I_C_Region regionRecord)
	{
		return IntegerLookupValue.of(regionRecord.getC_Region_ID(), regionRecord.getName());
	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return Optional.empty();
	}
}
