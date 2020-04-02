package com.robindrew.trading.price.candle.tick;

import com.robindrew.trading.price.candle.IPriceCandle;

public interface ITickPriceCandle extends IPriceCandle {

	long getTimestamp();

	int getMidPrice();

	int getBidPrice();

	int getAskPrice();

}
