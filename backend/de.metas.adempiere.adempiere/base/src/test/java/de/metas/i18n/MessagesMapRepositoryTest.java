package de.metas.i18n;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessagesMapRepositoryTest
{
	@Nested
	class normalizeToJavaMessageFormat
	{
		@Test
		void empty()
		{
			assertThat(MessagesMapRepository.normalizeToJavaMessageFormat("")).isEmpty();
		}

		@Test
		void noPlaceholder()
		{
			assertThat(MessagesMapRepository.normalizeToJavaMessageFormat("some text without placeholders"))
					.isEqualTo("some text without placeholders");
		}

		@Test
		void placeholderAtStringStart()
		{
			assertThat(MessagesMapRepository.normalizeToJavaMessageFormat("{} bla")).isEqualTo("{0} bla");
		}

		@Test
		void placeholderAtStringEnd()
		{
			assertThat(MessagesMapRepository.normalizeToJavaMessageFormat("bla {}")).isEqualTo("bla {0}");
		}

		@Test
		void multiplePlaceholders()
		{
			assertThat(MessagesMapRepository.normalizeToJavaMessageFormat("{} then {} and then {}"))
					.isEqualTo("{0} then {1} and then {2}");
		}

		@Test
		void multiplePlaceholdersMixedWithNormalizedPlaceholders()
		{
			assertThat(MessagesMapRepository.normalizeToJavaMessageFormat("{} vs {2} then {} vs {1} and then {} vs {0}"))
					.isEqualTo("{0} vs {2} then {1} vs {1} and then {2} vs {0}");
		}

	}
}