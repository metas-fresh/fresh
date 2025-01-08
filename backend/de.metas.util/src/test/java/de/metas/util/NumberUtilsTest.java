package de.metas.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberUtilsTest
{
	@Nested
	public class stripTrailingDecimalZeros
	{
		@Test
		public void nullValue()
		{
			assertThat(NumberUtils.stripTrailingDecimalZeros(null)).isNull();
		}

		@Test
		public void zeros()
		{
			test_stripTrailingDecimalZeros("0", "0");
			test_stripTrailingDecimalZeros("0", "0.0");
			test_stripTrailingDecimalZeros("0", "0.00");
			test_stripTrailingDecimalZeros("0", "0.0000000000000000000000000000000");
		}

		@Test
		public void standardTests()
		{
			test_stripTrailingDecimalZeros("0", "0");
			test_stripTrailingDecimalZeros("10", "10");
			test_stripTrailingDecimalZeros("10", "10.0");
			test_stripTrailingDecimalZeros("100000000", "100000000.0");
			test_stripTrailingDecimalZeros("10.1", "10.1");
			test_stripTrailingDecimalZeros("10.1", "10.10");
			test_stripTrailingDecimalZeros("10.1", "10.1000000000");
			test_stripTrailingDecimalZeros("10.1234567", "10.1234567");
			test_stripTrailingDecimalZeros("10.1234567", "10.12345670");
			test_stripTrailingDecimalZeros("10.1234567", "10.123456700000");

			test_stripTrailingDecimalZeros("-10", "-10");
			test_stripTrailingDecimalZeros("-10", "-10.0");
			test_stripTrailingDecimalZeros("-100000000", "-100000000.0");
			test_stripTrailingDecimalZeros("-10.1", "-10.1");
			test_stripTrailingDecimalZeros("-10.1", "-10.10");
			test_stripTrailingDecimalZeros("-10.1", "-10.1000000000");
			test_stripTrailingDecimalZeros("-10.1234567", "-10.1234567");
			test_stripTrailingDecimalZeros("-10.1234567", "-10.12345670");
			test_stripTrailingDecimalZeros("-10.1234567", "-10.123456700000");
		}

		private void test_stripTrailingDecimalZeros(final String expectedNumberStr, String numberStr)
		{
			final BigDecimal expectedNumber = new BigDecimal(expectedNumberStr);
			final BigDecimal number = new BigDecimal(numberStr);

			final BigDecimal actualNumber = NumberUtils.stripTrailingDecimalZeros(number);

			assertThat(actualNumber)
					.as("numberStr=" + numberStr)
					.isEqualTo(expectedNumber);
		}
	}

	@Nested
	public class getErrorMarginForScale
	{
		@Test
		public void standardTests()
		{
			test_getErrorMarginForScale("0", 0);
			test_getErrorMarginForScale("0.1", 1);
			test_getErrorMarginForScale("0.01", 2);
			test_getErrorMarginForScale("0.001", 3);
			//
			test_getErrorMarginForScale("10", -1);
			test_getErrorMarginForScale("100", -2);
			test_getErrorMarginForScale("1000", -3);
		}

		private void test_getErrorMarginForScale(final String expectedValueStr, final int scale)
		{
			assertThat(NumberUtils.getErrorMarginForScale(scale))
					.withFailMessage("Invalid ErrorMargin for scale=" + scale)
					.isEqualTo(new BigDecimal(expectedValueStr));
		}
	}

	@Nested
	public class randomBigDecimal
	{
		@RepeatedTest(100)
		public void between_100_and_900_scale_3()
		{
			test("100", "900", 3);
		}

		@SuppressWarnings("SameParameterValue")
		private void test(final String valueMinStr, final String valueMaxStr, final int scale)
		{
			final BigDecimal valueMin = new BigDecimal(valueMinStr);
			final BigDecimal valueMax = new BigDecimal(valueMaxStr);

			final BigDecimal value = NumberUtils.randomBigDecimal(valueMin, valueMax, scale);

			assertThat(value)
					.isGreaterThanOrEqualTo(valueMin)
					.isLessThanOrEqualTo(valueMax);
			assertThat(value.scale()).isLessThanOrEqualTo(scale);
		}
	}

	@Test
	void asBigDecimal()
	{
		assertThat(NumberUtils.asBigDecimal(new BigDecimal("0"))).isEqualByComparingTo(BigDecimal.ZERO);
	}

	@Nested
	public class asInt_with_defaultValue
	{
		@Test
		void emptyString()
		{
			assertThat(NumberUtils.asInt("", -100)).isEqualTo(-100);
		}
	}
}
