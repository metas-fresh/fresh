package de.metas.util.collections;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.SetMultimap;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2018 metas GmbH
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

@UtilityClass
public final class CollectionUtils
{
	/**
	 * Convert given <code>list</code> to string using given <code>separator</code>.
	 *
	 * @param collection list to be converted to string
	 * @param separator  between elements separator
	 * @return string representation
	 * @see #toString(Collection, String, Converter)
	 */
	public static <ET, CT extends Collection<ET>> String toString(final CT collection, final String separator)
	{
		return toString(collection, separator, null);
	}

	/**
	 * Convert given <code>list</code> to string using given <code>separator</code>.
	 *
	 * @param collection             collection to be converted to string
	 * @param separator              between elements separator
	 * @param elementStringConverter converter to be used when converting one list element to string
	 * @return string representation
	 */
	public static <ET, CT extends Collection<ET>> String toString(
			@Nullable final CT collection,
			@Nullable final String separator,
			@Nullable final Converter<String, ET> elementStringConverter)
	{
		if (collection == null)
		{
			return "";
		}
		if (collection.isEmpty())
		{
			return "";
		}

		final String separatorToUse = separator == null ? "" : separator;

		final StringBuilder sb = new StringBuilder();
		for (final ET element : collection)
		{
			if (sb.length() > 0)
			{
				sb.append(separatorToUse);
			}

			final String elementStr;
			if (elementStringConverter == null)
			{
				elementStr = String.valueOf(element);
			}
			else
			{
				elementStr = elementStringConverter.convert(element);
			}

			sb.append(elementStr);
		}

		return sb.toString();
	}

	public static Set<Integer> asIntSet(final int... arr)
	{
		if (arr == null || arr.length == 0)
		{
			return Collections.emptySet();
		}

		final Set<Integer> set = new HashSet<>(arr.length);
		for (final int i : arr)
		{
			set.add(i);
		}

		return set;
	}

	@SafeVarargs
	public static <T> Set<T> asSet(final T... arr)
	{
		if (arr == null || arr.length == 0)
		{
			return Collections.emptySet();
		}

		final HashSet<T> result = new HashSet<>(arr.length);
		Collections.addAll(result, arr);

		return result;
	}

	/**
	 * Assumes that only one element will be matched by filter and returns it.
	 * <p>
	 * If there were more elements matching or no element was matching an exception will be thrown.
	 *
	 * @param filter filter used to match the element
	 * @return matching element; returns null ONLY if the element is null
	 * @see de.metas.util.reducers.Reducers#singleValue()
	 */
	public static <T> T singleElement(@NonNull final Collection<T> collection, @NonNull final java.util.function.Predicate<T> filter)
	{
		final List<T> result = new ArrayList<>();

		for (final T e : collection)
		{
			if (filter.test(e))
			{
				result.add(e);
			}
		}

		final int size = result.size();
		Check.assume(size == 1, "One and only one matching element was expected but we got {}: {}", size, result);
		return result.get(0);
	}

	public static <T> Optional<T> singleElementOrEmpty(@NonNull final Collection<T> collection)
	{
		return singleElementOrEmpty(collection, e -> true);
	}

	/**
	 * @param filter filter used to match the element
	 * @return matching element wrapped as Optional or empty Optional if there were more elements matching or no element was matching
	 */
	public static <T> Optional<T> singleElementOrEmpty(@NonNull final Collection<T> collection, @NonNull final java.util.function.Predicate<T> filter)
	{
		T singleElement = null;
		boolean singleElementSet = false;

		for (final T e : collection)
		{
			if (filter.test(e))
			{
				if (singleElementSet)
				{
					// We already have an element => return empty
					return Optional.empty();
				}
				else
				{
					singleElementSet = true;
					singleElement = e;
				}
			}
		}

		return singleElementSet
				? Optional.of(singleElement)
				: Optional.empty();
	}

