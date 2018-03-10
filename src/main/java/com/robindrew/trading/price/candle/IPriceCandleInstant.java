package com.robindrew.trading.price.candle;

public interface IPriceCandleInstant {

	int getMidPrice();

	int getDecimalPlaces();

	long getTimestamp();

}
