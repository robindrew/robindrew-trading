package com.robindrew.trading.price.decimal;

import java.math.BigDecimal;

public class Decimals {

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

	/**
	 * Optimised method for converting a Decimal to a String.
	 * 
	 * @param value
	 *            the decimal value.
	 * @param decimalPlaces
	 *            the number of decimal places.
	 * @return the string equivalent.
	 */
	public static String toString(int value, int decimalPlaces) {
		if (value < 1) {
			throw new IllegalArgumentException("value=" + value);
		}
		if (decimalPlaces < 0) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}

		// Shortcut
		if (decimalPlaces == 0) {
			return String.valueOf(value);
		}

		final int length = 12;
		final char[] array = new char[length];

		int places = decimalPlaces;
		int latest = value;
		int offset;
		boolean first = true;
		for (offset = length - 1; offset >= 0; offset--) {
			int remainder = latest % 10;
			latest /= 10;

			// Special case - remove trailing zeros
			if (first) {
				if (places > 0 && remainder == 0) {
					places--;
					offset++;
					continue;
				}
				first = false;
			}

			// Digit
			array[offset] = (char) (48 + remainder);

			// Handle decimal point
			places--;
			if (places == 0) {
				offset--;
				array[offset] = '.';
			}

			// Finished?
			if (latest == 0 && places < 0) {
				break;
			}
		}

		return new String(array, offset, length - offset);
	}

	public static BigDecimal toBigDecimal(int value, int decimalPlaces) {
		if (decimalPlaces == 0) {
			return new BigDecimal(value);
		}
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

	public static int stringToInt(String text, int decimalPlaces) {
		return stringToInt(text, decimalPlaces, true);
	}

	public static int stringToInt(String text, int decimalPlaces, boolean checkPlaces) {
		double value = Double.parseDouble(text);
		return doubleToInt(value, decimalPlaces, checkPlaces);
	}

	public static int doubleToInt(double value, int decimalPlaces) {
		return doubleToInt(value, decimalPlaces, true);
	}

	public static int doubleToInt(double value, int decimalPlaces, boolean checkPlaces) {
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

		// Truncate to integer (and check we haven't lost any digits!)
		int roundedInt = (int) roundedLong;
		if (roundedInt != roundedLong) {
			throw new IllegalArgumentException(
					"Digits lost when rounding to integer, value=" + value + ", decimalPlaces=" + decimalPlaces);
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

	public static int toBigInt(BigDecimal value) {
		return toBigInt(value, value.scale(), false);
	}

	public static int toBigInt(BigDecimal value, int decimalPlaces, boolean checkPlaces) {
		if (decimalPlaces < 0) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}

		BigDecimal multiplyDouble = value.multiply(MULTIPLES[decimalPlaces]);

		long roundedLong = multiplyDouble.toBigInteger().longValue();

		// Truncate to integer (and check we haven't lost any digits!)
		int roundedInt = (int) roundedLong;
		if (roundedInt != roundedLong) {
			throw new IllegalArgumentException(
					"Digits lost when rounding to integer, value=" + value + ", decimalPlaces=" + decimalPlaces);
		}
		return roundedInt;
	}

	public static float decimalToFloat(int value, int decimalPlaces) {
		if (decimalPlaces < 0) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}

		float floating = value;
		for (int i = 0; i < decimalPlaces; i++) {
			floating /= 10.0f;
		}
		return floating;
	}

	public static double decimalToDouble(int value, int decimalPlaces) {
		if (decimalPlaces < 0) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}

		double floating = value;
		for (int i = 0; i < decimalPlaces; i++) {
			floating /= 10.0;
		}
		return floating;
	}

	public static int roundDoubleToInt(double value) {
		return (int) (value + 0.5);
	}

	public static int decimalToInt(int value, int decimalPlaces) {
		return roundDoubleToInt(decimalToDouble(value, decimalPlaces));
	}

}
