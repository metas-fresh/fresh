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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.UncheckedExecutionException;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Trace;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * metasfresh Cache.
 *
 * @param <K> Key
 * @param <V> Value
 * @author Jorg Janke
 * @version $Id: CCache.java,v 1.2 2006/07/30 00:54:35 jjanke Exp $
 */
public class CCache<K, V> implements CacheInterface
{
	/**
	 * Creates a new LRU cache
	 *
	 * @param cacheName          cache name; shall respect the current naming conventions, see {@link #extractTableNameForCacheName(String)}
	 * @param maxSize            LRU cache maximum size
	 * @param expireAfterMinutes if positive, the entries will expire after given number of minutes
	 * @return new cache instance
	 */
	public static <K, V> CCache<K, V> newLRUCache(final String cacheName, final int maxSize, final int expireAfterMinutes)
	{
		return CCache.<K, V>builder()
				.cacheName(cacheName)
				// .tableName(null) // auto-detect tableName
				.maximumSize(maxSize)
				.expireMinutes(expireAfterMinutes)
				.cacheMapType(CacheMapType.LRU)
				.build();
	}

	/**
	 * Similar to {@link #newLRUCache(String, int, int)}.
	 *
	 * @param cacheName cache name; shall respect the current naming conventions, see {@link #extractTableNameForCacheName(String)}
	 * @
	 */
	public static <K, V> CCache<K, V> newCache(final String cacheName, final int initialCapacity, final int expireAfterMinutes)
	{
		return CCache.<K, V>builder()
				.cacheName(cacheName)
				// .tableName(null) // auto-detect tableName
				.initialCapacity(initialCapacity)
				.expireMinutes(expireAfterMinutes)
				.cacheMapType(CacheMapType.HashMap)
				.build();
	}

	public enum CacheMapType
	{
		/**
		 * Data is cached in a normal hash map
		 */
		HashMap,

		/**
		 * Data is cached in a LRU map (least recently used). This means that if the caches size limit is reached, then the oldest record is removed from cache in order to add a new record.
		 * This means that we can have a have a cache with a defined (limited) size without any expiration time.
		 */
		LRU,
	}

	private static final Logger logger = LogManager.getLogger(CCache.class);

	@NonNull private final CCacheConfig config;

	/**
	 * Internal map that is used as cache
	 */
	@NonNull private final Cache<K, V> cache;

	static final AtomicLong NEXT_CACHE_ID = new AtomicLong(1);
	/**
	 * unique cache ID, mainly used for tracking, logging and debugging
	 */
	@Getter private final long cacheId;

	@NonNull @Getter private final String cacheName;
	@NonNull @Getter private final ImmutableSet<CacheLabel> labels;

	public static final int EXPIREMINUTES_Never = 0;
	/**
	 * Just reset
	 */
	private boolean m_justReset = true;

	/**
	 * Can provide a collection of cache keys for a given record reference.
	 */
	private final Optional<CachingKeysMapper<K>> invalidationKeysMapper;

	@Nullable private final String debugAcquireStacktrace;

	@Nullable private final CacheAdditionListener<K, V> additionListener;

	/**
	 * Metasfresh Cache - expires after 2 hours
	 *
	 * @param name            (table) name of the cache
	 * @param initialCapacity initial capacity
	 */
	public CCache(final String name, final int initialCapacity)
	{
		this(name, initialCapacity, 120);
	}    // CCache

	/**
	 * Metasfresh Cache
	 *
	 * @param name            (table) name of the cache
	 * @param initialCapacity initial capacity
	 * @param expireMinutes   expire after minutes (0=no expire)
	 */
	public CCache(final String name, final int initialCapacity, final int expireMinutes)
	{
		this(name, // cache name
				null, // auto-detect tableName
				null, // additionalTableNamesToResetFor
				null, // additionalLabels
				initialCapacity,
				null,
				expireMinutes,
				null,
				null,
				null,
				null);
	}

