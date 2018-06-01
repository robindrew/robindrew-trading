package com.robindrew.trading.price.precision;

import java.math.BigDecimal;

import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPricePrecision {

	int getDecimalPlaces();

	int toBigInt(BigDecimal price);

	BigDecimal toBigDecimal(int price);

	IPriceCandle normalize(IPriceCandle candle);
}
