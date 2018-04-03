package com.robindrew.trading.price.decimal;

import java.math.BigDecimal;

public interface IDecimal {

	int getValue();

	int getDecimalPlaces();

	double doubleValue();

	float floatValue();

	IDecimal setDecimalPlaces(int decimalPlaces);

	BigDecimal toBigDecimal();

}
