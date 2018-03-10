package com.robindrew.trading.price.candle.io.stream.sink;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.robindrew.trading.price.candle.IPriceCandle;

public class MultiStreamSink implements IPriceCandleStreamSink {

	private final String name;
	private final Set<IPriceCandleStreamSink> sinks = new CopyOnWriteArraySet<>();

	public MultiStreamSink(String name) {
		if (name == null) {
			throw new NullPointerException("name");
		}
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void close() {
		for (IPriceCandleStreamSink sink : sinks) {
			sink.close();
		}
		sinks.clear();
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		for (IPriceCandleStreamSink sink : sinks) {
			sink.putNextCandle(candle);
		}
	}
}
