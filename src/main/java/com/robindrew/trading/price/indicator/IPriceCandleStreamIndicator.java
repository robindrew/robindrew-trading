package com.robindrew.trading.price.indicator;

import com.google.common.base.Optional;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public interface IPriceCandleStreamIndicator<V> extends IPriceCandleStreamSink {

	Optional<V> getIndicator();

}
