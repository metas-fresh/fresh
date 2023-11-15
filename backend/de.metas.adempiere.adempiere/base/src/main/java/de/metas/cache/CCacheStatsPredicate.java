package de.metas.cache;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.function.Predicate;

@ToString
public class CCacheStatsPredicate implements Predicate<CCacheStats>
{
	@Nullable private final String cacheNameContainsLC;
	private final int minSize;
	@NonNull private final ImmutableSet<CacheLabel> labels;

	@Builder
	private CCacheStatsPredicate(
			@Nullable final String cacheNameContains,
			@Nullable final Integer minSize,
			@Nullable final String labels)
	{
		this.cacheNameContainsLC = StringUtils.trimBlankToOptional(cacheNameContains)
				.map(String::toLowerCase)
				.orElse(null);
		this.minSize = minSize != null ? Math.max(minSize, 0) : 0;
		this.labels = parseLabels(labels);
	}

	private static ImmutableSet<CacheLabel> parseLabels(final String labels)
	{
		final String labelsNorm = StringUtils.trimBlankToNull(labels);
		if (labelsNorm == null)
		{
			return ImmutableSet.of();
		}

		return Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToStream(labelsNorm)
				.map(CacheLabel::ofTableName)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public boolean test(@NonNull final CCacheStats stats)
	{
		if (cacheNameContainsLC != null && !stats.getName().toLowerCase().contains(cacheNameContainsLC))
		{
			return false;
		}

		if (minSize > 0 && stats.getSize() < minSize)
		{
			return false;
		}

		if (!labels.isEmpty() && !stats.getLabels().containsAll(labels))
		{
			return false;
		}

		return true;
	}
}
