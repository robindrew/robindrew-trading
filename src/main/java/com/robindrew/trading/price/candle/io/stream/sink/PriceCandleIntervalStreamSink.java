package com.robindrew.trading.price.candle.io.stream.sink;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.interval.IPriceCandleInterval;

/**
 * Note that this does NOT support monthly intervals or above, as months have different lengths, and years are subject
 * to leap year loss.
 */
public class PriceCandleIntervalStreamSink implements IPriceCandleStreamSink {

	private final IPriceCandleStreamSink sink;
	private final IPriceCandleInterval interval;

	private IPriceCandle merged = null;
	private long mergedTimePeriod;

	public PriceCandleIntervalStreamSink(IPriceCandleStreamSink sink, IPriceCandleInterval interval) {
		if (sink == null) {
			throw new NullPointerException("sink");
		}
		if (interval == null) {
			throw new NullPointerException("interval");
		}
		this.sink = sink;
		this.interval = interval;
	}

	public IPriceCandleInterval getInterval() {
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

}
