package com.robindrew.trading.strategy;

import com.robindrew.trading.platform.ITradingPlatform;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;
import com.robindrew.trading.price.tick.io.stream.sink.IPriceTickStreamSink;

public interface ITradingStrategy extends IPriceCandleStreamSink, IPriceTickStreamSink {

	ITradingPlatform getPlatform();

}
