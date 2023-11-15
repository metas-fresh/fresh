/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.window.model.lookup;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.adempiere.service.impl.TooltipType;
import de.metas.common.util.CoalesceUtil;
import de.metas.logging.LogManager;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DebugProperties;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.sql.SqlForFetchingLookupById;
import de.metas.ui.web.window.descriptor.sql.SqlForFetchingLookups;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.service.impl.LookupDAO;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.ad.validationRule.INamePairPredicate;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.comparator.FixedOrderByKeyComparator;
import org.compiere.util.DB;
import org.compiere.util.NamePair;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class GenericSqlLookupDataSourceFetcher implements LookupDataSourceFetcher
{
	private static final Logger logger = LogManager.getLogger(GenericSqlLookupDataSourceFetcher.class);
	public static final int DEFAULT_PAGE_SIZE = 1000;

	@NonNull private final String lookupTableName;
	@Getter private final boolean numericKey;
	@Getter @NonNull private final SqlForFetchingLookups sqlForFetchingLookups;
	@Getter @NonNull private final SqlForFetchingLookupById sqlForFetchingLookupById;
	@Nullable private final INamePairPredicate postQueryPredicate;
	@Getter @NonNull private final Optional<WindowId> zoomIntoWindowId;
	@NonNull private final TooltipType tooltipType;

	private final int pageLength;

	//
	// Computed:
	private final @NonNull Optional<String> lookupTableNameAsOptional;
	private final boolean isTranslatable;

	@Builder
	private GenericSqlLookupDataSourceFetcher(
			@NonNull final TableName lookupTableName,
			@NonNull final SqlForFetchingLookups sqlForFetchingLookups,
			@NonNull final SqlForFetchingLookupById sqlForFetchingLookupById,
			@Nullable final INamePairPredicate postQueryPredicate,
			@NonNull final Optional<WindowId> zoomIntoWindowId,
			@NonNull final TooltipType tooltipType,
			@Nullable final Integer pageLength)
	{
		this.lookupTableName = lookupTableName.getAsString();
		this.numericKey = sqlForFetchingLookupById.isNumericKey();
		this.sqlForFetchingLookups = sqlForFetchingLookups;
		this.sqlForFetchingLookupById = sqlForFetchingLookupById;
		this.postQueryPredicate = postQueryPredicate;
		this.zoomIntoWindowId = zoomIntoWindowId;
		this.tooltipType = tooltipType;
		this.pageLength = CoalesceUtil.coalesceNotNull(pageLength, -1);

		this.lookupTableNameAsOptional = Optional.of(this.lookupTableName);
		this.isTranslatable = this.sqlForFetchingLookupById.requiresParameter(LookupDataSourceContext.PARAM_AD_Language.getName());
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("lookupTableName", lookupTableName)
				.add("sqlForFetchingLookups", sqlForFetchingLookups)
				.add("postQueryPredicate", postQueryPredicate)
				.toString();
	}

	@Override
	public String getCachePrefix()
	{
		// NOTE: it's very important to have the lookupTableName as cache name prefix because we want the cache invalidation to happen for this table
		return lookupTableName;
	}

	@Override
	public Optional<String> getLookupTableName()
	{
		return lookupTableNameAsOptional;
	}

	@Override
	public boolean isCached()
	{
		return false;
	}

	@Override
	public void cacheInvalidate()
	{
	}

	@Override
	public final LookupDataSourceContext.Builder newContextForFetchingById(@Nullable final Object id)
	{
		return LookupDataSourceContext.builder(lookupTableName)
				.putFilterById(IdsToFilter.ofSingleValue(id))
				.setRequiredParameters(sqlForFetchingLookupById.getParameters());
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingByIds(@NonNull final Collection<?> ids)
	{
		return newContextForFetchingById(null)
				.putFilterById(IdsToFilter.ofMultipleValues(ids));
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingList()
	{
		return LookupDataSourceContext.builder(lookupTableName)
				.putPostQueryPredicate(postQueryPredicate)
				.setRequiredParameters(sqlForFetchingLookups.getParameters());
	}

	/**
	 * @return lookup values list
	 */
	@Override
	public LookupValuesPage retrieveEntities(final LookupDataSourceContext evalCtxParam)
	{
		final int offset = evalCtxParam.getOffset(0);
		final int pageLength = firstGreaterThanZero(this.pageLength, evalCtxParam.getLimit(DEFAULT_PAGE_SIZE));

		final LookupDataSourceContext evalCtxEffective = evalCtxParam
				.withOffset(offset)
				.withLimit(pageLength < Integer.MAX_VALUE ? pageLength + 1 : pageLength); // add 1 to pageLength to be able to recognize if there are more items

		final String sqlForFetching = this.sqlForFetchingLookups.evaluate(evalCtxEffective);
		final int entityTypeIndex = sqlForFetchingLookups.getEntityTypeIndex();
		final String adLanguage = isTranslatable ? evalCtxEffective.getAD_Language() : null;

		try (final LookupDAO.SQLNamePairIterator data = new LookupDAO.SQLNamePairIterator(sqlForFetching, numericKey, entityTypeIndex))
		{
			DebugProperties debugProperties = null;
			if (WindowConstants.isProtocolDebugging())
			{
				debugProperties = DebugProperties.EMPTY
						.withProperty("debug-sql", sqlForFetching)
						.withProperty("debug-params", evalCtxEffective.toString());
			}

			final List<NamePair> valuesBeforePostFilter = data.fetchAll();
			final boolean hasMoreResults = valuesBeforePostFilter.size() > pageLength;

			final LookupValuesList values = valuesBeforePostFilter
					.stream()
					.limit(pageLength)
					.filter(evalCtxEffective::acceptItem)
					.map(namePair -> LookupValue.fromNamePair(namePair, adLanguage, this.tooltipType))
					.collect(LookupValuesList.collect(debugProperties));

			logger.trace("Returning values={} (executed sql: {})", values, sqlForFetching);
			return LookupValuesPage.ofValuesAndHasMoreFlag(values, hasMoreResults);
		}
	}

	@Override
	public final LookupValue retrieveLookupValueById(final @NonNull LookupDataSourceContext evalCtx)
	{
		final SqlAndParams sqlAndParams = this.sqlForFetchingLookupById.evaluate(evalCtx).orElse(null);
		if (sqlAndParams == null)
		{
			throw new AdempiereException("No ID provided in " + evalCtx);
		}

		final String adLanguage = evalCtx.getAD_Language();

		final ImmutableList<LookupValue> rows = DB.retrieveRows(
				sqlAndParams.getSql(),
				sqlAndParams.getSqlParams(),
				rs -> SqlForFetchingLookupById.retrieveLookupValue(rs, isNumericKey(), isTranslatable, adLanguage));

		if (rows.isEmpty())
		{
			return LOOKUPVALUE_NULL;
		}
		else if (rows.size() == 1)
		{
			return rows.get(0);
		}
		else
		{
			// shall not happen
			throw new AdempiereException("More than one row found for " + evalCtx + ": " + rows);
		}
	}

	@Override
	public LookupValuesList retrieveLookupValueByIdsInOrder(@NonNull final LookupDataSourceContext evalCtx)
	{
		final SqlAndParams sqlAndParams = this.sqlForFetchingLookupById.evaluate(evalCtx).orElse(null);
		if (sqlAndParams == null)
		{
			return LookupValuesList.EMPTY;
		}

		final String adLanguage = evalCtx.getAD_Language();

		final ImmutableList<LookupValue> rows = DB.retrieveRows(
				sqlAndParams.getSql(),
				sqlAndParams.getSqlParams(),
				rs -> SqlForFetchingLookupById.retrieveLookupValue(rs, numericKey, isTranslatable, adLanguage));

		if (rows.isEmpty())
		{
			return LookupValuesList.EMPTY;
		}
		else if (rows.size() == 1)
		{
			return LookupValuesList.fromNullable(rows.get(0));
		}
		else
		{
			return rows.stream()
					.sorted(FixedOrderByKeyComparator.<LookupValue, Object>builder()
							.fixedOrderKeys(LookupValue.normalizeIds(evalCtx.getIdsToFilter().toImmutableList(), numericKey))
							.keyMapper(LookupValue::getId)
							.build())
					.collect(LookupValuesList.collect());
		}
	}
}
