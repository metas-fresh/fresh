package de.metas.util;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Number Utils
 *
 * @author tsa
 */
public final class NumberUtils
{
	private NumberUtils()
	{
	}

	/**
	 * Remove trailing zeros after decimal separator
	 *
	 * @return <code>bd</code> without trailing zeros after separator; if argument is NULL then NULL will be returned
	 */
	public static BigDecimal stripTrailingDecimalZeros(final BigDecimal bd)
	{
		if (bd == null)
		{
			return null;
		}

		//
		// Remove all trailing zeros
		BigDecimal result = bd.stripTrailingZeros();

		// Fix very weird java 6 bug: stripTrailingZeros doesn't work on 0 itself
		// see http://stackoverflow.com/questions/5239137/clarification-on-behavior-of-bigdecimal-striptrailingzeroes
		// see http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6480539 => fixed in 8 (b100)
		if (result.signum() == 0)
		{
			result = BigDecimal.ZERO;
		}

		//
		// If after removing our scale is negative, we can safely set the scale to ZERO because we don't want to get rid of zeros before decimal point
		if (result.scale() < 0)
		{
			//noinspection BigDecimalMethodWithoutRoundingCalled
			result = result.setScale(0);
		}

		return result;
	}

	/**
	 * Converts given <code>bd</code> to a big decimal which has at least <code>minScale</code> decimals.
	 * <p>
	 * If it has more decimals (and not trailing zeros) the value will not be changed.
	 */
	public static BigDecimal setMinimumScale(final BigDecimal bd, final int minScale)
	{
		BigDecimal result = bd;
		if (result.scale() < minScale)
		{
			return result.setScale(minScale, RoundingMode.UNNECESSARY);
		}

		result = stripTrailingDecimalZeros(result);
		if (result.scale() < minScale)
		{
			return result.setScale(minScale, RoundingMode.UNNECESSARY);
		}

		return result;
	}

	/**
	 * Creates the error margin absolute value for given scale.
	 * <p>
	 * e.g.
	 * <ul>
	 * <li>for scale=0 it will return 0
	 * <li>for scale=1 it will return 0.1
	 * <li>for scale=2 it will return 0.01
	 * <li>for scale=-1 it will return 10
	 * <li>for scale=-2 it will return 100
	 * </ul>
	 *
	 * @return error mergin (absolute value)
	 */
	public static BigDecimal getErrorMarginForScale(final int scale)
	{
		if (scale == 0)
		{
			return BigDecimal.ZERO;
		}
		return BigDecimal.ONE.movePointLeft(scale);
	}

	/**
	 * Converts given <code>value</code> to BigDecimal.
	 *
	 * @return <ul>
	 * <li>{@link BigDecimal} if the value is a BigDecimal or its string representation can be converted to BigDecimal
	 * <li><code>defaultValue</code> if value is <code>null</code> or it's string representation cannot be converted to BigDecimal.
	 * </ul>
	 */
	public static BigDecimal asBigDecimal(@Nullable final Object value, @Nullable final BigDecimal defaultValue)
	{
		final boolean failIfUnparsable = false;
		return asBigDecimal(value, defaultValue, failIfUnparsable);
	}

	public static BigDecimal asBigDecimal(@Nullable final Object value)
	{
		final BigDecimal defaultValue = null;
		final boolean failIfUnparsable = true;
		return asBigDecimal(value, defaultValue, failIfUnparsable);
	}

	private static BigDecimal asBigDecimal(
			@Nullable final Object value,
			@Nullable final BigDecimal defaultValue,
			final boolean failIfUnparsable)
	{
		if (value == null) //note that a zero-BigDecimal is also "empty" according to Check.IsEmpty()!
		{
			return defaultValue;
		}
		if (value instanceof BigDecimal)
		{
			return (BigDecimal)value;
		}
		else if (value instanceof Integer)
		{
			return BigDecimal.valueOf((int)value);
		}
		else if (value instanceof Long)
		{
			return BigDecimal.valueOf((long)value);
		}
		else
		{
			final String valueStr = value.toString();
			if (Check.isBlank(valueStr))
			{
				return defaultValue;
			}
			try
			{
				return new BigDecimal(valueStr);
			}
			catch (final NumberFormatException numberFormatException)
			{
				final String errorMsg = "Cannot convert `" + value + "` (" + value.getClass() + ") to BigDecimal";

				if (failIfUnparsable)
				{
					final RuntimeException ex = Check.mkEx(errorMsg);
					ex.initCause(numberFormatException);
					throw ex;
				}
				else
				{
					System.err.println(errorMsg + ". Returning defaultValue=" + defaultValue);
					numberFormatException.printStackTrace();
					return defaultValue;
				}
			}
		}
	}

	public static int asInt(@NonNull final Object value)
	{
		final Integer integerValue = asIntegerOrNull(value);
		if (integerValue == null)
		{
			throw Check.mkEx("Cannot convert `" + value + "` (" + value.getClass() + ") to int");
		}
		return integerValue;
	}

	public static int asIntOrZero(final Object value)
	{
		return asInt(value, 0);
	}

	/**
	 * Converts given <code>value</code> to integer.
	 *
	 * @return <ul>
	 * <li>integer value if the value is a integer or its string representation can be converted to integer
	 * <li><code>defaultValue</code> if value is <code>null</code> or it's string representation cannot be converted to integer.
	 * </ul>
	 */
	public static int asInt(@Nullable final Object value, final int defaultValue)
	{
		return asInteger(value, defaultValue);
	}

	public static Integer asIntegerOrNull(@Nullable final Object value)
	{
		return asInteger(value, null);
	}

	public static Integer asInteger(@Nullable final Object value, @Nullable final Integer defaultValue)
	{
		if (value == null)
		{
			return defaultValue;
		}
		else if (value instanceof RepoIdAware)
		{
			return ((RepoIdAware)value).getRepoId();
		}
		else if (value instanceof Integer)
		{
			return (Integer)value;
		}
		else if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}
		else
		{
			try
			{
<<<<<<< HEAD
				final BigDecimal bd = new BigDecimal(value.toString());
=======
				final String valueStr = StringUtils.trimBlankToNull(value.toString());
				if (valueStr == null)
				{
					return defaultValue;
				}

				final BigDecimal bd = new BigDecimal(valueStr);
>>>>>>> 0eed8b1baf6 (Cache API improvements for observability (REST API) and configuration (#16625))
				return bd.intValue();
			}
			catch (final NumberFormatException e)
			{
				// System.err.println("Cannot convert " + value + " to integer.");
				// e.printStackTrace();

				return defaultValue;
			}
		}
	}

	public static BigDecimal randomBigDecimal(
			final BigDecimal valueMin,
			final BigDecimal valueMax,
			final int scale)
	{
		final BigDecimal range = valueMax.subtract(valueMin);
		final BigDecimal random = BigDecimal.valueOf(Math.random());

		return valueMin
				.add(random.multiply(range))
				.setScale(scale, RoundingMode.DOWN);

	}

	@Nullable
	public static Integer graterThanZeroOrNull(@Nullable final Integer value)
	{
		return Optional.ofNullable(value)
				.filter(v1 -> v1 > 0)
				.orElse(null);
	}
}
