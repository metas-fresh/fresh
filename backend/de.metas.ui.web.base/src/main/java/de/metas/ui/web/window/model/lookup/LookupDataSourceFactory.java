package de.metas.ui.web.window.model.lookup;

import de.metas.cache.CCache;
import de.metas.cache.CCacheStats;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_M_AttributeSetInstance;
import org.slf4j.Logger;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

public final class LookupDataSourceFactory
{
	public static final transient LookupDataSourceFactory instance = new LookupDataSourceFactory();

	private static final Logger logger = LogManager.getLogger(LookupDataSourceFactory.class);

	private final CCache<LookupDescriptor, LookupDataSource> lookupDataSourcesCache = new CCache<>("LookupDataSourcesCache", 300);
	private final ConcurrentHashMap<String, CacheInvalidationGroup> cacheInvalidationGroupsByTableName = new ConcurrentHashMap<>();

	private LookupDataSourceFactory()
	{
	}

	public LookupDataSource searchInTableLookup(final String tableName)
	{
		if (I_M_AttributeSetInstance.Table_Name.equals(tableName))
		{
			return productAttributes();
		}
		else
		{
			final LookupDescriptor lookupDescriptor = SqlLookupDescriptor.searchInTable(tableName)
					.provide()
					.get();
			return getLookupDataSource(lookupDescriptor);
		}
	}

	public LookupDataSource productAttributes()
	{
		final LookupDescriptor lookupDescriptor = SqlLookupDescriptor.productAttributes()
				.provide()
				.get();
		return getLookupDataSource(lookupDescriptor);
	}

	public LookupDataSource listByAD_Reference_Value_ID(final int AD_Reference_Value_ID)
	{
		final LookupDescriptor lookupDescriptor = SqlLookupDescriptor
				.listByAD_Reference_Value_ID(AD_Reference_Value_ID)
				.provide()
				.get();
		return getLookupDataSource(lookupDescriptor);
	}

	public LookupDataSource searchByAD_Val_Rule_ID(final int AD_Reference_Value_ID, final int AD_Val_Rule_ID)
	{
		final LookupDescriptor lookupDescriptor = SqlLookupDescriptor
				.searchByAD_Val_Rule_ID(AD_Reference_Value_ID, AD_Val_Rule_ID)
				.provide()
				.get();
		return getLookupDataSource(lookupDescriptor);
	}

	public LookupDataSource searchByColumn(@NonNull final String tableName, @NonNull final String columnname)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final I_AD_Column column = adTableDAO.retrieveColumn(tableName, columnname);

		final LookupDescriptor lookupDescriptor = SqlLookupDescriptor
				.searchByAD_Val_Rule_ID(column.getAD_Reference_Value_ID(), column.getAD_Val_Rule_ID())
				.provide()
				.get();

		return getLookupDataSource(lookupDescriptor);
	}

	public LookupDataSource getLookupDataSource(final LookupDescriptor lookupDescriptor)
	{
		return lookupDataSourcesCache.getOrLoad(lookupDescriptor, this::createLookupDataSource);
	}

	public LookupDataSource createLookupDataSource(@NonNull final LookupDescriptor lookupDescriptor)
	{
		final LookupDataSourceFetcher fetcher = lookupDescriptor.getLookupDataSourceFetcher();

		final LookupDataSource lookupDataSource;
		if (fetcher.isCached())
		{
			lookupDataSource = LookupDataSourceAdapter.of(fetcher);
		}
		else if (!lookupDescriptor.isHighVolume() && !lookupDescriptor.hasParameters())
		{
			lookupDataSource = FullyCachedLookupDataSource.of(fetcher);
		}
		else
		{
			final CachedLookupDataSourceFetcherAdapter cachedFetcher = CachedLookupDataSourceFetcherAdapter.of(fetcher);
			lookupDataSource = LookupDataSourceAdapter.of(cachedFetcher);
		}

		//
		// Register cache invalidation on depending table changed
		lookupDescriptor.getDependsOnTableNames()
				.stream()
				.map(this::getCacheInvalidationGroupByTableName)
				.forEach(cacheInvalidationGroup -> cacheInvalidationGroup.addLookupDataSource(lookupDataSource));

		logger.debug("Creating lookup data source for {}: {}", lookupDescriptor, lookupDataSource);
		return lookupDataSource;
	}

	private CacheInvalidationGroup getCacheInvalidationGroupByTableName(final String tableName)
	{
		return cacheInvalidationGroupsByTableName.computeIfAbsent(tableName, CacheInvalidationGroup::new);
	}

	public void cacheInvalidateOnRecordsChanged(final Set<String> tableNames)
	{
		tableNames.stream()
				.map(cacheInvalidationGroupsByTableName::get)
				.filter(Objects::nonNull)
				.forEach(CacheInvalidationGroup::cacheInvalidate);
	}

	public List<CCacheStats> getCacheStats()
	{
		return lookupDataSourcesCache
				.values()
				.stream()
				.flatMap(dataSource -> dataSource.getCacheStats().stream())
				.distinct()
				.sorted(Comparator.comparing(CCacheStats::getName))
				.collect(GuavaCollectors.toImmutableList());
	}

	@ToString(exclude = "lookupDataSources")
	private static final class CacheInvalidationGroup
	{
		private final String tableName;
		private final List<WeakReference<LookupDataSource>> lookupDataSources = new ArrayList<>();

		public CacheInvalidationGroup(final String tableName)
		{
			this.tableName = tableName;
		}

		public void addLookupDataSource(@NonNull final LookupDataSource lookupDataSource)
		{
			synchronized (lookupDataSources)
			{
				lookupDataSources.add(new WeakReference<>(lookupDataSource));
			}
		}

		private List<LookupDataSource> purgeAndGet()
		{
			synchronized (lookupDataSources)
			{
				final List<LookupDataSource> result = new ArrayList<>(lookupDataSources.size());
				for (final Iterator<WeakReference<LookupDataSource>> it = lookupDataSources.iterator(); it.hasNext();)
				{
					final LookupDataSource lookupDataSource = it.next().get();
					if (lookupDataSource == null)
					{
						it.remove();
						continue;
					}

					result.add(lookupDataSource);
				}

				return result;
			}

		}

		public void cacheInvalidate()
		{
			for (final LookupDataSource lookupDataSource : purgeAndGet())
			{
				try
				{
					lookupDataSource.cacheInvalidate();
					logger.debug("Cache invalidated {} on {} record changed", lookupDataSource, tableName);
				}
				catch (final Exception ex)
				{
					logger.warn("Failed invalidating {}. Skipped", lookupDataSource, ex);
				}
			}
		}
	}
}
