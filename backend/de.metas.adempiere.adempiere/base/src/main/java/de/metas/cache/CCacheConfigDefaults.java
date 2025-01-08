package de.metas.cache;

import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.compiere.SpringContextHolder;

@Value
@Builder
@Jacksonized
public class CCacheConfigDefaults
{
	public static final CCacheConfigDefaults DEFAULTS = builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(0)
			.maximumSize(1000)
			.expireMinutes(0)
			.captureStacktrace(false)
			.build();

	@NonNull CCache.CacheMapType cacheMapType;
	int initialCapacity;
	int maximumSize;
	int expireMinutes;
	boolean captureStacktrace;

	private static final String PROPERTY_PREFIX = "metasfresh.cache.defaults.";

	public static CCacheConfigDefaults computeFrom(@NonNull final SpringContextHolder springContextHolder)
	{
		return builder()
				.cacheMapType(springContextHolder.getProperty(PROPERTY_PREFIX + "cacheMapType").map(CCache.CacheMapType::valueOf).orElse(DEFAULTS.cacheMapType))
				.initialCapacity(springContextHolder.getProperty(PROPERTY_PREFIX + "initialCapacity").map(NumberUtils::asIntOrZero).orElse(DEFAULTS.initialCapacity))
				.maximumSize(springContextHolder.getProperty(PROPERTY_PREFIX + "maximumSize").map(NumberUtils::asIntOrZero).orElse(DEFAULTS.maximumSize))
				.expireMinutes(springContextHolder.getProperty(PROPERTY_PREFIX + "expireMinutes").map(NumberUtils::asIntOrZero).orElse(DEFAULTS.expireMinutes))
				.captureStacktrace(springContextHolder.getProperty(PROPERTY_PREFIX + "captureStacktrace").map(StringUtils::toBoolean).orElse(DEFAULTS.captureStacktrace))
				.build();
	}

}
