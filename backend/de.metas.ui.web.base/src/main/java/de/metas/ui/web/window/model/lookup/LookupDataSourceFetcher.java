package de.metas.ui.web.window.model.lookup;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCacheStats;
import de.metas.cache.CacheLabel;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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

public interface LookupDataSourceFetcher
{
	CacheLabel ADDITIONAL_CACHE_LABEL = CacheLabel.ofString(LookupDataSourceFetcher.class.getSimpleName());

	LookupValue LOOKUPVALUE_NULL = IntegerLookupValue.builder()
			.id(-1)
			.active(false)
			.build();

	boolean isNumericKey();

	LookupDataSourceContext.Builder newContextForFetchingById(Object id);

	@Nullable
	LookupValue retrieveLookupValueById(@NonNull LookupDataSourceContext evalCtx);

	default LookupDataSourceContext.Builder newContextForFetchingByIds(@NonNull final Collection<?> ids)
	{
		return newContextForFetchingById(null)
				.putFilterById(IdsToFilter.ofMultipleValues(ids));
	}

	default LookupValuesList retrieveLookupValueByIdsInOrder(@NonNull final LookupDataSourceContext evalCtx)
	{
		return evalCtx.streamSingleIdContexts()
				.map(this::retrieveLookupValueById)
				.filter(Objects::nonNull)
				.collect(LookupValuesList.collect());
	}

	LookupDataSourceContext.Builder newContextForFetchingList();

	LookupValuesPage retrieveEntities(LookupDataSourceContext evalCtx);

	//
	// Caching
	//@formatter:off
	/** @return true if this fetcher already has caching embedded so on upper levels, caching is not needed */
	boolean isCached();
	/** @return cache prefix; relevant only if {@link #isCached()} returns <code>false</code> */
	@Nullable String getCachePrefix();
	default List<CCacheStats> getCacheStats() { return ImmutableList.of(); }
	//@formatter:on

	/**
	 * @return tableName if available
	 */
	Optional<String> getLookupTableName();

	/**
	 * @return optional WindowId to be used when zooming into
	 */
	Optional<WindowId> getZoomIntoWindowId();

	void cacheInvalidate();
}