	public static <T> Optional<T> singleElementOrEmptyIfNotFound(@NonNull final Collection<T> collection, @NonNull final java.util.function.Predicate<T> filter)
	{
		final List<T> result = new ArrayList<>();

		for (final T e : collection)
		{
			if (filter.test(e))
			{
				result.add(e);
			}
		}

		if (result.isEmpty())
		{
			return Optional.empty();
		}
		else if (result.size() == 1)
		{
			return Optional.of(result.get(0));
		}
		else
		{
			throw Check.mkEx("Only one matching element was expected but we got more: " + result);
		}
	}

	/**
	 * Assumes that given collection has one element only and returns it.
	 * <p>
	 * If the collection has more elements or no element then an exception will be thrown.
	 *
	 * @return element; returns null ONLY if the element is null
	 * @see de.metas.util.reducers.Reducers#singleValue()
	 */
	public static <T> T singleElement(@NonNull final Collection<T> collection)
	{
		Check.errorUnless(collection.size() == 1, "The given collection needs to have exactly one 1 item, but has {} items; collection={}", collection.size(), collection);
		return collection.iterator().next();
	}

	/**
	 * Assumes that given collection has one element only and returns it.
	 * <p>
	 * If the collection has more elements or no element then <code>null</code> will be returned.
	 *
	 * @see de.metas.util.reducers.Reducers#singleValue()
	 */
	@Nullable
	public static <T> T singleElementOrNull(final Collection<T> collection)
	{
		return singleElementOrDefault(collection, null);
	}

	/**
	 * Assumes that given collection has one element only and returns it.
	 * <p>
	 * If the collection has more elements or no element then <code>defaultValue</code> will be returned.
	 *
	 * @param defaultValue value to be returned in case there are more than one element or no element
	 * @see de.metas.util.reducers.Reducers#singleValue()
	 */
	@Contract("_, !null -> !null")
	@Nullable
	public static <T> T singleElementOrDefault(@Nullable final Collection<T> collection, @Nullable final T defaultValue)
	{
		if (collection == null)
		{
			return defaultValue;
		}
		if (collection.size() != 1)
		{
			return defaultValue;
		}

		final T element = collection.iterator().next();
		if (element == null)
		{
			return defaultValue;
		}
		return element;
	}

	/**
	 * @see de.metas.util.reducers.Reducers#singleValue()
	 */
	public static <T, R> R extractSingleElement(
			@NonNull final Collection<T> collection,
			@NonNull final Function<T, R> extractFunction)
	{
		final ImmutableList<R> extractedElements = extractDistinctElements(collection, extractFunction);
		return singleElement(extractedElements);
	}

	/**
	 * @see de.metas.util.reducers.Reducers#singleValue()
	 */
	@Contract("_, _, !null -> !null")
	@Nullable
	public static <T, R> R extractSingleElementOrDefault(
			@NonNull final Collection<T> collection,
			@NonNull final Function<T, R> extractFunction,
			@Nullable final R defaultValue)
	{
		if (collection.isEmpty())
		{
			return defaultValue;
		}

		final ImmutableList<R> extractedElements = extractDistinctElements(collection, extractFunction);
		return singleElementOrDefault(extractedElements, defaultValue);
	}

	public static <T, R> boolean hasDifferentValues(
			@NonNull final Collection<T> collection,
			@NonNull final Function<T, R> extractFunction)
	{
		if (collection.isEmpty())
		{
			return false;
		}

		final ImmutableList<R> extractedElements = extractDistinctElements(collection, extractFunction);
		return extractedElements.size() > 1;
	}

