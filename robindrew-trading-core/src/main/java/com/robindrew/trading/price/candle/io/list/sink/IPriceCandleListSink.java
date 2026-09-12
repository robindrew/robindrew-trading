package com.robindrew.trading.price.candle.io.list.sink;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.util.io.INamedCloseable;
import java.util.List;

public interface IPriceCandleListSink extends INamedCloseable {

    void putNextCandles(List<? extends IPriceCandle> candles);
}
