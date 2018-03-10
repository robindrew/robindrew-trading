package com.robindrew.trading.price.candle.io.list.sink;

import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.list.filter.IPriceCandleListFilter;

public class PriceCandleListFilteredSink implements IPriceCandleListSink {

	private final IPriceCandleListSink sink;
	private final IPriceCandleListFilter filter;

	public PriceCandleListFilteredSink(IPriceCandleListSink sink, IPriceCandleListFilter filter) {
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
	public void putNextCandles(List<IPriceCandle> candles) {
		candles = filter.filter(candles);
		sink.putNextCandles(candles);
	}

}
