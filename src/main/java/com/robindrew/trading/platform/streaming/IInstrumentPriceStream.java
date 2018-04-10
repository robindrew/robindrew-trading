package com.robindrew.trading.platform.streaming;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.streaming.IStreamingCandlePrice;
import com.robindrew.trading.price.tick.io.stream.sink.IPriceTickStreamSink;

public interface IInstrumentPriceStream extends IPriceTickStreamSink {

	/**
	 * Returns the instrument for which this stream prices.
	 * @return the instrument for which this stream prices.
	 */
	IInstrument getInstrument();

	/**
	 * Returns the latest price from the stream.
	 * @return the latest price from the stream.
	 */
	IStreamingCandlePrice getPrice();

	/**
	 * Register a listener to the streaming prices.
	 * @param sink the sink to register.
	 * @return true if successful.
	 */
	boolean register(IPriceTickStreamSink sink);

	/**
	 * Unregister a listener to the streaming prices.
	 * @param sink the sink to unregister.
	 * @return true if successful.
	 */
	boolean unregister(IPriceTickStreamSink sink);

	/**
	 * Close this stream of prices (unsubscribe the underlying price stream).
	 */
	@Override
	void close();

}