	@Builder
	protected CCache(
			@Nullable final String cacheName,
			@Nullable final String tableName,
			@Nullable @Singular("additionalTableNameToResetFor") final Set<String> additionalTableNamesToResetFor,
			@Nullable @Singular("additionalLabel") final Set<CacheLabel> additionalLabels,
			@Nullable final Integer initialCapacity,
			@Nullable final Integer maximumSize,
			@Nullable final Integer expireMinutes,
			@Nullable final CacheMapType cacheMapType,
			@Nullable final CachingKeysMapper<K> invalidationKeysMapper,
			@Nullable final CacheRemovalListener<K, V> removalListener,
			@Nullable final CacheAdditionListener<K, V> additionListener)
	{
		this.cacheId = NEXT_CACHE_ID.getAndIncrement();

		this.invalidationKeysMapper = Optional.ofNullable(invalidationKeysMapper);
		this.additionListener = additionListener;

		final String tableNameEffective;
		boolean isNoCacheName = false;
		if (cacheName == null)
		{
			if (tableName == null)
			{
<<<<<<< HEAD
				this.cacheName = "$NoCacheName$" + cacheId;
				tableNameEffective = "$NoTableName$" + cacheId;
=======
				if (additionalTableNamesToResetFor != null && additionalTableNamesToResetFor.size() == 1)
				{
					tableNameEffective = additionalTableNamesToResetFor.iterator().next();
					this.cacheName = tableNameEffective;
				}
				else
				{
					this.cacheName = "$NoCacheName$" + cacheId;
					tableNameEffective = CacheLabel.NO_TABLENAME_PREFIX + cacheId;
					isNoCacheName = true;
				}
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
			}
			else
			{
				this.cacheName = tableName;
				tableNameEffective = tableName;
			}
		}
		else // cacheName != null
		{
			this.cacheName = cacheName;

			if (tableName == null)
			{
				final String extractedTableName = extractTableNameForCacheName(cacheName);
				tableNameEffective = extractedTableName != null ? extractedTableName : cacheName;
			}
			else
			{
				tableNameEffective = tableName;
			}
		}

		this.labels = buildCacheLabels(tableNameEffective, additionalTableNamesToResetFor, additionalLabels);

		final CCacheConfigDefaults configDefaults = CacheMgt.get().getConfigDefaults();
		final CacheMapType cacheMapTypeEffective = cacheMapType != null ? cacheMapType : configDefaults.getCacheMapType();
		final CCacheConfig.CCacheConfigBuilder configBuilder = CCacheConfig.builder()
				.cacheMapType(cacheMapTypeEffective)
				.expireMinutes(expireMinutes != null && expireMinutes >= 0 ? expireMinutes : configDefaults.getExpireMinutes());
		if (cacheMapTypeEffective == CacheMapType.HashMap)
		{
			configBuilder.initialCapacity(initialCapacity != null && initialCapacity >= 0 ? initialCapacity : configDefaults.getInitialCapacity());
		}
		else if (cacheMapTypeEffective == CacheMapType.LRU)
		{
			final Integer maximumSizeEffective = maximumSize != null ? maximumSize : initialCapacity;
			configBuilder.maximumSize(maximumSizeEffective != null && maximumSizeEffective >= 0 ? maximumSizeEffective : configDefaults.getMaximumSize());
		}
		else
		{
			throw new AdempiereException("Unknown CacheMapType: " + cacheMapTypeEffective);
		}
		this.config = configBuilder.build();

		this.cache = newGuavaCache(this.config, removalListener);

		this.debugAcquireStacktrace = configDefaults.isCaptureStacktrace() || isNoCacheName ? Trace.toOneLineStackTraceString() : null;

		//
		// Register it to CacheMgt
		CacheMgt.get().register(this);
	}

	private static <K, V> Cache<K, V> newGuavaCache(
			@NonNull final CCacheConfig config,
			@Nullable final CacheRemovalListener<K, V> removalListener)
	{
		final CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().recordStats();
		if (config.getInitialCapacity() > 0)
		{
			cacheBuilder.initialCapacity(config.getInitialCapacity());
		}
		if (config.getMaximumSize() > 0)
		{
			cacheBuilder.maximumSize(config.getMaximumSize());
		}
		if (config.getExpireMinutes() > 0)
		{
			cacheBuilder.expireAfterWrite(config.getExpireMinutes(), TimeUnit.MINUTES);
		}
		if (removalListener != null)
		{
			//noinspection ResultOfMethodCallIgnored
			cacheBuilder.removalListener(notification -> {
				@SuppressWarnings("unchecked") final K key = (K)notification.getKey();
				@SuppressWarnings("unchecked") final V value = (V)notification.getValue();
				removalListener.itemRemoved(key, value);
			});
		}

		return cacheBuilder.build();
	}

