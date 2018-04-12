package com.robindrew.trading.price.candle;

public interface ITickPriceCandle extends IPriceCandle {

	long getTimestamp();

	int getMidPrice();

	int getBidPrice();

	int getAskPrice();

}
