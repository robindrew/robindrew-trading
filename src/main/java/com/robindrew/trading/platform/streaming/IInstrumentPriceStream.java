package com.robindrew.trading.platform.streaming;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.streaming.latest.IStreamingPrice;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public interface IInstrumentPriceStream extends IPriceCandleStreamSink {

	/**
	 * Returns the instrument for which this stream prices.
	 * @return the instrument for which this stream prices.
	 */
	IInstrument getInstrument();

	/**
	 * Returns the latest price from the stream.
	 * @return the latest price from the stream.
	 */
	IStreamingPrice getPrice();

	/**
	 * Register a listener to the streaming prices.
	 * @param sink the sink to register.
	 * @return true if successful.
	 */
	boolean register(IPriceCandleStreamSink sink);

	/**
	 * Unregister a listener to the streaming prices.
	 * @param sink the sink to unregister.
	 * @return true if successful.
	 */
	boolean unregister(IPriceCandleStreamSink sink);

	/**
	 * Close this stream of prices (unsubscribe the underlying price stream).
	 */
	@Override
	void close();

}
