/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package de.metas.cache;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

<<<<<<< HEAD
import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.jmx.JMXRegistry;
import org.adempiere.util.jmx.JMXRegistry.OnJMXAlreadyExistsPolicy;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;

import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.logging.LogManager;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService.SpanMetadata;
import de.metas.monitoring.adapter.PerformanceMonitoringService.SubType;
import de.metas.monitoring.adapter.PerformanceMonitoringService.Type;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

=======
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
/**
 * Adempiere Cache Management
 *
 * @author Jorg Janke
 * @version $Id: CacheMgt.java,v 1.2 2006/07/30 00:54:35 jjanke Exp $
 */
public final class CacheMgt
{
	public static CacheMgt get()
	{
		return instance;
	}

	private static final CacheMgt instance = new CacheMgt();

	public static final String JMX_BASE_NAME = "de.metas.cache";

	@Getter @NonNull private final CCacheConfigDefaults configDefaults = CCacheConfigDefaults.computeFrom(SpringContextHolder.instance);

	private final ConcurrentHashMap<CacheLabel, CachesGroup> cachesByLabel = new ConcurrentHashMap<>();

	private final CopyOnWriteArrayList<ICacheResetListener> globalCacheResetListeners = new CopyOnWriteArrayList<>();
	private final ConcurrentMap<String, CopyOnWriteArrayList<ICacheResetListener>> cacheResetListenersByTableName = new ConcurrentHashMap<>();

	/* package */ static final Logger logger = LogManager.getLogger(CacheMgt.class);

	private final AtomicBoolean cacheResetRunning = new AtomicBoolean();
	private final AtomicLong lastCacheReset = new AtomicLong();

	private CacheMgt()
	{
	}

	/**
	 * Enable caches for the given table to be invalidated by remote events.<br>
	 * Example: if a user somewhere else opens/closes a period, we can allow the system to invalidate all the local caches to avoid it becoming stale.
	 */
	public void enableRemoteCacheInvalidationForTableName(final String tableName)
	{
		CacheInvalidationRemoteHandler.instance.enableForTableName(tableName);
	}

	/**
	 * Enable caches for the given table to be invalidated by remote events.<br>
	 */
	public void enableRemoteCacheInvalidationForTableNamesGroup(@NonNull final TableNamesGroup group)
	{
		CacheInvalidationRemoteHandler.instance.enableForTableNamesGroup(group);
	}

	private CachesGroup getCachesGroup(@NonNull final CacheLabel label)
	{
		return cachesByLabel.computeIfAbsent(label, CachesGroup::new);
	}

	private CachesGroup getCachesGroupIfPresent(@NonNull final CacheLabel label)
	{
		return cachesByLabel.get(label);
	}

