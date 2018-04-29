package com.robindrew.trading.price.candle.io.list.sink;

import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public class PriceCandleListToStreamSink implements IPriceCandleListSink {

	private final IPriceCandleStreamSink sink;

	public PriceCandleListToStreamSink(IPriceCandleStreamSink sink) {
		if (sink == null) {
			throw new NullPointerException("sink");
		}
		this.sink = sink;
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
	public void putNextCandles(List<? extends IPriceCandle> candles) {
		for (IPriceCandle candle : candles) {
			sink.putNextCandle(candle);
		}
	}

}
