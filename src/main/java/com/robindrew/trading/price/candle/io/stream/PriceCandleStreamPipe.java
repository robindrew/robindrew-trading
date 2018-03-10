package com.robindrew.trading.price.candle.io.stream;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;

public class PriceCandleStreamPipe {

	private final IPriceCandleStreamSource source;
	private final IPriceCandleStreamSink sink;

	public PriceCandleStreamPipe(IPriceCandleStreamSource source, IPriceCandleStreamSink sink) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (sink == null) {
			throw new NullPointerException("sink");
		}
		this.source = source;
		this.sink = sink;
	}

	public void pipe() {
		while (true) {
			IPriceCandle candle = source.getNextCandle();
			if (candle == null) {
				break;
			}
			sink.putNextCandle(candle);
		}
	}

}
