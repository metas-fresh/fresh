package de.metas.cache;

import com.google.common.collect.ImmutableSet;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
public class CCacheStats
{
	//
	// Cache Info
	long cacheId;
	@NonNull String name;
	@NonNull ImmutableSet<CacheLabel> labels;
	@NonNull CCacheConfig config;
	@Nullable String debugAcquireStacktrace;

	//
	// Actual Stats
	long size;
	long hitCount;
	long missCount;
	@NonNull Percent hitRate;
	@NonNull Percent missRate;

	@Builder
	@Jacksonized
	private CCacheStats(
			final long cacheId,
			@NonNull final String name,
			@Nullable final ImmutableSet<CacheLabel> labels,
			@NonNull final CCacheConfig config,
			@Nullable final String debugAcquireStacktrace,
			final long size,
			final long hitCount,
			final long missCount)
	{
		this.cacheId = cacheId;
		this.name = name;
		this.labels = labels != null ? labels : ImmutableSet.of();
		this.config = config;
		this.debugAcquireStacktrace = debugAcquireStacktrace;
		this.size = size;
		this.hitCount = hitCount;
		this.missCount = missCount;

		this.hitRate = Percent.of(hitCount, hitCount + missCount);
		this.missRate = Percent.ONE_HUNDRED.subtract(hitRate);

	}
}
