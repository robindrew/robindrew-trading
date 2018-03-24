package com.robindrew.trading.price.candle.format.pcf;

import java.math.BigDecimal;

public class FloatingPoint {

	private static final BigDecimal[] MULTIPLES = createMultiplyArray();

	private static BigDecimal[] createMultiplyArray() {
		BigDecimal[] array = new BigDecimal[10];

		int multiply = 1;
		for (int i = 0; i < array.length; i++) {
			array[i] = new BigDecimal(multiply);
			multiply *= 10;
		}
		return array;
	}

	public static String toString(int value, int decimalPlaces) {
		if (decimalPlaces <= 0) {
			return String.valueOf(value);
		}
		String text = String.valueOf(value);
		int index = text.length() - decimalPlaces;
		return text.substring(0, index) + "." + text.substring(index);
	}

	public static BigDecimal toBigDecimal(int value, int decimalPlaces) {
		return new BigDecimal(toString(value, decimalPlaces)).stripTrailingZeros();
	}

	public static int getDecimalPlaces(double value) {
		return getDecimalPlaces(Double.toString(value));
	}

	public static int getDecimalPlaces(String text) {
		boolean decimal = false;
		int places = 0;
		for (int i = text.length() - 1; i >= 0; i--) {
			char c = text.charAt(i);
			if (c == '.') {
				decimal = true;
				break;
			}
			if (c == '0' && places == 0) {
				continue;
			}
			places++;
		}
		if (!decimal) {
			return 0;
		}
		return places;
	}

	public static int toInt(String text, int decimalPlaces) {
		return toInt(text, decimalPlaces, true);
	}

	public static int toInt(String text, int decimalPlaces, boolean checkPlaces) {
		double value = Double.parseDouble(text);
		return toInt(value, decimalPlaces, checkPlaces);
	}

	public static int toInt(double value, int decimalPlaces) {
		return toInt(value, decimalPlaces, true);
	}

	public static int toInt(double value, int decimalPlaces, boolean checkPlaces) {
		double multiplyDouble = value;
		for (int i = 0; i < decimalPlaces; i++) {
			multiplyDouble *= 10;
		}
		long roundedLong = Math.round(multiplyDouble);

		// Validate all decimal places have been removed
		if (checkPlaces) {
			double checkDouble = multiplyDouble;
			long checkLong = roundedLong;
			for (int i = 0; i < 2; i++) {
				checkDouble *= 10;
				checkLong *= 10;
				if (checkLong != Math.round(checkDouble)) {
					throw new IllegalArgumentException("value=" + value + ", decimalPlaces=" + decimalPlaces);
				}
			}
		}

		// Truncate to integer
		int roundedInt = (int) roundedLong;
		if (roundedInt != roundedLong) {
			throw new IllegalArgumentException("value=" + value + ", decimalPlaces=" + decimalPlaces);
		}
		return roundedInt;
	}

	public static int toBigInt(String text, int decimalPlaces) {
		return toBigInt(text, decimalPlaces, true);
	}

	public static int toBigInt(String text, int decimalPlaces, boolean checkPlaces) {
		BigDecimal value = new BigDecimal(text);
		return toBigInt(value, decimalPlaces, checkPlaces);
	}

	public static int toBigInt(BigDecimal value, int decimalPlaces) {
		return toBigInt(value, decimalPlaces, true);
	}

	public static int toBigInt(BigDecimal value, int decimalPlaces, boolean checkPlaces) {
		BigDecimal multiplyDouble = value.multiply(MULTIPLES[decimalPlaces]);

		long roundedLong = multiplyDouble.toBigInteger().longValue();

		// Truncate to integer
		int roundedInt = (int) roundedLong;
		if (roundedInt != roundedLong) {
			throw new IllegalArgumentException("value=" + value + ", decimalPlaces=" + decimalPlaces);
		}
		return roundedInt;
	}

}
