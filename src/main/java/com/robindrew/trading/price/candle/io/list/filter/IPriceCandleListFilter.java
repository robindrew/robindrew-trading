package com.robindrew.trading.price.candle.io.list.filter;

import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;

@FunctionalInterface
public interface IPriceCandleListFilter {

	List<IPriceCandle> filter(List<? extends IPriceCandle> candles);

}
