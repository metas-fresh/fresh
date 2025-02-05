package de.metas.i18n;

import com.google.common.base.MoreObjects;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Function;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@EqualsAndHashCode
public final class ExplainedOptional<T>
{
	public static <T> ExplainedOptional<T> emptyBecause(@NonNull final String explanation)
	{
		return emptyBecause(TranslatableStrings.anyLanguage(explanation));
	}

	public static <T> ExplainedOptional<T> emptyBecause(@NonNull final ITranslatableString explanation)
	{
		return new ExplainedOptional<>(null, explanation);
	}

	public static <T> ExplainedOptional<T> of(@NonNull final T value)
	{
		return new ExplainedOptional<>(value, null);
	}

	private final T value;
	private final ITranslatableString explanation;

	private ExplainedOptional(@Nullable final T value, @Nullable final ITranslatableString explanation)
	{
		this.value = value;
		this.explanation = TranslatableStrings.nullToEmpty(explanation);
	}

	@Override
	public String toString()
	{
		final String explanationStr = !TranslatableStrings.isBlank(explanation)
				? explanation.getDefaultValue()
				: null;

		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.addValue(value)
				.add("explanation", explanationStr)
				.toString();
	}

	public ITranslatableString getExplanation()
	{
		return explanation != null ? explanation : TranslatableStrings.empty();
	}

	@Nullable
	public T orElse(@Nullable final T other)
	{
		return value != null ? value : other;
	}

	public T orElseThrow()
	{
		return orElseThrow(AdempiereException::new);
	}

	public T orElseThrow(@NonNull final Function<ITranslatableString, RuntimeException> exceptionFactory)
	{
		if (value != null)
		{
			return value;
		}
		else
		{
			throw exceptionFactory.apply(explanation);
		}
	}

	public T get()
	{
		return orElseThrow();
	}

	public boolean isPresent()
	{
		return value != null;
	}

	public <U> ExplainedOptional<U> map(@NonNull final Function<? super T, ? extends U> mapper)
	{
		if (!isPresent())
		{
			return emptyBecause(explanation);
		}
		else
		{
			final U newValue = mapper.apply(value);
			if (newValue == null)
			{
				return emptyBecause(explanation);
			}
			else
			{
				return of(newValue);
			}
		}
	}

	public ExplainedOptional<T> ifPresent(@NonNull final Consumer<T> consumer)
	{
		if (isPresent())
		{
			consumer.accept(value);
		}
		return this;
	}

	@SuppressWarnings("UnusedReturnValue")
	public ExplainedOptional<T> ifAbsent(@NonNull final Consumer<ITranslatableString> consumer)
	{
		if (!isPresent())
		{
			consumer.accept(explanation);
		}
		return this;
	}

	public <R> R resolve(
			@NonNull final Function<T, R> mapPresent,
			@NonNull final Function<ITranslatableString, R> mapAbsent)
	{
		if (isPresent())
		{
			return mapPresent.apply(value);
		}
		else
		{
			return mapAbsent.apply(explanation);
		}
	}
}