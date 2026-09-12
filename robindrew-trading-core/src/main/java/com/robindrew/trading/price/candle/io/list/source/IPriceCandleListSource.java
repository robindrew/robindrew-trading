package com.robindrew.trading.price.candle.io.list.source;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.util.io.INamedCloseable;
import java.util.List;

public interface IPriceCandleListSource extends INamedCloseable {

    List<IPriceCandle> getNextCandles();
}
