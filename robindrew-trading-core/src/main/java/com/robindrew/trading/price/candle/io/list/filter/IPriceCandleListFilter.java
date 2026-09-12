package com.robindrew.trading.price.candle.io.list.filter;

import com.robindrew.trading.price.candle.IPriceCandle;
import java.util.List;

@FunctionalInterface
public interface IPriceCandleListFilter {

    List<IPriceCandle> filter(List<? extends IPriceCandle> candles);
}
