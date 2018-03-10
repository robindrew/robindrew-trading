package com.robindrew.trading.price.precision;

import java.math.BigDecimal;

public interface IPricePrecision {

	int getDecimalPlaces();

	int getMinPrice();

	int getMaxPrice();

	int toBigInt(BigDecimal price);

	BigDecimal toBigDecimal(int price);

}
