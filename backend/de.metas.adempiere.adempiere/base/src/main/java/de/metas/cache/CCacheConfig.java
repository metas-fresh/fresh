package de.metas.cache;

import de.metas.cache.CCache.CacheMapType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CCacheConfig
{
	@NonNull CacheMapType cacheMapType;
	int initialCapacity;
	int maximumSize;
	int expireMinutes;
}
