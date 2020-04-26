package com.robindrew.trading.price.range;

import static com.robindrew.trading.price.decimal.Decimals.decimalToDouble;

public class PriceRange implements IPriceRange {

	private final double min;
	private final double max;

	public PriceRange(double min, double max) {
		if (min <= 0.0) {
			throw new IllegalArgumentException("min=" + min);
		}
		if (max <= 0.0) {
			throw new IllegalArgumentException("max=" + max);
		}
		if (max <= min) {
			throw new IllegalArgumentException("min=" + min + ", max=" + max);
		}
		this.min = min;
		this.max = max;
	}

	@Override
	public double getMin() {
		return min;
	}

	@Override
	public double getMax() {
		return max;
	}

	@Override
	public boolean doubleInRange(double value) {
		return min <= value && value <= max;
	}

	@Override
	public boolean decimalInRange(int value, int decimalPlaces) {
		return doubleInRange(decimalToDouble(value, decimalPlaces));
	}

}
