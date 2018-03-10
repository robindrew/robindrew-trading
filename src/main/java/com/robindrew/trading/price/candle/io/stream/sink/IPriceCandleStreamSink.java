package com.robindrew.trading.price.candle.io.stream.sink;

import com.robindrew.common.io.INamedCloseable;
import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleStreamSink extends INamedCloseable {

	void putNextCandle(IPriceCandle candle);
	
}
