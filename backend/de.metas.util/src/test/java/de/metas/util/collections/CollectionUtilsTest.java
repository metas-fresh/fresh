/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.util.collections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import de.metas.util.ImmutableMapEntry;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class CollectionUtilsTest
{

	@Nested
	class emptyOrSingleElement
	{
		@Test
		void empty()
		{
			assertThat(CollectionUtils.emptyOrSingleElement(ImmutableList.of())).isEmpty();
		}

		@Test
		void oneElement()
		{
			assertThat(CollectionUtils.emptyOrSingleElement(ImmutableList.of("1"))).contains("1");
		}

		@Test
		void twoIdenticalElements()
		{
			// NOTE: checking if elements are distinct is not the job of this method
			assertThatThrownBy(() -> CollectionUtils.emptyOrSingleElement(ImmutableList.of("1", "1")))
					.isInstanceOf(RuntimeException.class)
					.hasMessageStartingWith("The given collection needs to have ZERO or ONE item");
		}

		@Test
		void moreElements()
		{
			assertThatThrownBy(() -> CollectionUtils.emptyOrSingleElement(ImmutableList.of("1", "2")))
					.isInstanceOf(RuntimeException.class)
					.hasMessageStartingWith("The given collection needs to have ZERO or ONE item");
		}
	}

	@Nested
	class singleElementOrEmpty
	{
		@Test
		void empty()
		{
			ImmutableList<Integer> list = ImmutableList.of();
			final Optional<Integer> result = CollectionUtils.singleElementOrEmpty(list, item -> true);
			assertThat(result).isEmpty();
		}

		@Test
		void oneElement()
		{
			ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
			final Optional<Integer> result = CollectionUtils.singleElementOrEmpty(list, item -> item == 2);
			assertThat(result).contains(2);
		}

		@Test
		void twoIdenticalElements()
		{
			// NOTE: checking if elements are distinct is not the job of this method
			ImmutableList<Integer> list = ImmutableList.of(1, 1);
			final Optional<Integer> result = CollectionUtils.singleElementOrEmpty(list, item -> true);
			assertThat(result).isEmpty();
		}

		@Test
		void moreElements()
		{
			ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
			final Optional<Integer> result = CollectionUtils.singleElementOrEmpty(list, item -> true);
			assertThat(result).isEmpty();
		}
	}

	@Nested
	class singleElementOrEmptyIfNotFound
	{
		@Test
		void empty()
		{
			ImmutableList<Integer> list = ImmutableList.of();
			final Optional<Integer> result = CollectionUtils.singleElementOrEmptyIfNotFound(list, item -> true);
			assertThat(result).isEmpty();
		}

		@Test
		void oneElement()
		{
			ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
			final Optional<Integer> result = CollectionUtils.singleElementOrEmptyIfNotFound(list, item -> item == 2);
			assertThat(result).contains(2);
		}

		@Test
		void twoIdenticalElements()
		{
			// NOTE: checking if elements are distinct is not the job of this method
			ImmutableList<Integer> list = ImmutableList.of(1, 1);
			assertThatThrownBy(() -> CollectionUtils.singleElementOrEmptyIfNotFound(list, item -> true))
					.hasMessageStartingWith("Only one matching element was expected but we got more");
		}

		@Test
		void moreElements()
		{
			ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
			assertThatThrownBy(() -> CollectionUtils.singleElementOrEmptyIfNotFound(list, item -> true))
					.hasMessageStartingWith("Only one matching element was expected but we got more");
		}
	}

	@Nested
	class mergeElementToMap
	{
		@SafeVarargs
		private final ImmutableMap<String, ImmutableMapEntry<String, String>> mapOf(
				final ImmutableMapEntry<String, String>... entries
		)
		{
			return Maps.uniqueIndex(Arrays.asList(entries), ImmutableMapEntry::getKey);
		}

		@Test
		void addNewElement()
		{
			assertThat(
					CollectionUtils.mergeElementToMap(
							mapOf(
									ImmutableMapEntry.of("K1", "V1")
							),
							ImmutableMapEntry.of("K2", "V2"),
							ImmutableMapEntry::getKey)
			).isEqualTo(mapOf(
					ImmutableMapEntry.of("K1", "V1"),
					ImmutableMapEntry.of("K2", "V2")
			));
		}

		@Test
		void replaceExistingElement()
		{
			assertThat(
					CollectionUtils.mergeElementToMap(
							mapOf(
									ImmutableMapEntry.of("K1", "V1")
							),
							ImmutableMapEntry.of("K1", "V1.1"),
							ImmutableMapEntry::getKey)
			).isEqualTo(mapOf(
					ImmutableMapEntry.of("K1", "V1.1")
			));
		}

		@Test
		void addSameElement()
		{
			final ImmutableMap<String, ImmutableMapEntry<String, String>> map = mapOf(
					ImmutableMapEntry.of("K1", "V1")
			);
			assertThat(
					CollectionUtils.mergeElementToMap(
							map,
							ImmutableMapEntry.of("K1", "V1"),
							ImmutableMapEntry::getKey)
			).isSameAs(map);
		}

	}

	@Nested
	class filter
	{
		@Test
		void acceptAll()
		{
			ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5, 6);
			assertThat(CollectionUtils.filter(list, item -> true)).isSameAs(list);
		}

		@Test
		void acceptAll_on_emptyList()
		{
			ImmutableList<Integer> list = ImmutableList.of();
			assertThat(CollectionUtils.filter(list, item -> true)).isSameAs(list);
		}

		@Test
		void acceptNone()
		{
			ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5, 6);
			assertThat(CollectionUtils.filter(list, item -> false)).isEmpty();
		}

		@Test
		void acceptNone_on_emptyList()
		{
			ImmutableList<Integer> list = ImmutableList.of();
			assertThat(CollectionUtils.filter(list, item -> false)).isEmpty();
		}

		@Test
		void acceptEvenNumbers()
		{
			ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5, 6);
			assertThat(CollectionUtils.filter(list, item -> item % 2 == 0)).containsExactly(2, 4, 6);
		}

		@Test
		void acceptFirst()
		{
			ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5, 6);
			assertThat(CollectionUtils.filter(list, item -> item == 1)).containsExactly(1);
		}

		@Test
		void acceptLast()
		{
			ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5, 6);
			assertThat(CollectionUtils.filter(list, item -> item == 6)).containsExactly(6);
		}
	}

	@Nested
	class map
	{
		@Test
		void noChange()
		{
			final ImmutableList<String> list = ImmutableList.of("1", "2", "3");
			final ImmutableList<String> listChanged = CollectionUtils.map(list, item -> item);
			assertThat(listChanged).isSameAs(list);
		}

		@Test
		void oneElementChanged()
		{
			final ImmutableList<String> list = ImmutableList.of("1", "2", "3");
			final ImmutableList<String> listChanged = CollectionUtils.map(list, item -> item.equals("2") ? "99" : item);
			assertThat(listChanged).containsExactly("1", "99", "3");
		}

		@Test
		void oneElementRemoved()
		{
			final ImmutableList<String> list = ImmutableList.of("1", "2", "3");
			final ImmutableList<String> listChanged = CollectionUtils.map(list, item -> item.equals("2") ? null : item);
			assertThat(listChanged).containsExactly("1", "3");
		}
	}

	@Nested
	class mapKeys_SetMultimap
	{
		@Test
		void empty()
		{
			final ImmutableSetMultimap<Object, Object> multimap = ImmutableSetMultimap.of();
			assertThat(CollectionUtils.mapKeys(multimap, k -> k)).isSameAs(multimap);
		}

		@Test
		void noChange()
		{
			final ImmutableSetMultimap<Integer, String> multimap = ImmutableSetMultimap.of(1, "one", 2, "two");
			assertThat(CollectionUtils.mapKeys(multimap, k -> k)).isSameAs(multimap);
		}

		@Test
		void oneElementChanged()
		{
			final ImmutableSetMultimap<Integer, String> multimap = ImmutableSetMultimap.of(1, "one", 2, "two");
			assertThat(CollectionUtils.mapKeys(multimap, k -> k == 1 ? k * 10 : k))
					.isEqualTo(ImmutableSetMultimap.of(10, "one", 2, "two"));
		}

	}
}