package com.robindrew.trading.price.calc;

import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleStreamCalc {

	int getMinPrices();

	int getMaxPrices();

	void next(IPriceCandle price, int index);

}
