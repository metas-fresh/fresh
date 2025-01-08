package de.metas.cache.rest;

import de.metas.cache.CCache;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCache
{
	@NonNull JsonCacheStats stats;
	@Nullable Map<String, String> sample;

	public static JsonCache of(@NonNull final CCache<?, ?> cache, final int sampleLimit)
	{
		//noinspection unchecked
		return JsonCache.builder()
				.stats(JsonCacheStats.of(cache.stats()))
				.sample(extractSample((CCache<Object, Object>)cache, sampleLimit))
				.build();
	}

	private static Map<String, String> extractSample(final CCache<Object, Object> cache, final int sampleLimit)
	{
		final HashMap<String, String> sample = sampleLimit > 0 ? new HashMap<>(sampleLimit) : new HashMap<>();
		for (final Object key : cache.keySet())
		{
			if (sampleLimit > 0 && sample.size() > sampleLimit)
			{
				break;
			}

			final Object value = cache.get(key);

			sample.put(key != null ? key.toString() : null, value != null ? value.toString() : null);
		}

		return sample;
	}
}
