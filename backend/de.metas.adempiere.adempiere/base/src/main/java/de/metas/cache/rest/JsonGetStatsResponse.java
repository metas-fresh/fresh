package de.metas.cache.rest;

import com.google.common.collect.ImmutableList;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.stream.Collector;

@Value
public class JsonGetStatsResponse
{
	int count;
	@NonNull List<JsonCacheStats> stats;

	@Builder
	@Jacksonized
	private JsonGetStatsResponse(@NonNull final List<JsonCacheStats> stats)
	{
		this.count = stats.size();
		this.stats = ImmutableList.copyOf(stats);
	}

	public static Collector<JsonCacheStats, ?, JsonGetStatsResponse> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(JsonGetStatsResponse::new);
	}
}
