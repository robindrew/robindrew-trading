package com.robindrew.trading.price.candle.io.stream.sink.subscriber;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public interface IInstrumentPriceStreamListener extends IPriceCandleStreamSink {

	IInstrument getInstrument();

	boolean register(IPriceCandleStreamSink sink);

	boolean unregister(IPriceCandleStreamSink sink);

}
