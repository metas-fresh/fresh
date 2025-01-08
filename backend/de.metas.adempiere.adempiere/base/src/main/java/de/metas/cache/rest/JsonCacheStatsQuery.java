package de.metas.cache.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.cache.CCacheStatsOrderBy;
import de.metas.cache.CCacheStatsPredicate;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@Builder
@Jacksonized
public class JsonCacheStatsQuery
{
	@Nullable String cacheName;
	@Nullable Integer minSize;
	@Nullable String labels;
	@Nullable String orderByString;

	@JsonIgnore
	public CCacheStatsPredicate toCCacheStatsPredicate()
	{
		return CCacheStatsPredicate.builder()
				.cacheNameContains(cacheName)
				.minSize(minSize)
				.labels(labels)
				.build();
	}

	@JsonIgnore
	public Optional<CCacheStatsOrderBy> toCCacheStatsOrderBy()
	{
		return CCacheStatsOrderBy.parse(orderByString);
	}
}
