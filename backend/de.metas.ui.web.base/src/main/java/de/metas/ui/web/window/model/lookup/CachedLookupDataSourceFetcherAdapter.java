package de.metas.ui.web.window.model.lookup;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.cache.CCacheStats;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext.Builder;
import de.metas.util.Check;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

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

/**
 * Wraps a given {@link LookupDataSourceFetcher} and cached its retrieving methods.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public final class CachedLookupDataSourceFetcherAdapter implements LookupDataSourceFetcher
{
	public static CachedLookupDataSourceFetcherAdapter of(final LookupDataSourceFetcher delegate)
	{
		if (delegate instanceof CachedLookupDataSourceFetcherAdapter)
		{
			return (CachedLookupDataSourceFetcherAdapter)delegate;
		}
		return new CachedLookupDataSourceFetcherAdapter(delegate);
	}

	private static final String NAME = "PerPartition";

	private final LookupDataSourceFetcher delegate;
	private final String cachePrefix;

	private final transient CCache<LookupDataSourceContext, LookupValuesPage> cache_retrieveEntities;
	private final transient CCache<LookupDataSourceContext, LookupValue> cache_retrieveLookupValueById;

	private CachedLookupDataSourceFetcherAdapter(@NonNull final LookupDataSourceFetcher delegate)
	{
		this.delegate = delegate;
		this.cachePrefix = Check.assumeNotEmpty(delegate.getCachePrefix(), "cachePrefix is not empty");
		final int maxSize = 100;
		final int expireAfterMinutes = 60 * 2;

		// NOTE: it's very important to have the lookupTableName as cache name prefix because we want the cache invalidation to happen for this table

		cache_retrieveEntities = CCache.<LookupDataSourceContext, LookupValuesPage>builder()
				.cacheName(cachePrefix + "#" + NAME + "#retrieveEntities")
				.additionalTableNameToResetFor(cachePrefix)
				.maximumSize(maxSize)
				.expireMinutes(expireAfterMinutes)
				.additionalLabel(ADDITIONAL_CACHE_LABEL)
				.build();

		cache_retrieveLookupValueById = CCache.<LookupDataSourceContext, LookupValue>builder()
				.cacheName(cachePrefix + "#" + NAME + "#retrieveLookupValueById")
				.additionalTableNameToResetFor(cachePrefix)
				.maximumSize(maxSize)
				.expireMinutes(expireAfterMinutes)
				.additionalLabel(ADDITIONAL_CACHE_LABEL)
				.build();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper("Cached")
				.addValue(delegate)
				.toString();
	}

	@Override
	public Optional<String> getLookupTableName()
	{
		return delegate.getLookupTableName();
	}

	@Override
	public String getCachePrefix()
	{
		return cachePrefix;
	}

	@Override
	public boolean isCached()
	{
		return true;
	}

	@Override
	public void cacheInvalidate()
	{
		cache_retrieveEntities.reset();
		cache_retrieveLookupValueById.reset();
	}

	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.<CCacheStats>builder()
				.add(cache_retrieveEntities.stats())
				.add(cache_retrieveLookupValueById.stats())
				.addAll(delegate.getCacheStats())
				.build();
	}

	@Override
	public boolean isNumericKey()
	{
		return delegate.isNumericKey();
	}

	@Override
	public LookupDataSourceContext.Builder newContextForFetchingById(final Object id)
	{
		return delegate.newContextForFetchingById(id);
	}

	@Override
	public LookupValue retrieveLookupValueById(final @NonNull LookupDataSourceContext evalCtx)
	{
		return cache_retrieveLookupValueById.getOrLoad(evalCtx, () -> delegate.retrieveLookupValueById(evalCtx));
	}

	@Override
	public LookupValuesList retrieveLookupValueByIdsInOrder(final @NonNull LookupDataSourceContext evalCtx)
	{
		cache_retrieveLookupValueById.getAllOrLoad(
				evalCtx.streamSingleIdContexts().collect(ImmutableSet.toImmutableSet()),
				singleIdCtxs -> {
					final LookupDataSourceContext multipleIdsCtx = LookupDataSourceContext.mergeToMultipleIds(singleIdCtxs);
					return delegate.retrieveLookupValueByIdsInOrder(LookupDataSourceContext.mergeToMultipleIds(singleIdCtxs))
							.getValues()
							.stream()
							.collect(ImmutableMap.toImmutableMap(
									lookupValue -> multipleIdsCtx.withIdToFilter(IdsToFilter.ofSingleValue(lookupValue.getId())),
									lookupValue -> lookupValue));
				}
		);
		return LookupDataSourceFetcher.super.retrieveLookupValueByIdsInOrder(evalCtx);
	}

	@Override
	public Builder newContextForFetchingList()
	{
		return delegate.newContextForFetchingList();
	}

	@Override
	public LookupValuesPage retrieveEntities(final LookupDataSourceContext evalCtx)
	{
		return cache_retrieveEntities.getOrLoad(evalCtx, delegate::retrieveEntities);
	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return delegate.getZoomIntoWindowId();
	}
}
