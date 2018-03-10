package com.robindrew.trading.price.candle.io.list;

import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.list.sink.IPriceCandleListSink;
import com.robindrew.trading.price.candle.io.list.source.IPriceCandleListSource;

public class PriceCandleListPipe {

	private final IPriceCandleListSource source;
	private final IPriceCandleListSink sink;

	public PriceCandleListPipe(IPriceCandleListSource source, IPriceCandleListSink sink) {
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
			List<IPriceCandle> candles = source.getNextCandles();
			if (candles.isEmpty()) {
				break;
			}
			sink.putNextCandles(candles);
		}
	}

}
