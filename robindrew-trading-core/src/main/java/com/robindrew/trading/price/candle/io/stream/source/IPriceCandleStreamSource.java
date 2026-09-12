package com.robindrew.trading.price.candle.io.stream.source;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.util.io.INamedCloseable;

public interface IPriceCandleStreamSource extends INamedCloseable {

    IPriceCandle getNextCandle();
}
