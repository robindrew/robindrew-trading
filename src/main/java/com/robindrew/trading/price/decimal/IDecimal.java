package com.robindrew.trading.price.decimal;

import java.math.BigDecimal;

public interface IDecimal {

	int getValue();

	int getDecimalPlaces();

	double doubleValue();

	float floatValue();

	IDecimal setDecimalPlaces(int decimalPlaces);

	BigDecimal toBigDecimal();

	IDecimal add(IDecimal value);

	IDecimal subtract(IDecimal value);

	IDecimal multiply(IDecimal value);

	IDecimal add(BigDecimal value);

	IDecimal subtract(BigDecimal value);

	IDecimal multiply(BigDecimal value);

}
