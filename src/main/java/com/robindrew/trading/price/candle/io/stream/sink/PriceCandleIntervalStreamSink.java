package com.robindrew.trading.price.candle.io.stream.sink;

import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.io.list.sink.IPriceCandleListSink;

public class PriceCandleIntervalStreamSink implements IPriceCandleStreamSink, IPriceCandleListSink {

	private final IPriceCandleStreamSink sink;
	private final IPriceInterval interval;

	private IPriceCandle merged = null;
	private long mergedTimePeriod;

	public PriceCandleIntervalStreamSink(IPriceCandleStreamSink sink, IPriceInterval interval) {
		if (sink == null) {
			throw new NullPointerException("sink");
		}
		if (interval == null) {
			throw new NullPointerException("interval");
		}
		this.sink = sink;
		this.interval = interval;
	}

	public IPriceInterval getInterval() {
		return interval;
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
	public void putNextCandle(IPriceCandle current) {

		// First candle?
		long currentTimePeriod = interval.getTimePeriod(current);
		if (merged == null) {
			merged = current;
			mergedTimePeriod = currentTimePeriod;
			return;
		}

		// Another candle in the same time period
		if (mergedTimePeriod == currentTimePeriod) {
			merged = merged.mergeWith(current);
			return;
		}

		// New time period, send down the merged candle
		IPriceCandle next = merged;
		merged = current;
		mergedTimePeriod = currentTimePeriod;
		sink.putNextCandle(next);
	}

	@Override
	public void putNextCandles(List<? extends IPriceCandle> candles) {
		for (IPriceCandle candle : candles) {
			putNextCandle(candle);
		}
	}

}