	/**
	 * Extracts TableName from given <code>cacheName</code>.
	 * <p>
	 * NOTE: we assume cacheName has following format: TableName#by#ColumnName1#ColumnName2...
	 *
	 * @return tableName or null
	 */
	@Nullable
	@VisibleForTesting
	public static String extractTableNameForCacheName(final String cacheName)
	{
		if (cacheName == null || cacheName.isEmpty())
		{
			return null;
		}
		final int idx = cacheName.indexOf("#");
		if (idx <= 0)
		{
			return null;
		}

		final String tableName = cacheName.substring(0, idx).trim();
		if (tableName.isEmpty())
		{
			return null;
		}

		return tableName;
	}

<<<<<<< HEAD
	private static ImmutableSet<CacheLabel> buildCacheLabels(@NonNull final String tableName, final Set<String> additionalTableNamesToResetFor)
=======
	@NonNull
	private static ImmutableSet<CacheLabel> buildCacheLabels(
			@NonNull final String tableName,
			@Nullable final Set<String> additionalTableNamesToResetFor,
			@Nullable final Set<CacheLabel> additionalLabels)
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
	{
		final ImmutableSet.Builder<CacheLabel> builder = ImmutableSet.builder();
		builder.add(CacheLabel.ofTableName(tableName));

		if (additionalTableNamesToResetFor != null && !additionalTableNamesToResetFor.isEmpty())
		{
			additionalTableNamesToResetFor.stream()
					.map(CacheLabel::ofTableName)
					.forEach(builder::add);
		}

		if (additionalLabels != null && !additionalLabels.isEmpty())
		{
			builder.addAll(additionalLabels);
		}

		return builder.build();
	}

	/**
	 * Cache was just reset.
	 * <p>
	 * NOTE:
	 * <ul>
	 * <li>this flag is set to <code>false</code> after any change operation (e.g. {@link #put(Object, Object)})
	 * <li>this flag is set to <code>true</code> after {@link #reset()}
	 *
	 * @return true if it was just reset
	 */
	public final boolean isReset() {return m_justReset;}

