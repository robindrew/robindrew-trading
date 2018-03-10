package com.robindrew.trading.price;

import java.math.BigDecimal;

import com.robindrew.trading.price.candle.format.pcf.FloatingPoint;

public class IntDecimal {

	private static final int[] PLACES = new int[] { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000 };

	public static IntDecimal valueOf(String text, int decimalPlaces) {
		int value = FloatingPoint.toBigInt(text, decimalPlaces);
		return new IntDecimal(value, decimalPlaces);
	}

	public static IntDecimal multiply(IntDecimal decimal1, IntDecimal decimal2) {
		int decimalPlaces1 = decimal1.getDecimalPlaces();
		int decimalPlaces2 = decimal2.getDecimalPlaces();

		int value1 = decimal1.getValue();
		int value2 = decimal2.getValue();

		// Decimal places match?
		if (decimalPlaces1 == decimalPlaces2) {
			return new IntDecimal(value1 * value2, decimalPlaces1);
		}

		if (decimalPlaces1 > decimalPlaces2) {
			decimal2 = decimal2.setDecimalPlaces(decimalPlaces1);
			return multiply(decimal1, decimal2);
		} else {
			decimal1 = decimal1.setDecimalPlaces(decimalPlaces2);
			return multiply(decimal1, decimal2);
		}
	}

	public IntDecimal setDecimalPlaces(int decimalPlaces) {
		if (getDecimalPlaces() == decimalPlaces) {
			return this;
		}
		int places = decimalPlaces - getDecimalPlaces();
		if (places >= 0) {
			int multiplier = PLACES[places];
			return new IntDecimal(value * multiplier, decimalPlaces);
		} else {
			int divisor = PLACES[-places];
			return new IntDecimal(value / divisor, decimalPlaces);
		}
	}

	private final int value;
	private final int decimalPlaces;

	public IntDecimal(int value, int decimalPlaces) {
		if (decimalPlaces < 0) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}
		this.value = value;
		this.decimalPlaces = decimalPlaces;
	}

	public IntDecimal(BigDecimal value, int decimalPlaces) {
		this.value = FloatingPoint.toBigInt(value, decimalPlaces);
		this.decimalPlaces = decimalPlaces;
	}

	public int getValue() {
		return value;
	}

	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	public IntDecimal multiply(IntDecimal decimal) {
		return multiply(this, decimal);
	}

	@Override
	public int hashCode() {
		return 1999 * value + decimalPlaces;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (object instanceof IntDecimal) {
			IntDecimal decimal = (IntDecimal) object;
			if (getValue() != decimal.getValue()) {
				return false;
			}
			if (getDecimalPlaces() != decimal.getDecimalPlaces()) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		int value = getValue();
		if (value == 0) {
			return "0.0";
		}
		int divisor = PLACES[decimalPlaces];

		StringBuilder text = new StringBuilder();
		text.append(value / divisor);
		text.append('.');

		int decimal = value % divisor;
		if (decimal == 0 || divisor == 1) {
			text.append('0');
		} else {
			divisor /= 10;
			while (divisor > decimal) {
				text.append('0');
				divisor /= 10;
			}
			text.append(decimal);
		}
		return text.toString();
	}
}
