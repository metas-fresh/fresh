package de.metas.cache.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache.CacheMapType;
import de.metas.cache.CCacheStats;
import de.metas.cache.CacheLabel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Set;

@Value
@Builder
@Jacksonized
public class JsonCacheStats
{
	//
	// Cache Info
	long cacheId;
	@NonNull String name;
	@NonNull Set<String> labels;
	@NonNull CacheMapType type;
	int initialCapacity;
	int maximumSize;
	int expireMinutes;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable String debugAcquireStacktrace;

	//
	// Actual Stats
	long size;
	long hitCount;
	@NonNull BigDecimal hitRate;
	long missCount;
	@NonNull BigDecimal missRate;

	public static JsonCacheStats of(@NonNull final CCacheStats stats)
	{
		return JsonCacheStats.builder()
				.cacheId(stats.getCacheId())
				.name(stats.getName())
				.labels(stats.getLabels().stream()
						.map(CacheLabel::getName)
						.collect(ImmutableSet.toImmutableSet()))
				.type(stats.getConfig().getCacheMapType())
				.initialCapacity(stats.getConfig().getInitialCapacity())
				.maximumSize(stats.getConfig().getMaximumSize())
				.expireMinutes(stats.getConfig().getExpireMinutes())
				.debugAcquireStacktrace(stats.getDebugAcquireStacktrace())
				//
				.size(stats.getSize())
				.hitCount(stats.getHitCount())
				.hitRate(stats.getHitRate().toBigDecimal())
				.missCount(stats.getMissCount())
				.missRate(stats.getMissRate().toBigDecimal())
				.build();
	}
}
