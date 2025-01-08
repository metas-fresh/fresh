package de.metas.cache;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CCacheStatsOrderByTest
{
	@Nested
	class parse
	{
		@Test
		void blankString()
		{
			assertThat(CCacheStatsOrderBy.parse("   ")).isEmpty();
		}

		@Test
		void standardTest()
		{
			assertThat(CCacheStatsOrderBy.parse("  name,  -size , +hitRate ,   -missRate   "))
					.get()
					.isEqualTo(CCacheStatsOrderBy.builder()
							.ascending(CCacheStatsOrderBy.Field.name)
							.descending(CCacheStatsOrderBy.Field.size)
							.ascending(CCacheStatsOrderBy.Field.hitRate)
							.descending(CCacheStatsOrderBy.Field.missRate)
							.build());
		}

		@Test
		void unknownField()
		{
			assertThatThrownBy(() -> CCacheStatsOrderBy.parse("unknownField"))
					.hasMessageStartingWith("Invalid order by string");
		}

	}
}