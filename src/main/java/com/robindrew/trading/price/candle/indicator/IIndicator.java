package com.robindrew.trading.price.candle.indicator;

import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

/**
 * A Price Indicator - a {@link IPriceCandleStreamSink} that builds the underlying indicator as it receives updates.
 */
public interface IIndicator extends IPriceCandleStreamSink {

}
