package com.robindrew.trading.strategy;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.ITradingPlatform;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;
import com.robindrew.trading.provider.ITradingInstrument;

public interface ITradingStrategy<I extends ITradingInstrument> extends IPriceCandleStreamSink {

	ITradingPlatform<I> getPlatform();

	IInstrument getInstrument();

}
