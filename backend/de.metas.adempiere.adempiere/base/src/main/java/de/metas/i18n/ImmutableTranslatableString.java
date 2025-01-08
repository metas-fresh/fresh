package de.metas.i18n;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Immutable {@link ITranslatableString} implementation.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@EqualsAndHashCode
public final class ImmutableTranslatableString implements ITranslatableString
{
	private final ImmutableMap<String, String> trlMap;
	private final String defaultValue;

	@lombok.Builder
	private ImmutableTranslatableString(
			@Nullable @Singular final Map<String, String> trls,
			@Nullable final String defaultValue)
	{
		this.trlMap = trls == null ? ImmutableMap.of() : ImmutableMap.copyOf(trls);
		this.defaultValue = defaultValue == null ? "" : defaultValue;
	}

<<<<<<< HEAD
=======
	public static ImmutableTranslatableString ofMap(
			@Nullable final Map<String, String> trls,
			@Nullable final String defaultValue)
	{
		return new ImmutableTranslatableString(trls, defaultValue);
	}

	public static ImmutableTranslatableString ofDefaultValue(@Nullable final String defaultValue)
	{
		return ofMap(null, defaultValue);
	}

	private static ImmutableMap<String, String> normalizeTrlsMap(@Nullable final Map<String, String> trls)
	{
		if (trls == null || trls.isEmpty())
		{
			return ImmutableMap.of();
		}
		else if (trls.containsValue(null))
		{
			ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
			trls.forEach((adLanguage, trl) -> builder.put(adLanguage, trl != null ? trl : ""));
			return builder.build();
		}
		else
		{
			return ImmutableMap.copyOf(trls);
		}
	}

>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
	@Override
	@Deprecated
	public String toString()
	{
		return defaultValue;
	}

	@Override
	public String translate(final String adLanguage)
	{
		return trlMap.getOrDefault(adLanguage, defaultValue);
	}

	@Override
	public String getDefaultValue()
	{
		return defaultValue;
	}

	@Override
	public Set<String> getAD_Languages()
	{
		return trlMap.keySet();
	}

	@JsonIgnore // needed for snapshot testing
	public boolean isEmpty()
	{
		return defaultValue.isEmpty()
				&& trlMap.values().stream().allMatch(String::isEmpty);
	}
}
