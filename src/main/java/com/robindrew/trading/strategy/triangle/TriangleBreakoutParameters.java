package com.robindrew.trading.strategy.triangle;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.IPriceCandleInstant;
import com.robindrew.trading.price.candle.interval.IPriceCandleInterval;

public class TriangleBreakoutParameters {

	private final IPriceCandleInterval interval;
	private final IPriceCandleInstant upper1;
	private final IPriceCandleInstant upper2;
	private final IPriceCandleInstant lower1;
	private final IPriceCandleInstant lower2;

	public TriangleBreakoutParameters(IPriceCandleInterval interval, IPriceCandleInstant upper1, IPriceCandleInstant upper2, IPriceCandleInstant lower1, IPriceCandleInstant lower2) {
		this.interval = Check.notNull("interval", interval);
		this.upper1 = Check.notNull("upper1", upper1);
		this.upper2 = Check.notNull("upper2", upper2);
		this.lower1 = Check.notNull("lower1", lower1);
		this.lower2 = Check.notNull("lower2", lower2);

		// Sanity checks
		if (upper1.getTimestamp() >= upper2.getTimestamp()) {
			throw new IllegalArgumentException("upper1=" + upper1 + ", upper2=" + upper2);
		}
		if (lower1.getTimestamp() >= lower2.getTimestamp()) {
			throw new IllegalArgumentException("lower1=" + lower1 + ", lower2=" + lower2);
		}
	}

	public IPriceCandleInterval getInterval() {
		return interval;
	}

	public IPriceCandleInstant getUpper1() {
		return upper1;
	}

	public IPriceCandleInstant getUpper2() {
		return upper2;
	}

	public IPriceCandleInstant getLower1() {
		return lower1;
	}

	public IPriceCandleInstant getLower2() {
		return lower2;
	}

}
