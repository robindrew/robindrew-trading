package com.robindrew.trading.price.decimal;

import java.math.BigDecimal;

import com.robindrew.trading.price.candle.format.pcf.FloatingPoint;

public class Decimal implements IDecimal {

	private static final int[] PLACES = new int[] { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000 };

	public static Decimal valueOf(String text, int decimalPlaces) {
		int value = FloatingPoint.toBigInt(text, decimalPlaces);
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
	private int decimalPlaces;

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
		this(FloatingPoint.toBigInt(value, decimalPlaces), decimalPlaces);
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
		if (decimalPlaces == 0) {
			return value;
		}
		return toBigDecimal().doubleValue();
	}

	@Override
	public float floatValue() {
		if (decimalPlaces == 0) {
			return value;
		}
		return toBigDecimal().floatValue();
	}

	@Override
	public BigDecimal toBigDecimal() {
		if (decimalPlaces == 0) {
			return new BigDecimal(value);
		}
		return FloatingPoint.toBigDecimal(value, decimalPlaces);
	}

}