	public void register(@NonNull final CacheInterface instance)
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(instance))
		{
			final Set<CacheLabel> labels = Check.assumeNotEmpty(instance.getLabels(), "labels is not empty");
			labels.stream()
					.map(this::getCachesGroup)
					.forEach(cacheGroup -> cacheGroup.addCache(instance));
		}
	}

	public void unregister(final CacheInterface cache)
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(cache))
		{
			cache.getLabels()
					.stream()
					.map(this::getCachesGroup)
					.forEach(cacheGroup -> cacheGroup.removeCache(cache));
		}
	}

	public Set<String> getTableNamesToBroadcast()
	{
		return CacheInvalidationRemoteHandler.instance.getTableNamesToBroadcast();
	}

	/**
	 * @return last time cache reset timestamp
	 */
	public long getLastCacheReset()
	{
		return lastCacheReset.get();
	}

	/**
	 * Invalidate ALL cached entries of all registered {@link CacheInterface}s.
	 *
	 * @return how many cache entries were invalidated
	 */
	public long reset()
	{
		// Do nothing if already running (i.e. avoid recursion)
		if (cacheResetRunning.getAndSet(true))
		{
			logger.trace("Avoid calling full cache reset again. We are currently doing it...");
			return 0;
		}

<<<<<<< HEAD
		final SpanMetadata spanMetadata = SpanMetadata.builder()
				.name("Full CacheReset")
				.type(Type.CACHE_OPERATION.getCode()).subType(SubType.CACHE_INVALIDATE.getCode())
				.build();
		return getPerfMonService().monitorSpan(
				() -> reset0(),
				spanMetadata);
	}

	@Nullable
	private PerformanceMonitoringService getPerfMonService()
	{
		// this is called already very early in the startup phase, so we need to avoid an exception if there is no spring context yet
		return SpringContextHolder.instance.getBeanOr(
				PerformanceMonitoringService.class,
				NoopPerformanceMonitoringService.INSTANCE);
	}

	private long reset0()
	{
		long total = 0;
=======
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
		try
		{
			final long total = streamCaches()
					.mapToLong(CachesGroup::invalidateNoFail)
					.sum();

			fireGlobalCacheResetListeners(CacheInvalidateMultiRequest.all());

			lastCacheReset.incrementAndGet();

			logger.info("Reset all: cache instances invalidated ({} cached items invalidated).", total);
			return total;
		}
		finally
		{
			cacheResetRunning.set(false);
		}
	}

	private void fireGlobalCacheResetListeners(final CacheInvalidateMultiRequest multiRequest)
	{
		globalCacheResetListeners.forEach(listener -> fireCacheResetListenerNoFail(listener, multiRequest));

		if (multiRequest.isResetAll())
		{
			cacheResetListenersByTableName.values()
					.stream()
					.flatMap(Collection::stream)
					.forEach(listener -> fireCacheResetListenerNoFail(listener, multiRequest));
		}
		else
		{
			multiRequest.getTableNamesEffective()
					.stream()
					.map(cacheResetListenersByTableName::get)
					.filter(Objects::nonNull)
					.flatMap(Collection::stream)
					.forEach(listener -> fireCacheResetListenerNoFail(listener, multiRequest));
		}
	}

	private void fireCacheResetListenerNoFail(final ICacheResetListener listener, final CacheInvalidateMultiRequest multiRequest)
	{
		try
		{
			listener.reset(multiRequest);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed firing {} for {}. Ignored.", listener, multiRequest, ex);
		}
	}

	/**
	 * Invalidate all cached entries for given TableName.
	 *
	 * @param tableName table name
	 * @return how many cache entries were invalidated
	 */
	public long reset(final String tableName)
	{
		final CacheInvalidateMultiRequest request = CacheInvalidateMultiRequest.allRecordsForTable(tableName);
		return reset(request, ResetMode.LOCAL_AND_BROADCAST);
	}    // reset

	/**
	 * Invalidate all cached entries for given TableName.
	 * <p>
	 * The event won't be broadcasted.
	 *
	 * @param tableName table name
	 * @return how many cache entries were invalidated
	 */
	@SuppressWarnings("UnusedReturnValue")
	public long resetLocal(final String tableName)
	{
		final CacheInvalidateMultiRequest request = CacheInvalidateMultiRequest.allRecordsForTable(tableName);
		return reset(request, ResetMode.LOCAL);
	}    // reset

	/**
	 * Invalidate all cached entries for given TableName/Record_ID.
	 *
	 * @param tableName table name
	 * @param recordId  record if applicable or negative for all
	 * @return how many cache entries were invalidated
	 */
	public long reset(final String tableName, final int recordId)
	{
		final CacheInvalidateMultiRequest request = CacheInvalidateMultiRequest.fromTableNameAndRecordId(tableName, recordId);

		final ResetMode mode = Adempiere.isUnitTestMode()
				? ResetMode.LOCAL
				: ResetMode.LOCAL_AND_BROADCAST;

		return reset(request, mode);
	}

	/**
	 * Reset cache for TableName/Record_ID when given transaction is committed.
	 * <p>
	 * If no transaction was given or given transaction was not found, the cache is reset right away.
	 */
	public void resetLocalNowAndBroadcastOnTrxCommit(final String trxName, final CacheInvalidateMultiRequest request)
	{

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx trx = trxManager.get(trxName, OnTrxMissingPolicy.ReturnTrxNone);
		if (!trxManager.isActive(trx))
		{
			reset(request, ResetMode.LOCAL_AND_BROADCAST);
		}
		else
		{
			reset(request, ResetMode.LOCAL);
			RecordsToResetOnTrxCommitCollector.getCreate(trx).addRecord(request, ResetMode.JUST_BROADCAST);
		}
	}

	enum ResetMode
	{
		LOCAL, LOCAL_AND_BROADCAST, JUST_BROADCAST;

		public boolean isResetLocal()
		{
			return this == LOCAL || this == LOCAL_AND_BROADCAST;
		}

		public boolean isBroadcast()
		{
			return this == LOCAL_AND_BROADCAST || this == JUST_BROADCAST;
		}
	}

	public long reset(@NonNull final CacheInvalidateMultiRequest multiRequest)
	{
		return reset(multiRequest, ResetMode.LOCAL_AND_BROADCAST);
	}

	/**
	 * Invalidate all cached entries for given TableName/Record_ID.
	 *
	 * @return how many cache entries were invalidated (estimated!)
	 */
	long reset(@NonNull final CacheInvalidateMultiRequest multiRequest, @NonNull final ResetMode mode)
	{
		final SpanMetadata spanMetadata = SpanMetadata.builder()
				.name("CacheReset")
				.type(Type.CACHE_OPERATION.getCode()).subType(SubType.CACHE_INVALIDATE.getCode())
				.label("resetMode", mode.toString())
				.build();
		return getPerfMonService().monitorSpan(
				() -> reset0(multiRequest, mode),
				spanMetadata);
	}

	private Long reset0(final CacheInvalidateMultiRequest multiRequest, final ResetMode mode)
	{
		final long resetCount;
		if (mode.isResetLocal())
		{
			resetCount = invalidateForMultiRequest(multiRequest);
			fireGlobalCacheResetListeners(multiRequest);
		}
		else
		{
			resetCount = 0;
		}

		//
		// Broadcast cache invalidation.
		// We do this, even if we don't have any cache interface registered locally, because there might be remotely.
		if (mode.isBroadcast())
		{
			CacheInvalidationRemoteHandler.instance.postEvent(multiRequest);
		}

		return resetCount;
	}

	private long invalidateForMultiRequest(final CacheInvalidateMultiRequest multiRequest)
	{
		if (multiRequest.isResetAll())
		{
			return reset();
		}

		long total = 0;
		for (final CacheInvalidateRequest request : multiRequest.getRequests())
		{
			final long totalPerRequest = invalidateForRequest(request);
			total += totalPerRequest;
		}

		return total;
	}

	private long invalidateForRequest(@NonNull final CacheInvalidateRequest request)
	{
		if (request.isAllRecords())
		{
			final CacheLabel label = CacheLabel.ofTableName(request.getTableNameEffective());
			try (final MDCCloseable ignored = CacheMDC.putCacheLabel(label))
			{
				final CachesGroup cachesGroup = getCachesGroupIfPresent(label);
				if (cachesGroup == null)
				{
					return 0;
				}

				return cachesGroup.invalidateAllNoFail();
			}
		}
		else
		{
			long resetCount = 0;

			final TableRecordReference childRecordRef = request.getChildRecordOrNull();
			if (childRecordRef != null)
			{
				resetCount += invalidateForRecord(childRecordRef);
			}
			final TableRecordReference rootRecordRef = request.getRootRecordOrNull();
			if (rootRecordRef != null)
			{
				resetCount += invalidateForRecord(rootRecordRef);
			}

			return resetCount;
		}
	}

	private long invalidateForRecord(@NonNull final TableRecordReference recordRef)
	{
		final CacheLabel label = CacheLabel.ofTableName(recordRef.getTableName());
		try (final MDCCloseable ignored = CacheMDC.putCacheLabel(label))
		{
			final CachesGroup cachesGroup = getCachesGroupIfPresent(label);
			if (cachesGroup == null)
			{
				return 0;
			}

			return cachesGroup.invalidateForRecordNoFail(recordRef);
		}
	}

	/**
	 * @return how many cached elements do we have in total
	 */
	private long computeTotalSize()
	{
		return streamCaches().mapToLong(CacheInterface::size).sum();
	}

	/**
	 * String Representation
	 */
	@Override
	public String toString()
	{
		return "CacheMgt[Instances=" + cachesByLabel.size() + "]";
	}

	/**
	 * Extended String Representation
	 */
	public String toStringX()
	{
		return "CacheMgt[Instances=" + cachesByLabel.size() + ", Elements=" + computeTotalSize() + "]";
	}

	public void addCacheResetListener(@NonNull final ICacheResetListener cacheResetListener)
	{
		final boolean added = globalCacheResetListeners.addIfAbsent(cacheResetListener);
		if (added)
		{
			logger.info("Registered global cache reset listener: {}", cacheResetListener);
		}
		else
		{
			logger.warn("Skip registering global cache reset listener because it was already registered: {}", cacheResetListener);
		}
	}

	/**
	 * Adds an listener which will be fired when the cache for given table is about to be reset.
	 */
	public void addCacheResetListener(@NonNull final String tableName, @NonNull final ICacheResetListener cacheResetListener)
	{
		cacheResetListenersByTableName
				.computeIfAbsent(tableName, k -> new CopyOnWriteArrayList<>())
				.addIfAbsent(cacheResetListener);
	}

	@SuppressWarnings("UnusedReturnValue")
	public boolean removeCacheResetListener(@NonNull final String tableName, @NonNull final ICacheResetListener cacheResetListener)
	{
		final CopyOnWriteArrayList<ICacheResetListener> cacheResetListeners = cacheResetListenersByTableName.get(tableName);
		if (cacheResetListeners == null)
		{
			return false;
		}

		return cacheResetListeners.remove(cacheResetListener);
	}

	private Stream<CacheInterface> streamCaches()
	{
		return cachesByLabel.values().stream()
				.flatMap(CachesGroup::streamCaches)
				.collect(GuavaCollectors.distinctBy(CacheInterface::getCacheId));
	}

	public Stream<CCacheStats> streamStats(@NonNull final CCacheStatsPredicate predicate)
	{
		return streamStats().filter(predicate);
	}

	public Stream<CCacheStats> streamStats()
	{
		return streamCaches()
				.map(CacheMgt::extractStatsOrNull)
				.filter(Objects::nonNull);
	}

	private static CCacheStats extractStatsOrNull(final CacheInterface cacheInterface)
	{
		return cacheInterface instanceof CCache ? ((CCache<?, ?>)cacheInterface).stats() : null;
	}

	public Optional<CacheInterface> getById(final long cacheId)
	{
		return streamCaches()
				.filter(cache -> cache.getCacheId() == cacheId)
				.findFirst();
	}

	/**
	 * Collects records that needs to be removed from cache when a given transaction is committed
	 */
	private static final class RecordsToResetOnTrxCommitCollector
	{
		/**
		 * Gets/creates the records collector which needs to be reset when transaction is committed
		 */
		public static RecordsToResetOnTrxCommitCollector getCreate(final ITrx trx)
		{
			return trx.getProperty(TRX_PROPERTY, () -> {

				final RecordsToResetOnTrxCommitCollector collector = new RecordsToResetOnTrxCommitCollector();

				// Listens {@link ITrx}'s after-commit and fires enqueued cache invalidation requests
				trx.getTrxListenerManager()
						.newEventListener(TrxEventTiming.AFTER_COMMIT)
						.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
						.registerHandlingMethod(innerTrx -> {

							final RecordsToResetOnTrxCommitCollector innerCollector = innerTrx.getProperty(TRX_PROPERTY);
							if (innerCollector == null)
							{
								return;
							}
							innerCollector.sendRequestsAndClear();
						});

				return collector;
			});
		}

		private static final String TRX_PROPERTY = RecordsToResetOnTrxCommitCollector.class.getName();

		private final Map<CacheInvalidateRequest, ResetMode> request2resetMode = Maps.newConcurrentMap();

		/**
		 * Enqueues a record
		 */
		public void addRecord(@NonNull final CacheInvalidateMultiRequest multiRequest, @NonNull final ResetMode resetMode)
		{
			multiRequest.getRequests()
					.forEach(request -> request2resetMode.put(request, resetMode));
			logger.debug("Scheduled cache invalidation on transaction commit: {} ({})", multiRequest, resetMode);
		}

		/**
		 * Reset the cache for all enqueued records
		 */
		private void sendRequestsAndClear()
		{
			if (request2resetMode.isEmpty())
			{
				return;
			}

			final CacheMgt cacheMgt = CacheMgt.get();

			final ImmutableList.Builder<CacheInvalidateRequest> resetLocalRequestsBuilder = ImmutableList.builder();
			final ImmutableList.Builder<CacheInvalidateRequest> broadcastRequestsBuilder = ImmutableList.builder();
			request2resetMode.forEach((request, resetMode) -> {
				if (resetMode.isResetLocal())
				{
					resetLocalRequestsBuilder.add(request);
				}
				if (resetMode.isBroadcast())
				{
					broadcastRequestsBuilder.add(request);
				}
			});

			final ImmutableList<CacheInvalidateRequest> resetLocalRequests = resetLocalRequestsBuilder.build();
			if (!resetLocalRequests.isEmpty())
			{
				cacheMgt.reset(CacheInvalidateMultiRequest.of(resetLocalRequests), ResetMode.LOCAL);
			}

			final ImmutableList<CacheInvalidateRequest> broadcastRequests = broadcastRequestsBuilder.build();
			if (!broadcastRequests.isEmpty())
			{
				cacheMgt.reset(CacheInvalidateMultiRequest.of(broadcastRequests), ResetMode.JUST_BROADCAST);
			}

			request2resetMode.clear();
		}
	}

	private static class CachesGroup
	{
		private final CacheLabel label;
		private final ConcurrentMap<Long, CacheInterface> caches = new MapMaker().weakValues().makeMap();

		public CachesGroup(@NonNull final CacheLabel label)
		{
			this.label = label;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("label", label)
					.add("size", caches.size())
					.toString();
		}

		public void addCache(@NonNull final CacheInterface cache)
		{
			try (final IAutoCloseable ignored = CacheMDC.putCache(cache))
			{
				caches.put(cache.getCacheId(), cache);
			}
		}

		public void removeCache(@NonNull final CacheInterface cache)
		{
			try (final IAutoCloseable ignored = CacheMDC.putCache(cache))
			{
				caches.remove(cache.getCacheId());
			}
		}

		private Stream<CacheInterface> streamCaches()
		{
			return caches.values().stream().filter(Objects::nonNull);
		}

		private long invalidateAllNoFail()
		{
			return streamCaches()
					.mapToLong(CachesGroup::invalidateNoFail)
					.sum();
		}

		private long invalidateForRecordNoFail(final TableRecordReference recordRef)
		{
			return streamCaches()
					.mapToLong(cache -> invalidateNoFail(cache, recordRef))
					.sum();
		}

		private static long invalidateNoFail(@Nullable final CacheInterface cacheInstance)
		{
			try (final IAutoCloseable ignored = CacheMDC.putCache(cacheInstance))
			{
				if (cacheInstance == null)
				{
					return 0;
				}
				return cacheInstance.reset();
			}
			catch (final Exception ex)
			{
				// log but don't fail
				logger.warn("Error while resetting {}. Ignored.", cacheInstance, ex);
				return 0;
			}
		}

		private static long invalidateNoFail(final CacheInterface cacheInstance, final TableRecordReference recordRef)
		{
			try (final IAutoCloseable ignored = CacheMDC.putCache(cacheInstance))
			{
				return cacheInstance.resetForRecordId(recordRef);
			}
			catch (final Exception ex)
			{
				// log but don't fail
				logger.warn("Error while resetting {} for {}. Ignored.", cacheInstance, recordRef, ex);
				return 0;
			}
		}
	}
}
