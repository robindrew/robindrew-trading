package com.robindrew.trading.price.precision;

import java.math.BigDecimal;

public interface IPricePrecision {

	int getDecimalPlaces();

	int toBigInt(BigDecimal price);

	BigDecimal toBigDecimal(int price);

}
