package com.robindrew.trading.platform.streaming;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.streaming.latest.ILatestPrice;
import com.robindrew.trading.price.candle.io.stream.sink.subscriber.IInstrumentPriceStreamListener;

public interface IInstrumentPriceStream extends AutoCloseable {

	IInstrument getInstrument();

	IInstrumentPriceStreamListener getListener();

	ILatestPrice getLatestPrice();

	@Override
	void close();

}
