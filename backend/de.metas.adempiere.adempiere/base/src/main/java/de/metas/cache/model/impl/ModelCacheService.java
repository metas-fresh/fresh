package de.metas.cache.model.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.MapMaker;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.cache.CacheMgt;
import de.metas.cache.IDCache;
import de.metas.cache.interceptor.CacheCtxParamDescriptor;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.cache.model.IModelCacheService;
import de.metas.cache.model.IMutableTableCacheConfig;
import de.metas.cache.model.ITableCacheConfig;
import de.metas.cache.model.ITableCacheConfig.TrxLevel;
import de.metas.cache.model.ITableCacheConfigBuilder;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.NullTrxPlaceholder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ModelCacheService implements IModelCacheService
{
	private static final Logger logger = LogManager.getLogger(ModelCacheService.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final ConcurrentMap<ITrx, TrxCacheMap> cacheMapsByTrx = new MapMaker()
			.weakKeys()
			.makeMap();

	private final ConcurrentHashMap<String, ITableCacheConfig> tableName2cacheConfig = new ConcurrentHashMap<>();

	public ModelCacheService()
	{
		CacheMgt.get().addCacheResetListener(this::onCacheResetRequest);
	}

	@Override
	public ITableCacheConfigBuilder createTableCacheConfigBuilder(final String tableName)
	{
		return new TableCacheConfigBuilder(this, tableName);
	}

	@Override
	public ITableCacheConfigBuilder createTableCacheConfigBuilder(final Class<?> modelClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		return createTableCacheConfigBuilder(tableName);
	}

	private ITableCacheConfig getTableCacheConfig(final String tableName)
	{
		final ITableCacheConfig cacheConfig = tableName2cacheConfig.get(tableName);
		if (cacheConfig == null)
		{
			return null;
		}

		if (!cacheConfig.isEnabled())
		{
			return null;
		}

		return cacheConfig;
	}

	@Override
	public final void addTableCacheConfig(final Class<?> modelClass)
	{
		final ITableCacheConfig cacheConfig = createDefaultTableCacheConfig(modelClass);
		final boolean override = true;
		addTableCacheConfig(cacheConfig, override);
	}

	@Override
	public void addTableCacheConfigIfAbsent(final Class<?> modelClass)
	{
		final ITableCacheConfig cacheConfig = createDefaultTableCacheConfig(modelClass);
		final boolean override = false;
		addTableCacheConfig(cacheConfig, override);
	}

	@Override
	public ITableCacheConfig createDefaultTableCacheConfig(final Class<?> modelClass)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		final IMutableTableCacheConfig cacheConfig = new MutableTableCacheConfig(tableName);
		cacheConfig.setEnabled(true);
		cacheConfig.setTrxLevel(TrxLevel.All);
		return cacheConfig;
	}

	protected final void addTableCacheConfig(final ITableCacheConfig cacheConfig, final boolean override)
	{
		if (!Adempiere.isUnitTestMode())
		{
			final POInfo poInfo = POInfo.getPOInfoNotNull(cacheConfig.getTableName());
			if (!poInfo.isSingleKeyColumnName())
			{
				logger.warn("Skip adding {} because the table does not have a single primary key", cacheConfig);
			}
		}

		tableName2cacheConfig.compute(cacheConfig.getTableName(), (tableName, previousConfig) -> {
			if (previousConfig == null)
			{
				logger.info("Registering new table cache config: {}", cacheConfig);
				return cacheConfig;
			}
			else if (override)
			{
				logger.info("Registering table cache config: {} \nPrevious table caching config: {}", cacheConfig, previousConfig);
				return cacheConfig;
			}
			else
			{
				logger.info("Skip registering table cache config: {} \nPreserving current table cache config: {}", cacheConfig, previousConfig);
				return previousConfig;
			}
		});
	}

	@Override
	public final PO retrieveObject(final Properties ctx, final String tableName, final int recordId, final String trxName)
	{
		//
		// Get cache configuration
		// No cache config => we don't do caching
		// NOTE: we are not checking for cacheConfig.isEnabled because we want to record the statistics
		final ITableCacheConfig cacheConfig = getTableCacheConfig(tableName);
		if (cacheConfig == null)
		{
			return null;
		}

		//
		// Get the right transaction
		final ITrx trx = trxManager.getTrxOrNull(trxName);
		final boolean inTransaction = trx != null;
		// Check if we got the right transaction
		if (trx == null && !trxManager.isNull(trxName) && !Util.same(trxName, ITrx.TRXNAME_ThreadInherited))
		{
			final AdempiereException ex = new AdempiereException("No transaction was found for " + trxName + ". Skip cache."
					+ "\ntableName=" + tableName
					+ "\nrecordId=" + recordId
					+ "\ntrxName=" + trxName
					+ "\ntrx=" + trx
					+ "\nThread TrxName=" + trxManager.getThreadInheritedTrxName()
					+ "\nActive transactions: " + trxManager.getActiveTransactionsList()
					+ "\nClosed transactions: " + trxManager.getDebugClosedTransactions());
			logger.warn(ex.getLocalizedMessage(), ex);

			// return null (not found)
			return null;
		}

		//
		// Search cache on transaction level
		// (at this point "trx" can be an actual transaction or out-of-transaction=None)
		PO poCached = retrieveObjectFromTrx(cacheConfig, ctx, tableName, recordId, trx);

		//
		// If nothing found and we are not out-of-transaction
		// then try searching on out-of-transaction level and if something found clone it and return it
		if (poCached == null && inTransaction)
		{
			poCached = retrieveObjectFromTrx(cacheConfig, ctx, tableName, recordId, ITrx.TRX_None);
		}

		if (logger.isTraceEnabled())
		{
			final boolean hit = poCached != null;
			logger.trace("Cache {} (inTrx={}) - tableName/recordId={}/{}", hit ? "HIT" : "MISS", inTransaction, tableName, recordId);
		}

		// Make sure the trls are loaded before copying it, else trls will be loaded each time
		if (poCached != null)
		{
			poCached.get_ModelTranslationMap();
		}

		// each caller gets their own copy because in case they loaded the PO from database, they would also have gotten an instance of their own.
		return copyPO(poCached, trxName);
	}

	/**
	 * Creates a copy of orginal PO, having given <code>trxName</code>.
	 *
	 * @return <ul>
	 *         <li>copy of <code>originalPO</code>;
	 *         <li>if original PO is null, null will be returned;
	 *         <li>if there is an error while cloning given PO, null will be returned but an warning will be logged (just to not stop the current execution)
	 *         </ul>
	 */
	private static PO copyPO(final PO originalPO, final String trxName)
	{
		if (originalPO == null)
		{
			return null;
		}

		try
		{
			final PO poCopy = originalPO.copy();
			poCopy.set_TrxName(trxName);
			return poCopy;
		}
		catch (final Exception e)
		{
			logger.warn("Cannot create a copy of {}. Returning null.", originalPO, e);
			return null;
		}
	}

	private TrxCacheMap getTrxCacheMapIfPresent(final ITrx trx)
	{
		return cacheMapsByTrx.get(NullTrxPlaceholder.boxNotNull(trx));
	}

	private TrxCacheMap getTrxCacheMap(final ITrx trx)
	{
		return cacheMapsByTrx.computeIfAbsent(NullTrxPlaceholder.boxNotNull(trx), TrxCacheMap::new);
	}

	private PO retrieveObjectFromTrx(final ITableCacheConfig cacheConfig, final Properties ctx, final String tableName, final int recordId, final ITrx trx)
	{
		//
		// Check if we can cache on this transaction level?
		if (!isTrxLevelEnabled(cacheConfig, trx))
		{
			return null;
		}

		final TrxCacheMap trxCacheMap = getTrxCacheMapIfPresent(trx);
		if (trxCacheMap == null)
		{
			return null;
		}

		final CCache<Object, PO> cache = trxCacheMap.getByTableNameIfExists(tableName);
		if (cache == null)
		{
			return null;
		}

		PO poCached = cache.get(recordId);
		if (poCached == null)
		{
			return null;
		}

		//
		// Check if our cached object is expired (i.e. trxName changed etc)
		if (isExpired(ctx, tableName, recordId, trx, poCached))
		{
			cache.remove(recordId);
			logger.trace("Removed {} from model cache because it's expired (trx={})", poCached, trx);
			poCached = null;
		}

		return poCached;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private boolean isTrxLevelEnabled(final ITableCacheConfig cacheConfig, final ITrx trx)
	{
		// If caching is not enabled in cache config, simply return false
		if (!cacheConfig.isEnabled())
		{
			return false;
		}

		// If transaction is not active, we shall not use the caching for it.
		// NOTE: We are not deleting it (let it expire on WeakHashMap).
		if (trx != null && !trx.isActive())
		{
			return false;
		}

		final boolean isInTransaction = !trxManager.isNull(trx);
		final TrxLevel trxLevel = cacheConfig.getTrxLevel();
		if (isInTransaction)
		{
			return trxLevel == TrxLevel.All || trxLevel == TrxLevel.InTransactionOnly;
		}
		else
		{
			return trxLevel == TrxLevel.All || trxLevel == TrxLevel.OutOfTransactionOnly;
		}
	}

	/**
	 * @return true if given PO is expired
	 */
	private boolean isExpired(final Properties ctx, final String tableName, final int recordId, final ITrx trx, final PO po)
	{
		// Check if our cached PO was deleted
		final int poRecordId = po.get_ID();
		if (poRecordId < 0)
		{
			return true;
		}

		// Check if PO has the same trxName as we required
		final String poTrxName = po.get_TrxName();
		if (!trxManager.isSameTrxName(trx, poTrxName))
		{
			return true;
		}

		// Check if PO has the same ID as we required
		if (recordId != poRecordId)
		{
			return true;
		}

		// Check if PO has the same Context as we required
		final Properties poCtx = po.getCtx();
		if (!CacheCtxParamDescriptor.isSameCtx(poCtx, ctx)) // gh #1036
		{
			return true;
		}

		//
		// We assume our PO is not expired
		return false;
	}

	@Override
	public void addToCache(@Nullable final PO po)
	{
		// Don't cache null objects
		if (po == null)
		{
			return;
		}

		// Don't cache new objects
		if (po.is_new())
		{
			return;
		}

		//
		// Do we cache for this model's table?
		final String tableName = po.get_TableName();
		final ITableCacheConfig cacheConfig = getTableCacheConfig(tableName);
		// No cache config => we don't do caching
		if (cacheConfig == null || !cacheConfig.isEnabled())
		{
			return;
		}

		//
		// Do we cache for model's transaction?
		final String trxName = po.get_TrxName();
		final ITrx trx = trxManager.getTrxOrNull(trxName);
		if (!isTrxLevelEnabled(cacheConfig, trx))
		{
			return;
		}

		getTrxCacheMap(trx).put(po, cacheConfig);
	}

	@Override
	public void invalidate(@NonNull final CacheInvalidateMultiRequest request)
	{
		final ITrx trx = trxManager.getTrxOrNull(ITrx.TRXNAME_ThreadInherited);
		for (final CacheInvalidateRequest singleRequest : request.getRequests())
		{
			if (singleRequest.isAll())
			{
				invalidateAll(trx);
				break;
			}
			else if (singleRequest.isAllRecords())
			{
				invalidateTable(singleRequest.getTableNameEffective(), trx);
			}
			else
			{
				final TableRecordReference record = singleRequest.getRecordEffective();
				invalidateRecord(record, trx);
			}
		}
	}

	private void invalidateAll(@Nullable final ITrx trx)
	{
		final TrxCacheMap trxCacheMap = getTrxCacheMapIfPresent(trx);
		if (trxCacheMap == null)
		{
			return;
		}
		trxCacheMap.invalidateAll();
	}

	private void invalidateTable(@NonNull final String tableName, @Nullable final ITrx trx)
	{
		final TrxCacheMap trxCacheMap = getTrxCacheMapIfPresent(trx);
		if (trxCacheMap == null)
		{
			return;
		}

		trxCacheMap.invalidateByTableName(tableName);
	}

	private void invalidateRecord(@NonNull final TableRecordReference recordRef, @Nullable final ITrx trx)
	{
		final TrxCacheMap trxCacheMap = getTrxCacheMapIfPresent(trx);
		if (trxCacheMap == null)
		{
			return;
		}

		trxCacheMap.invalidateByRecord(recordRef);
	}

	/**
	 * Full cache reset
	 */
	private int onCacheResetRequest(final CacheInvalidateMultiRequest multiRequest)
	{
		if (!multiRequest.isResetAll())
		{
			return 0;
		}

		logger.debug("Clearing all cache instances of {}", this);

		final ImmutableList<TrxCacheMap> cacheMapsToInvalidate = ImmutableList.copyOf(cacheMapsByTrx.values());
		cacheMapsByTrx.clear();

		cacheMapsToInvalidate.forEach(TrxCacheMap::invalidateAll);

		return 1;
	}

	//
	//
	//
	//
	//
	private static class TrxCacheMap
	{
		private final String trxName;

		private final ConcurrentMap<String, IDCache<PO>> cachesByTableName = new ConcurrentHashMap<>();

		public TrxCacheMap(final ITrx trx)
		{
			trxName = NullTrxPlaceholder.unboxToNull(trx) != null ? trx.getTrxName() : ITrx.TRXNAME_None;
		}

		public IDCache<PO> getByTableNameIfExists(final String tableName)
		{
			return cachesByTableName.get(tableName);
		}

		public IDCache<PO> getByTableNameOrCreate(final ITableCacheConfig cacheConfig)
		{
			final String tableName = cacheConfig.getTableName();
			return cachesByTableName.computeIfAbsent(tableName, k -> create(cacheConfig));
		}

		private IDCache<PO> create(final ITableCacheConfig cacheConfig)
		{
			final String tableName = cacheConfig.getTableName();
			final int expireMinutes = cacheConfig.getExpireMinutes();
			final CacheMapType cacheMapType = cacheConfig.getCacheMapType();
			final int initialCapacity = cacheConfig.getInitialCapacity();
			int maxCapacity = cacheConfig.getMaxCapacity();
			if (maxCapacity <= 0)
			{
				maxCapacity = initialCapacity;
			}
			return new IDCache<>(tableName, trxName, maxCapacity, expireMinutes, cacheMapType);
		}

		public void invalidateAll()
		{
			cachesByTableName.values().forEach(IDCache::reset);
			cachesByTableName.clear();

			if (logger.isTraceEnabled())
			{
				logger.trace("Cleared all caches for trx={} ", trxName);
			}
		}

		public void invalidateByTableName(final String tableName)
		{
			final IDCache<PO> cache = getByTableNameIfExists(tableName);
			if (cache == null)
			{
				return;
			}

			// Invalidate the cache
			cache.reset();

			// Logging
			if (logger.isTraceEnabled())
			{
				logger.trace("Cleared cache {} for tableName={}, trx={} ", cache.getCacheName(), tableName, trxName);
			}
		}

		public void invalidateByRecord(final TableRecordReference recordRef)
		{
			final String tableName = recordRef.getTableName();
			final int recordId = recordRef.getRecord_ID();

			//
			// Get table's cache map.
			final IDCache<PO> cache = getByTableNameIfExists(tableName);
			if (cache == null)
			{
				return;
			}

			// Invalidate the cache
			final boolean invalidated = cache.remove(recordId) != null;

			// Logging
			if (invalidated && logger.isTraceEnabled())
			{
				logger.trace("Model removed from cache {}: record={}/{}, trx={} ", cache.getCacheName(), tableName, recordId, trxName);
			}
		}

		public void put(@NonNull final PO po, @NonNull final ITableCacheConfig cacheConfig)
		{
			//
			// Get table's cache map.
			// If does not exist, create one.
			final IDCache<PO> cache = getByTableNameOrCreate(cacheConfig);

			//
			// Add our PO to cache
			final int recordId = po.get_ID();
			final PO poCopy = copyPO(po, trxName);
			if (poCopy == null)
			{
				logger.warn("Failed to add {} to cache because copy failed. Ignored.", po);
				return;
			}

			cache.put(recordId, poCopy);

			// Logging
			if (logger.isTraceEnabled())
			{
				logger.trace("Model added to cache {}: {} ", cache.getCacheName(), poCopy);
			}
		}
	}
}