	/**
	 * Reset Cache.
	 * <p>
	 * It is the same as calling {@link #clear()} but this method will return how many items were cleared.
	 *
	 * @return number of items cleared
	 * @see de.metas.cache.CacheInterface#reset()
	 */
	@Override
	public long reset()
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			final long no = cache.size();
			clear();
			if (no > 0)
			{
				logger.trace("Reset {} entries from {}", no, this);
			}
			return no;
		}
	}

	private void clear()
	{
		// Clear
		cache.invalidateAll();
		cache.cleanUp();

		m_justReset = true;
	}    // clear

	@Override
	public long resetForRecordId(@NonNull final TableRecordReference recordRef)
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			if (invalidationKeysMapper.isPresent())
			{
				return resetForRecordIdUsingKeysMapper(recordRef, invalidationKeysMapper.get());
			}
			else
			{
				// NOTE: resetting only by "key" is not supported, so we are resetting everything
				return reset();
			}
		}
	}

	private long resetForRecordIdUsingKeysMapper(
			@NonNull final TableRecordReference recordRef,
			@NonNull final CachingKeysMapper<K> keysMapper)
	{
		if (keysMapper.isResetAll(recordRef))
		{
			logger.debug("resetForRecordIdUsingKeysMapper - given keysMapper indicated to reset all for recordRef={}; -> resetting the whole cache", recordRef);
			return reset();
		}

		long counter = 0; // note that also the "reset-all" reset() method only returns an approx number.

		final Collection<K> keysToReset = keysMapper.computeCachingKeys(recordRef);
		logger.debug("resetForRecordIdUsingKeysMapper - given keysMapper indicated the following keys for recordRef={}: {}", keysToReset, recordRef);

		for (final K key : keysToReset)
		{
			final V removedItem = remove(key);
			final boolean keyRemoved = removedItem != null;
			if (keyRemoved)
			{
				counter++;
			}
		}
		return counter;
	}

	@Override
	public String toString()
	{
		final long size = cache.size();
		final StringBuilder sb = new StringBuilder("CCache[")
				.append(cacheName)
				.append(", size=").append(size)
				.append(", id=").append(cacheId);

		if (this.debugAcquireStacktrace != null)
		{
			sb.append("\n").append(this.debugAcquireStacktrace);
		}

		//
		// Show a first 10 items
		// usually that's enough to figure out what's in the cache.
		final int sampleSize = 10;
		final String firstEntries = cache.asMap().entrySet()
				.stream()
				.limit(sampleSize)
				.map(entry -> entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining("\n\t"));
		if (!firstEntries.isEmpty())
		{
			sb.append("\nfirst ").append(sampleSize).append(" entries={")
					.append("\n\t").append(firstEntries)
					.append("\n}");
		}

		sb.append("]");

		return sb.toString();
	}

	public boolean containsKey(final K key)
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			return cache.getIfPresent(key) != null;
		}
	}

	public V remove(final K key)
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			final V value = cache.getIfPresent(key);
			cache.invalidate(key);
			logger.debug("remove - Removed key={}; item that was actually in this cache={}", key, value);
			return value;
		}
	}

	public void removeAll(final Iterable<K> keys)
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			cache.invalidateAll(keys);
		}
	}

	/**
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Nullable
	public V get(final K key)
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			final V result = cache.getIfPresent(key);
			logger.debug("get - key={}; result={}", key, result);
			return result;
		}
	}

	/**
	 * Gets cached value by <code>key</code>.
	 * <p>
	 * For more informations, see {@link #get(Object, Callable)}.
	 */
	public V get(final K key, final Supplier<V> valueInitializer)
	{
		if (valueInitializer == null)
		{
			return cache.getIfPresent(key);
		}

		return get(key, new Callable<>()
		{
			@Override
			public String toString()
			{
				return "Callable[" + valueInitializer + "]";
			}

			@Override
			public V call()
			{
				return valueInitializer.get();
			}
		});
	}

	/**
	 * Gets cached value by <code>key</code>.
	 * <p>
	 * If value is not present in case it will try to initialize it by using <code>valueInitializer</code>.
	 * <p>
	 * If the <code>valueInitializer</code> returns null then this method will return <code>null</code> and the value will NOT be cached.
	 *
	 * @param valueInitializer optional cache initializer.
	 * @return cached value or <code>null</code>
	 */
	public V get(final K key, final Callable<V> valueInitializer)
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			if (valueInitializer == null)
			{
				return cache.getIfPresent(key);
			}

			try
			{
				return cache.get(key, valueInitializer);
			}
			catch (final InvalidCacheLoadException e)
			{
				// Exception thrown when the Callable returns null
				// We can safely ignore it and return null.
				// The value was not cached.
				return null;
			}
			catch (final ExecutionException e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
			catch (final UncheckedExecutionException e)
			{
				throw (RuntimeException)e.getCause();
			}
			catch (final ExecutionError e)
			{
				throw (Error)e.getCause();
			}
		}
	}

	/**
	 * Same as {@link #get(Object, Callable)}. Introduced here to be able to use it with lambdas, without having ambiguous method calls.
	 *
	 * @see #get(Object, Callable).
	 * @see #get(Object, Supplier)
	 */
	public V getOrLoad(final K key, final Callable<V> valueLoader)
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			return get(key, valueLoader);
		}
	}

	public V getOrLoad(final K key, @NonNull final Function<K, V> valueLoader)
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			final Callable<V> callable = () -> valueLoader.apply(key);
			return get(key, callable);
		}
	}

	/**
	 * Gets all values which are identified by given keys.
	 * <p>
	 * For those keys which were not found in cache, the <code>valuesLoader</code> will be called <b>one time</b> with all missing keys.
	 * The expected return of <code>valuesLoader</code> is a key/value map of all those values loaded.
	 * <p>
	 * The values which were just loaded will be also added to cache.
	 *
	 * @return values (IMPORTANT: order is not guaranteed)
	 */
	public Collection<V> getAllOrLoad(final Collection<K> keys, final Function<Set<K>, Map<K, V>> valuesLoader)
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			if (keys.isEmpty())
			{
				logger.debug("getAllOrLoad - Given keys is empty; -> return empty list");
				return ImmutableList.of();
			}

			//
			// Fetch from cache what's available
			final List<V> values = new ArrayList<>(keys.size());
			final Set<K> keysToLoad = new HashSet<>();
			for (final K key : ImmutableSet.copyOf(keys))
			{
				final V value = cache.getIfPresent(key);
				if (value == null)
				{
					logger.debug("getAllOrLoad - Cache miss for key={}; -> adding it to 'keysToLoad'", key);
					keysToLoad.add(key);
				}
				else
				{
					logger.debug("getAllOrLoad - Cache hit for key={}; -> adding it to result values", key);
					values.add(value);
				}
			}

			//
			// Load the missing keys if any
			if (keysToLoad.isEmpty())
			{
				logger.debug("getAllOrLoad - all keys had cached values; nothing to load");
			}
			else
			{
				logger.debug("getAllOrLoad - Appling valuesLoader to load values for keysToLoad={}", keysToLoad);
				final Map<K, V> valuesLoaded = valuesLoader.apply(keysToLoad);

				// add loaded values to cache and notify listener
				for (final Entry<K, V> entry : valuesLoaded.entrySet())
				{
					final K key = entry.getKey();
					final V value = entry.getValue();

					cache.put(key, value);
					fireAdditionListener(key, value);
				}
				values.addAll(valuesLoaded.values()); // add loaded values to the list we will return
			}

			return values;
		}
	}

	/**
	 * Return the value, if present, otherwise throw an exception to be created by the provided supplier.
	 */
	@NonNull
	public <E extends Throwable> V getOrElseThrow(final K key, final Supplier<E> exceptionSupplier) throws E
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			final V value = get(key);
			if (value == null)
			{
				throw exceptionSupplier.get();
			}
			return value;
		}
	}

	public void put(final K key, final V value)
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			m_justReset = false;
			if (value == null)
			{
				cache.invalidate(key);
			}
			else
			{
				cache.put(key, value);
				fireAdditionListener(key, value);
			}
		}
	}

	private void fireAdditionListener(final K key, final V value)
	{
		logger.debug("fireAdditionListener - Item added; key={}; value={}", key, value);
		if (additionListener != null)
		{
			additionListener.itemAdded(key, value);
		}
	}

	/**
	 * Add all key/value pairs to this cache.
	 *
	 * @param map key/value pairs
	 */
	public void putAll(final Map<? extends K, ? extends V> map)
	{
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			cache.putAll(map);

			for (final Entry<? extends K, ? extends V> entry : map.entrySet())
			{
				fireAdditionListener(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty()
	{
		return cache.size() == 0;
	}    // isEmpty

	/**
	 * @see java.util.Map#keySet()
	 */
	public Set<K> keySet()
	{
		return cache.asMap().keySet();
	}    // keySet

	/**
	 * @see java.util.Map#size()
	 */
	@Override
	public long size()
	{
		return cache.size();
	}    // size

	/**
	 * @see java.util.Map#values()
	 */
	public Collection<V> values()
	{
		return cache.asMap().values();
	}    // values

	@Override
	protected final void finalize() throws Throwable
	{
		// NOTE: to avoid memory leaks we need to programatically clear our internal state
		try (final IAutoCloseable ignored = CacheMDC.putCache(this))
		{
			logger.debug("Running finalize");
			if (cache != null)
			{
				cache.invalidateAll();
			}
		}
	}

	public CCacheStats stats()
	{
		final CacheStats guavaStats = cache.stats();
		return CCacheStats.builder()
				.cacheId(cacheId)
				.name(cacheName)
				.labels(labels)
				.config(config)
				.debugAcquireStacktrace(debugAcquireStacktrace)
				//
				.size(cache.size())
				.hitCount(guavaStats.hitCount())
				.missCount(guavaStats.missCount())
				.build();
	}

<<<<<<< HEAD
	@SuppressWarnings("serial")
	public static final class CCacheStats implements Serializable
	{
		// NOTE: must be Json serializable!!!

		private final long cacheId;
		private final String name;
		private final long size;
		private final CacheStats guavaStats;

		private CCacheStats(final long cacheId, final String name, final long size, final CacheStats guavaStats)
		{
			this.cacheId = cacheId;
			this.name = name;
			this.size = size;
			this.guavaStats = guavaStats;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("name", name)
					.add("size", size)
					.add("guavaStats", guavaStats)
					.add("cacheId", cacheId)
					.toString();
		}

		@Override
		public int hashCode()
		{
			return Objects.hash(cacheId, name, size, guavaStats);
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (obj instanceof CCacheStats)
			{
				final CCacheStats other = (CCacheStats)obj;
				return cacheId == other.cacheId
						&& name.equals(other.name)
						&& size == other.size
						&& guavaStats.equals(other.guavaStats);
			}
			return false;
		}

		public long getCacheId()
		{
			return cacheId;
		}

		public String getName()
		{
			return name;
		}

		public long getSize()
		{
			return size;
		}

		public CacheStats getGuavaStats()
		{
			return guavaStats;
		}
	}
}	// CCache
=======
	private boolean isNoCache()
	{
		return allowDisablingCacheByThreadLocal && ThreadLocalCacheController.instance.isNoCache();
	}
}
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
