package de.metas.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class GuavaCollectorsTest
{
	@Test
	public void test_toImmutableList()
	{
		final List<Integer> source = Arrays.asList(1, 2, 3, 1, 2, 3);

		final List<Integer> resultExpected = source;
		final List<Integer> result = source.stream().collect(GuavaCollectors.toImmutableList());

		Assert.assertEquals(resultExpected, result);
	}

	@Test
	public void test_toImmutableListExcludingDuplicates()
	{
		final List<Integer> source = Arrays.asList(1, 2, 3, 1, 2, 3, 4);

		final List<Integer> resultExpected = Arrays.asList(1, 2, 3, 4);
		final List<Integer> result = source.stream().collect(GuavaCollectors.toImmutableListExcludingDuplicates());

		Assert.assertEquals(resultExpected, result);
	}

	@Test
	public void test_toImmutableListExcludingDuplicates_With_KeyFunction_DuplicatesConsumer()
	{
		final List<IPair<Integer, String>> source = Arrays.<IPair<Integer, String>> asList(
				ImmutablePair.of(1, "one"), ImmutablePair.of(2, "two"), ImmutablePair.of(3, "three") //
				, ImmutablePair.of(1, "one-again"), ImmutablePair.of(2, "two-again"), ImmutablePair.of(3, "three-again") //
				, ImmutablePair.of(4, "four") //
		);

		final List<IPair<Integer, String>> resultExpected = Arrays.<IPair<Integer, String>> asList(
				ImmutablePair.of(1, "one"), ImmutablePair.of(2, "two"), ImmutablePair.of(3, "three") //
				, ImmutablePair.of(4, "four") //
		);

		final List<IPair<Integer, String>> duplicatesExpected = Arrays.<IPair<Integer, String>> asList(
				ImmutablePair.of(1, "one-again"), ImmutablePair.of(2, "two-again"), ImmutablePair.of(3, "three-again") //
		);

		final List<IPair<Integer, String>> duplicatesActual = new ArrayList<>();
		final Function<IPair<Integer, String>, Integer> keyFunction = item -> item.getLeft();
		final BiConsumer<Integer, IPair<Integer, String>> duplicatesConsumer = (key, item) -> {
			duplicatesActual.add(item);
		};

		ImmutableList<IPair<Integer, String>> resultActual = source
				.stream()
				.collect(GuavaCollectors.toImmutableListExcludingDuplicates(keyFunction, duplicatesConsumer));

		Assert.assertEquals("Result", resultExpected, resultActual);
		Assert.assertEquals("Duplicates", duplicatesExpected, duplicatesActual);
	}

	@Test
	public void test_toImmutableSet()
	{
		final List<Integer> source = Arrays.asList(1, 2, 3, 1, 2, 3, 4);

		final Set<Integer> resultExpected = ImmutableSet.of(1, 2, 3, 4);
		final Set<Integer> result = source.stream().collect(GuavaCollectors.toImmutableSet());

		Assert.assertEquals(resultExpected, result);
	}

	@Test
	public void test_toImmutableSetHandlingDuplicates()
	{
		final List<Integer> source = Arrays.asList(1, 2, 3, 1);

		try
		{
			final Set<Integer> result = source.stream().collect(GuavaCollectors.toImmutableSetHandlingDuplicates(DuplicateElementException.throwExceptionConsumer()));
			Assert.fail("Exception was expected but we got: " + result);
		}
		catch (final DuplicateElementException ex)
		{
			final Integer expected = 1;
			final Integer actual = ex.getElement();
			Assert.assertEquals(expected, actual);
		}
	}

	@Test
	public void test_toImmutableSetHandlingDuplicates_NoDuplicates()
	{
		final List<Integer> source = Arrays.asList(1, 2, 3, 4);

		final Set<Integer> resultExpected = ImmutableSet.of(1, 2, 3, 4);
		final Set<Integer> result = source.stream().collect(GuavaCollectors.toImmutableSetHandlingDuplicates(DuplicateElementException.throwExceptionConsumer()));

		Assert.assertEquals(resultExpected, result);
	}

	@Test
	public void test_toImmutableMapByKeyKeepFirstDuplicate()
	{
		final ImmutableMap<String, String> result = Stream.of("1_one", "2_two1", "2_two2", "3_three")
				.collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(value -> value.substring(0, 1)));

		final ImmutableMap<String, String> resultExpected = ImmutableMap.of("1", "1_one", "2", "2_two1", "3", "3_three");

		Assert.assertEquals(resultExpected, result);
	}
	
	/** i.e. make sure the last duplicate is kept */
	@Test
	public void test_toImmutableMapByKey()
	{
		final ImmutableMap<String, String> result = Stream.of("1_one", "2_two1", "2_two2", "3_three")
				.collect(GuavaCollectors.toImmutableMapByKey(value -> value.substring(0, 1)));

		final ImmutableMap<String, String> resultExpected = ImmutableMap.of("1", "1_one", "2", "2_two2", "3", "3_three");

		Assert.assertEquals(resultExpected, result);
	}


	@SuppressWarnings("serial")
	private static final class DuplicateElementException extends RuntimeException
	{
		public static final <T> Consumer<T> throwExceptionConsumer()
		{
			final Consumer<T> duplicateConsumer = (element) -> {
				throw new DuplicateElementException(element);
			};
			return duplicateConsumer;
		}

		private final Object element;

		public DuplicateElementException(final Object element)
		{
			super("Duplicate element test exception: " + element);
			this.element = element;
		}

		public <T> T getElement()
		{
			@SuppressWarnings("unchecked")
			final T elementCasted = (T)element;
			return elementCasted;
		}
	}

}
