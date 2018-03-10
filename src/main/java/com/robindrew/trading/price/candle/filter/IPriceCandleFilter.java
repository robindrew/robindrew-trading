package com.robindrew.trading.price.candle.filter;

import com.robindrew.trading.price.candle.IPriceCandle;

@FunctionalInterface
public interface IPriceCandleFilter {

	boolean accept(IPriceCandle candle);

}
