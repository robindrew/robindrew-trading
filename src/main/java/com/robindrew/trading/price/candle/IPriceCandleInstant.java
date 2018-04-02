package com.robindrew.trading.price.candle;

public interface IPriceCandleInstant extends IPriceCandle {

	int getBidPrice();

	int getAskPrice();

	int getMidPrice();

	int getDecimalPlaces();

	long getTimestamp();

}
