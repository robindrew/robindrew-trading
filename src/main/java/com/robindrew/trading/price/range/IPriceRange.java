package com.robindrew.trading.price.range;

public interface IPriceRange {

	double getMin();

	double getMax();

	boolean doubleInRange(double value);

	boolean decimalInRange(int value, int decimalPlaces);
}
