package de.metas.cache;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;

import java.util.HashSet;
import java.util.Set;

/**
 * An extension of {@link CCache} which is used to store models indexed by their primary key.
 * <p>
 * NOTE: we use Object as cache key because in future i think we will move from Integer keys to something else (UUID, String etc).
 *
 * @param <V>
 * @author tsa
 */
public class IDCache<V> extends CCache<Object, V>
{
	private static final CachingKeysMapper<Object> KEYS_MAPPER = tableRecordReference -> ImmutableList.of(tableRecordReference.getRecord_ID());
	public static final CacheLabel LABEL_MODEL_CACHE = CacheLabel.ofString("MODEL_CACHE");
	public static final CacheLabel LABEL_MODEL_CACHE_IN_TRANSACTION = CacheLabel.ofString("MODEL_CACHE_IN_TRANSACTION");

	public IDCache(final String tableName,
				   final String trxName,
				   final int initialCapacity,
				   final int expireMinutes,
				   final CacheMapType cacheMapType)
	{
		super(
				tableName + "#TrxName=" + trxName,
				tableName,
				null, // additionalTableNamesToResetFor
				computeAdditionalLabels(trxName),
				initialCapacity,
				null,
				expireMinutes,
				cacheMapType,
				KEYS_MAPPER,
				null,
				null);

		Check.assumeNotEmpty(tableName, "tableName not empty");
	}

	private static Set<CacheLabel> computeAdditionalLabels(final String trxName)
	{
		final HashSet<CacheLabel> additionalLabels = new HashSet<>();
		additionalLabels.add(LABEL_MODEL_CACHE);
		if (trxName != null)
		{
			additionalLabels.add(LABEL_MODEL_CACHE_IN_TRANSACTION);
		}

		return additionalLabels;
	}

}