	public static <R, T> ImmutableList<R> extractDistinctElements(
			@NonNull final Collection<T> collection,
			@NonNull final Function<T, R> extractFunction)
	{
		if (collection.isEmpty())
		{
			return ImmutableList.of();
		}

		return collection
				.stream()
				.map(extractFunction)
				.filter(Objects::nonNull)
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

	public static <R, T> ImmutableSet<R> extractDistinctElementsIntoSet(
			@NonNull final Collection<T> collection,
			@NonNull final Function<T, R> extractFunction)
	{
		return collection
				.stream()
				.map(extractFunction)
				.distinct()
				.collect(ImmutableSet.toImmutableSet());
	}

	public static <R, T> ImmutableList<R> map(
			@NonNull final ImmutableList<T> collection,
			@NonNull final Function<T, R> mappingFunction)
	{
		if (collection.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<R> result = ImmutableList.builder();
		boolean hasChanges = false;
		for (final T item : collection)
		{
			final R changedItem = mappingFunction.apply(item);
			if (changedItem != null)
			{
				result.add(changedItem);
				if (!hasChanges && !Objects.equals(item, changedItem))
				{
					hasChanges = true;
				}
			}
			else
			{
				hasChanges = true;
			}
		}

		//noinspection unchecked
		return hasChanges ? result.build() : (ImmutableList<R>)collection;
	}

	public static <T> ImmutableList<T> filter(
			@NonNull final ImmutableList<T> list,
			@NonNull final Predicate<T> predicate)
	{
		if (list.isEmpty())
		{
			return list;
		}

		ImmutableList.Builder<T> result = null;
		for (int i = 0, size = list.size(); i < size; i++)
		{
			final T item = list.get(i);
			if (!predicate.test(item))
			{
				if (result == null)
				{
					result = ImmutableList.builder();
					result.addAll(list.subList(0, i));
				}
			}
			else
			{
				if (result != null)
				{
					result.add(item);
				}
			}
		}

		if (result == null)
		{
			return list;
		}

		return result.build();
	}

	public static <T> ImmutableSet<T> removeElement(
			@NonNull final ImmutableSet<T> set,
			@Nullable final T elementToRemove)
	{
		if (elementToRemove == null || !set.contains(elementToRemove))
		{
			return set;
		}

		return set.stream().filter(element -> !element.equals(elementToRemove)).collect(ImmutableSet.toImmutableSet());
	}

	public static <K, V> ImmutableMap<K, V> mapValue(
			@NonNull final ImmutableMap<K, V> map,
			@NonNull final K key,
			@NonNull final UnaryOperator<V> mappingFunction)
	{
		return mapValues(
				map,
				(currentKey, currentValue) -> currentKey.equals(key) ? mappingFunction.apply(currentValue) : currentValue);
	}

	public static <K, V, W> ImmutableMap<K, W> mapValues(
			@NonNull final ImmutableMap<K, V> map,
			@NonNull final BiFunction<K, V, W> mappingFunction)
	{
		if (map.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<K, W> result = ImmutableMap.builder();
		boolean hasChanges = false;
		for (final K key : map.keySet())
		{
			final V item = map.get(key);
			final W changedItem = mappingFunction.apply(key, item);
			result.put(key, changedItem);

			if (!hasChanges && !Objects.equals(item, changedItem))
			{
				hasChanges = true;
			}
		}

		//noinspection unchecked
		return hasChanges ? result.build() : (ImmutableMap<K, W>)map;
	}

	public static <K, V, W> ImmutableMap<K, W> mapValues(
			@NonNull final ImmutableMap<K, V> map,
			@NonNull final Function<V, W> mappingFunction)
	{
		return mapValues(map, (k, v) -> mappingFunction.apply(v));
	}

	public static <K, V, K2> SetMultimap<K2, V> mapKeys(@NonNull final SetMultimap<K, V> multimap, @NonNull final Function<K, K2> keyMapper)
	{
		if (multimap.isEmpty())
		{
			//noinspection unchecked
			return (SetMultimap<K2, V>)multimap;
		}

		ImmutableSetMultimap.Builder<K2, V> newResult = ImmutableSetMultimap.builder();
		boolean hasChanges = false;
		for (final K key : multimap.keySet())
		{
			final K2 newKey = keyMapper.apply(key);
			final Set<V> values = multimap.get(key);
			newResult.putAll(newKey, values);
			if (!Objects.equals(key, newKey))
			{
				hasChanges = true;
			}
		}

		if (hasChanges)
		{
			return newResult.build();
		}
		else
		{
			//noinspection unchecked
			return (SetMultimap<K2, V>)multimap;
		}
	}

	/**
	 * Removes first element from {@link Set} and returns it.
	 * <p>
	 * NOTE: this method is NOT checking if the set is null or empty.
	 *
	 * @return the removed first element
	 */
	public static <T> T removeFirst(@NonNull final Set<T> set)
	{
		final Iterator<T> it = set.iterator();
		final T element = it.next();
		it.remove();
		return element;
	}

	public static <K, V> Collection<V> getAllOrLoad(
			@NonNull final Map<K, V> map,
			@NonNull final Collection<K> keys,
			@NonNull final Function<Set<K>, Map<K, V>> valuesLoader)
	{
		if (keys.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Fetch from cache what's available
		final List<V> values = new ArrayList<>(keys.size());
		final Set<K> keysToLoad = new HashSet<>();
		for (final K key : ImmutableSet.copyOf(keys))
		{
			final V value = map.get(key);
			if (value == null)
			{
				keysToLoad.add(key);
			}
			else
			{
				values.add(value);
			}
		}

		//
		// Load the missing keys if any
		if (!keysToLoad.isEmpty())
		{
			final Map<K, V> valuesLoaded = valuesLoader.apply(keysToLoad);
			map.putAll(valuesLoaded); // add loaded values to cache
			values.addAll(valuesLoaded.values()); // add loaded values to the list we will return
		}

		//
		return values;
	}

	public static <K, V> Map<K, V> getAllOrLoadReturningMap(
			@NonNull final Map<K, V> map,
			@NonNull final Collection<K> keys,
			@NonNull final Function<Set<K>, Map<K, V>> valuesLoader)
	{
		if (keys.isEmpty())
		{
			return ImmutableMap.of();
		}

		//
		// Fetch from cache what's available
		final HashMap<K, V> result = new HashMap<>(keys.size());
		final Set<K> keysToLoad = new HashSet<>();
		for (final K key : ImmutableSet.copyOf(keys))
		{
			final V value = map.get(key);
			if (value == null)
			{
				keysToLoad.add(key);
			}
			else
			{
				result.put(key, value);
			}
		}

		//
		// Load the missing keys if any
		if (!keysToLoad.isEmpty())
		{
			final Map<K, V> valuesLoaded = valuesLoader.apply(keysToLoad);
			map.putAll(valuesLoaded); // add loaded values to cache
			result.putAll(valuesLoaded); // add loaded values to the map we will return
		}

		//
		return result;
	}

	public static <K, V> LinkedHashMap<K, V> uniqueLinkedHashMap(
			@NonNull final Stream<V> stream,
			@NonNull final Function<? super V, ? extends K> keyFunction)
	{
		// thx to https://reversecoding.net/java-8-list-to-map/
		return stream
				.collect(Collectors.toMap(
						keyFunction,
						Function.identity(),
						(u, v) -> {
							throw new IllegalStateException(String.format("Duplicate key %s", u));
						},
						LinkedHashMap::new));
	}

	public static <T> ImmutableList<T> ofCommaSeparatedList(
			@Nullable final String commaSeparatedStr,
			@NonNull final Function<String, T> mapper)
	{
		if (commaSeparatedStr == null || Check.isBlank(commaSeparatedStr))
		{
			return ImmutableList.of();
		}

		return Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(commaSeparatedStr)
				.stream()
				.map(mapper)
				.collect(ImmutableList.toImmutableList());
	}

	public static <T> ImmutableSet<T> ofCommaSeparatedSet(
			@Nullable final String commaSeparatedStr,
			@NonNull final Function<String, T> mapper)
	{
		if (commaSeparatedStr == null || Check.isBlank(commaSeparatedStr))
		{
			return ImmutableSet.of();
		}

		return Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(commaSeparatedStr)
				.stream()
				.map(mapper)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static <T> Optional<T> emptyOrSingleElement(@NonNull final Collection<T> collection)
	{
		final int size = collection.size();
		if (size == 0)
		{
			return Optional.empty();
		}
		else if (size == 1)
		{
			return Optional.of(collection.iterator().next());
		}
		else
		{
			throw Check.mkEx("The given collection needs to have ZERO or ONE item, but has " + size + " items; collection=" + collection);
		}
	}

	@NonNull
	public static <T> ArrayList<T> mergeLists(@NonNull final ArrayList<T> list1, @NonNull final ArrayList<T> list2)
	{
		list1.addAll(list2);
		return list1;
	}

	public static <T> ImmutableSet<T> difference(@NonNull final ImmutableSet<T> set, @Nullable final Collection<T> excludes)
	{
		if (set.isEmpty())
		{
			return ImmutableSet.of();
		}
		else if (excludes == null || excludes.isEmpty())
		{
			return set;
		}
		else
		{
			final ImmutableSet<T> result = set.stream()
					.filter(e -> !excludes.contains(e))
					.collect(ImmutableSet.toImmutableSet());

			if (result.size() == set.size())
			{
				return set;
			}
			else
			{
				return result;
			}
		}
	}

	public static <K, V> ImmutableMap<K, V> mergeElementToMap(
			@NonNull final ImmutableMap<K, V> map,
			@NonNull final V element,
			@NonNull final Function<V, K> keyExtractor)
	{
		final K key = keyExtractor.apply(element);
		final V oldElement = map.get(key);
		if (Objects.equals(element, oldElement))
		{
			return map;
		}

		final LinkedHashMap<K, V> newMap = new LinkedHashMap<>(map);
		newMap.put(key, element);
		return ImmutableMap.copyOf(newMap);
	}

	public static <K, V> ImmutableMap<K, V> mergeMaps(
			@NonNull final ImmutableMap<K, V> map1,
			@NonNull final ImmutableMap<K, V> map2)
	{
		if (map2.isEmpty())
		{
			return map1;
		}
		else if (map1.isEmpty())
		{
			return map2;
		}
		else
		{
			final LinkedHashMap<K, V> result = new LinkedHashMap<>(map1);
			result.putAll(map2);
			return ImmutableMap.copyOf(result);
		}
	}

	@NonNull
	public <K, V> Map<K, List<V>> groupMultiValueByKey(
			@NonNull final Collection<V> values,
			@NonNull final Function<V, K> mappingFunction)
	{

		final HashMap<K, ArrayList<V>> key2Values = new HashMap<>();

		values.forEach(value -> {
			final K currentKey = mappingFunction.apply(value);

			final ArrayList<V> currentValues = new ArrayList<>();
			currentValues.add(value);

			key2Values.merge(currentKey, currentValues, CollectionUtils::mergeLists);
		});

		return ImmutableMap.copyOf(key2Values);
	}

	public static boolean hasDuplicatesForValue(@NonNull final Collection<String> collection, @NonNull final String value)
	{
		return collection.stream()
				.filter(elem -> value.equals(elem))
				.count() > 1;
	}

	@Nullable
	public static <T> T first(@NonNull final Collection<T> collection)
	{
		return !collection.isEmpty() ? collection.iterator().next() : null;
	}

	public static <T> Optional<T> firstOptional(@NonNull final Collection<T> collection)
	{
		return Optional.ofNullable(first(collection));
	}


	public static <K, V> ImmutableMap<K, ImmutableList<V>> toImmutableMap(final ListMultimap<K, V> multimap)
	{
		if (multimap.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<K, ImmutableList<V>> result = ImmutableMap.builder();
		for (K key : multimap.keySet())
		{
			result.put(key, ImmutableList.copyOf(multimap.get(key)));
		}
		return result.build();
	}


	@Nullable
	public static <T> ImmutableSet<T> toImmutableSetOrNullIfEmpty(@Nullable final Collection<T> collection)
	{
		return collection != null && !collection.isEmpty() ? ImmutableSet.copyOf(collection) : null;
	}

	@NonNull
	public static <T> ImmutableSet<T> toImmutableSetOrEmpty(@Nullable final Collection<T> collection)
	{
		return collection != null && !collection.isEmpty() ? ImmutableSet.copyOf(collection) : ImmutableSet.of();
	}

}
