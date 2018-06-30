package com.robindrew.trading.price.decimal;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class DecimalTest {

	@Test
	public void testToString() {

		testToString("1234", 1234, 0);
		testToString("123.4", 1234, 1);
		testToString("12.34", 1234, 2);
		testToString("1.234", 1234, 3);
		testToString("0.1234", 1234, 4);
		testToString("0.01234", 1234, 5);
	}

	private void testToString(String expected, int value, int decimalPlaces) {

		String bigDecimal = new BigDecimal(expected).toPlainString();
		String intDecimal = Decimals.toString(value, decimalPlaces);

		Assert.assertEquals(bigDecimal, intDecimal);
	}

	@Test
	public void testToBigDecimal() {

		testToBigDecimal("1234", 1234, 0);
		testToBigDecimal("123.4", 1234, 1);
		testToBigDecimal("12.34", 1234, 2);
		testToBigDecimal("1.234", 1234, 3);
		testToBigDecimal("0.1234", 1234, 4);
		testToBigDecimal("0.01234", 1234, 5);
	}

	private void testToBigDecimal(String expected, int value, int decimalPlaces) {

		BigDecimal expectedDecimal = new BigDecimal(expected);
		BigDecimal actualDecimal = Decimals.toBigDecimal(value, decimalPlaces);

		Assert.assertEquals(expectedDecimal, actualDecimal);
	}

	@Test
	public void testFromBigDecimal() {
		testFromBigDecimal(new Decimal(1234, 0), "1234");
		testFromBigDecimal(new Decimal(1234, 1), "123.4");
		testFromBigDecimal(new Decimal(1234, 2), "12.34");
		testFromBigDecimal(new Decimal(1234, 3), "1.234");
		testFromBigDecimal(new Decimal(1234, 4), "0.1234");
	}

	private static void testFromBigDecimal(Decimal decimal, String textValue) {
		BigDecimal value = new BigDecimal(textValue);
		Assert.assertEquals(decimal, new Decimal(value));
	}

	@Test
	public void testToDouble() {

		testToDouble(1234.0, 1234, 0);
		testToDouble(123.4, 1234, 1);
		testToDouble(12.34, 1234, 2);
		testToDouble(1.234, 1234, 3);
		testToDouble(0.1234, 1234, 4);
		testToDouble(0.01234, 1234, 5);
	}

	private void testToDouble(double expected, int value, int decimalPlaces) {

		double actual = Decimals.toDouble(value, decimalPlaces);

		Assert.assertEquals(expected, actual, 0.0001);
	}

}
