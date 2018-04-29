package com.robindrew.trading.price.candle.io.list.sink;

import java.util.List;

import com.robindrew.common.io.INamedCloseable;
import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleListSink extends INamedCloseable {

	void putNextCandles(List<? extends IPriceCandle> candles);

}
