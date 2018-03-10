package com.robindrew.trading.price.candle.io.stream.source;

import com.robindrew.common.io.INamedCloseable;
import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleStreamSource extends INamedCloseable {

	IPriceCandle getNextCandle();

}
