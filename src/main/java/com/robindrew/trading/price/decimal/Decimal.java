package com.robindrew.trading.price.decimal;

import java.math.BigDecimal;

import com.robindrew.trading.price.candle.format.pcf.FloatingPoint;

public class Decimal implements IDecimal {

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
		return toBigDecimal().doubleValue();
	}

	@Override
	public float floatValue() {
		return toBigDecimal().floatValue();
	}

	@Override
	public BigDecimal toBigDecimal() {
		return FloatingPoint.toBigDecimal(value, decimalPlaces);
	}

}
