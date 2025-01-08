package de.metas.ui.web.address;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.model.lookup.IdsToFilter;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext.Builder;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

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

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class AddressPostalLookupDescriptor implements LookupDescriptor, LookupDataSourceFetcher
{
	private static final Optional<String> LookupTableName = Optional.of(I_C_Postal.Table_Name);
	private static final String CACHE_PREFIX = I_C_Postal.Table_Name;
	private static final String CONTEXT_LookupTableName = I_C_Postal.Table_Name;

	private final AddressCountryLookupDescriptor countryLookup;

	@lombok.Builder
	private AddressPostalLookupDescriptor(@NonNull final AddressCountryLookupDescriptor countryLookup)
	{
		this.countryLookup = countryLookup;
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
		return true; // not cached but returning true to avoid caching
	}

	@Override
	public void cacheInvalidate()
	{
		countryLookup.cacheInvalidate();
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
		return false;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return ImmutableSet.of();
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
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return Optional.empty();
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builder(CONTEXT_LookupTableName);
	}

	@Override
	public LookupValuesPage retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		//
		// Determine what we will filter
		final String filter = evalCtx.getFilter();
		String filterUC;
		final int pageLength;
		final int offset = evalCtx.getOffset(0);
		if (filter == LookupDataSourceContext.FILTER_Any)
		{
			filterUC = "%"; // N/A
			pageLength = evalCtx.getLimit(9999); // kind of infinite
		}
		else if (Check.isBlank(filter))
		{
			return LookupValuesPage.EMPTY;
		}
		else
		{
			filterUC = filter.trim().toUpperCase();
			if (!filterUC.startsWith("%"))
			{
				filterUC = "%" + filterUC;
			}
			if (!filterUC.endsWith("%"))
			{
				filterUC = filterUC + "%";
			}
			pageLength = evalCtx.getLimit(100);
		}

		final int sqlQueryLimit = pageLength < Integer.MAX_VALUE ? pageLength + 1 : pageLength; // retrieving one more to recognize when we have more records
		final String sql = "SELECT "
				+ "\n " + I_C_Postal.COLUMNNAME_C_Postal_ID
				+ "\n, " + I_C_Postal.COLUMNNAME_Postal
				+ "\n, " + I_C_Postal.COLUMNNAME_City
				+ "\n, " + I_C_Postal.COLUMNNAME_Township
				+ "\n, " + I_C_Postal.COLUMNNAME_C_Country_ID
				+ "\n FROM " + I_C_Postal.Table_Name
				+ "\n WHERE "
				+ "\n " + I_C_Postal.COLUMNNAME_Postal + " ILIKE ?"
				+ "\n OR " + I_C_Postal.COLUMNNAME_City + " ILIKE ?"
				+ "\n ORDER BY " + I_C_Postal.COLUMNNAME_City + ", " + I_C_Postal.COLUMNNAME_Postal + ", " + I_C_Postal.COLUMNNAME_C_Postal_ID
				+ "\n LIMIT ? OFFSET ?";

		final Object[] sqlParams = new Object[] { filterUC, filterUC, sqlQueryLimit, offset };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			boolean hasMoreResults = false;
			final ArrayList<LookupValue> lookupValues = new ArrayList<>();
			while (rs.next())
			{
				if (lookupValues.size() >= pageLength)
				{
					hasMoreResults = true;
					break;
				}
				else
				{
					final IntegerLookupValue lookupValue = retrievePostalLookupValue(rs);
					lookupValues.add(lookupValue);
				}
			}

			return LookupValuesPage.ofValuesAndHasMoreFlag(lookupValues, hasMoreResults);
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@NonNull
	private IntegerLookupValue retrievePostalLookupValue(final ResultSet rs) throws SQLException
	{
		final int postalId = rs.getInt(I_C_Postal.COLUMNNAME_C_Postal_ID);
		final String postal = rs.getString(I_C_Postal.COLUMNNAME_Postal);
		final String city = rs.getString(I_C_Postal.COLUMNNAME_City);
		final String township = rs.getString(I_C_Postal.COLUMNNAME_Township);
		final int countryId = rs.getInt(I_C_Postal.COLUMNNAME_C_Country_ID);
		final LookupValue countryLookupValue = countryLookup.getLookupValueById(countryId);
		return buildPostalLookupValue(postalId, postal, city, township, countryLookupValue.getDisplayNameTrl());
	}

	public IntegerLookupValue getLookupValueFromLocation(final I_C_Location locationRecord)
	{
		final I_C_Postal postalRecord = locationRecord.getC_Postal();
		if (postalRecord == null || postalRecord.getC_Postal_ID() <= 0)
		{
			return null;
		}

		final LookupValue countryLookupValue = countryLookup.getLookupValueById(postalRecord.getC_Country_ID());

		return buildPostalLookupValue(
				postalRecord.getC_Postal_ID(),
				postalRecord.getPostal(),
				postalRecord.getCity(),
				postalRecord.getTownship(),
				countryLookupValue.getDisplayNameTrl());
	}

	private static IntegerLookupValue buildPostalLookupValue(
			final int postalId,
			final String postal,
			final String city,
			final String township,
			final ITranslatableString countryName)
	{
		final ITranslatableString displayName = TranslatableStrings.join("", postal, " ", city, " ", township, " (", countryName, ")");
		return IntegerLookupValue.of(postalId, displayName, null/* description */);
	}
}
