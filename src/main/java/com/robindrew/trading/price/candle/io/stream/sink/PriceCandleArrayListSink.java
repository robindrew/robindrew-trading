package com.robindrew.trading.price.candle.io.stream.sink;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.list.sink.IPriceCandleListSink;

public class PriceCandleArrayListSink implements IPriceCandleStreamSink, IPriceCandleListSink {

	private final List<IPriceCandle> list = new ArrayList<>();

	@Override
	public String getName() {
		return "PriceCandleArrayListStreamSink";
	}

	@Override
	public void close() {
		list.clear();
	}

	public List<IPriceCandle> toList() {
		return ImmutableList.copyOf(list);
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		Check.notNull("candle", candle);
		list.add(candle);
	}

	@Override
	public void putNextCandles(List<? extends IPriceCandle> candles) {
		list.addAll(candles);
	}

}
