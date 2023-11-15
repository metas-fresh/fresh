package de.metas.cache;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public final class CCacheStatsOrderBy implements Comparator<CCacheStats>
{
	@NonNull private final ImmutableList<Part> parts;
	private transient Comparator<CCacheStats> _actualComparator = null; // lazy

	@Builder
	private CCacheStatsOrderBy(@NonNull @Singular final ImmutableList<Part> parts)
	{
		Check.assumeNotEmpty(parts, "at least one order by criteria shall be specified");
		this.parts = parts;
	}

	@SuppressWarnings("unused")
	public static class CCacheStatsOrderByBuilder
	{
		public CCacheStatsOrderByBuilder ascending(@NonNull Field field) {return part(Part.of(field, true));}

		public CCacheStatsOrderByBuilder descending(@NonNull Field field) {return part(Part.of(field, false));}
	}

	public static Optional<CCacheStatsOrderBy> parse(@Nullable final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		if (stringNorm == null)
		{
			return Optional.empty();
		}

		try
		{
			final ImmutableList<Part> parts = Splitter.on(",")
					.trimResults()
					.omitEmptyStrings()
					.splitToStream(stringNorm)
					.map(Part::parse)
					.collect(ImmutableList.toImmutableList());

			return Optional.of(new CCacheStatsOrderBy(parts));
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid order by string `" + string + "`", ex);
		}
	}

	@Override
	public int compare(final CCacheStats o1, final CCacheStats o2)
	{
		return getActualComparator().compare(o1, o2);
	}

	private Comparator<CCacheStats> getActualComparator()
	{
		Comparator<CCacheStats> comparator = this._actualComparator;
		if (comparator == null)
		{
			comparator = this._actualComparator = createActualComparator();
		}
		return comparator;
	}

	private Comparator<CCacheStats> createActualComparator()
	{
		Comparator<CCacheStats> result = parts.get(0).toComparator();
		for (int i = 1; i < parts.size(); i++)
		{
			final Comparator<CCacheStats> partComparator = parts.get(i).toComparator();
			result = result.thenComparing(partComparator);
		}

		return result;
	}

	//
	//
	//

	public enum Field
	{
		name,
		size,
		hitRate,
		missRate,
	}

	@NonNull
	@Value(staticConstructor = "of")
	public static class Part
	{
		@NonNull Field field;
		boolean ascending;

		Comparator<CCacheStats> toComparator()
		{
			Comparator<CCacheStats> comparator = switch (field)
			{
				case name -> Comparator.comparing(CCacheStats::getName);
				case size -> Comparator.comparing(CCacheStats::getSize);
				case hitRate -> Comparator.comparing(CCacheStats::getHitRate);
				case missRate -> Comparator.comparing(CCacheStats::getMissRate);
			};

			if (!ascending)
			{
				comparator = comparator.reversed();
			}

			return comparator;
		}

		static Part parse(@NonNull String string)
		{
			String stringNorm = StringUtils.trimBlankToNull(string);
			if (stringNorm == null)
			{
				throw new AdempiereException("Invalid part: `" + string + "`");
			}

			final String fieldName;
			final boolean ascending;
			if (stringNorm.charAt(0) == '+')
			{
				fieldName = stringNorm.substring(1);
				ascending = true;
			}
			else if (stringNorm.charAt(0) == '-')
			{
				fieldName = stringNorm.substring(1);
				ascending = false;
			}
			else
			{
				fieldName = stringNorm;
				ascending = true;
			}

			final Field field = Field.valueOf(fieldName);

			return of(field, ascending);
		}
	}
}
