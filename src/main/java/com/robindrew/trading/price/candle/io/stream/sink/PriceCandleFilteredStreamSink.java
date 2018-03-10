package com.robindrew.trading.price.candle.io.stream.sink;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.filter.IPriceCandleFilter;

public class PriceCandleFilteredStreamSink implements IPriceCandleStreamSink {

	private final IPriceCandleStreamSink sink;
	private final IPriceCandleFilter filter;

	public PriceCandleFilteredStreamSink(IPriceCandleStreamSink sink, IPriceCandleFilter filter) {
		if (sink == null) {
			throw new NullPointerException("sink");
		}
		if (filter == null) {
			throw new NullPointerException("filter");
		}
		this.sink = sink;
		this.filter = filter;
	}

	@Override
	public String getName() {
		return sink.getName();
	}

	@Override
	public void close() {
		sink.close();
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		if (filter.accept(candle)) {
			sink.putNextCandle(candle);
		}
	}

}
