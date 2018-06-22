package com.robindrew.trading.price.decimal;

import java.math.BigDecimal;

public class Decimal implements IDecimal {

	private static final int[] PLACES = new int[] { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000 };

	public static Decimal valueOf(String text, int decimalPlaces) {
		int value = Decimals.toBigInt(text, decimalPlaces);
		return new Decimal(value, decimalPlaces);
	}

	public static Decimal multiply(IDecimal decimal1, IDecimal decimal2) {
		int decimalPlaces1 = decimal1.getDecimalPlaces();
		int decimalPlaces2 = decimal2.getDecimalPlaces();

		int value1 = decimal1.getValue();
		int value2 = decimal2.getValue();

		// Decimal places match?
		if (decimalPlaces1 == decimalPlaces2) {
			return new Decimal(value1 * value2, decimalPlaces1);
		}

		if (decimalPlaces1 > decimalPlaces2) {
			decimal2 = decimal2.setDecimalPlaces(decimalPlaces1);
			return multiply(decimal1, decimal2);
		} else {
			decimal1 = decimal1.setDecimalPlaces(decimalPlaces2);
			return multiply(decimal1, decimal2);
		}
	}

	@Override
	public Decimal setDecimalPlaces(int decimalPlaces) {
		if (getDecimalPlaces() == decimalPlaces) {
			return this;
		}
		int places = decimalPlaces - getDecimalPlaces();
		if (places >= 0) {
			int multiplier = PLACES[places];
			return new Decimal(value * multiplier, decimalPlaces);
		} else {
			int divisor = PLACES[-places];
			return new Decimal(value / divisor, decimalPlaces);
		}
	}

	private final int value;
	private final int decimalPlaces;

	public Decimal(int value, int decimalPlaces) {
		if (value <= 0) {
			throw new IllegalArgumentException("value=" + value);
		}
		if (decimalPlaces < 0) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}
		this.value = value;
		this.decimalPlaces = decimalPlaces;
	}

	public Decimal(BigDecimal value, int decimalPlaces) {
		this(Decimals.toBigInt(value, decimalPlaces), decimalPlaces);
	}

	public Decimal(double value, int decimalPlaces) {
		this(Decimals.toInt(value, decimalPlaces), decimalPlaces);
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	@Override
	public double doubleValue() {
		return Decimals.toDouble(value, decimalPlaces);
	}

	@Override
	public float floatValue() {
		return Decimals.toFloat(value, decimalPlaces);
	}

	@Override
	public BigDecimal toBigDecimal() {
		return Decimals.toBigDecimal(value, decimalPlaces);
	}

	@Override
	public String toString() {
		return Decimals.toString(value, decimalPlaces);
	}

	@Override
	public int hashCode() {
		return 1999 * (value + decimalPlaces);
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object instanceof Decimal) {
			Decimal that = (Decimal) object;
			return this.getValue() == that.getValue() && this.getDecimalPlaces() == that.getDecimalPlaces();
		}
		return false;
	}

}
