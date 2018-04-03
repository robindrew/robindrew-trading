package com.robindrew.trading.strategy.triangle;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.tick.IPriceTick;

public class TriangleBreakoutParameters {

	private final IPriceInterval interval;
	private final IPriceTick upper1;
	private final IPriceTick upper2;
	private final IPriceTick lower1;
	private final IPriceTick lower2;

	public TriangleBreakoutParameters(IPriceInterval interval, IPriceTick upper1, IPriceTick upper2, IPriceTick lower1, IPriceTick lower2) {
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

	public IPriceInterval getInterval() {
		return interval;
	}

	public IPriceTick getUpper1() {
		return upper1;
	}

	public IPriceTick getUpper2() {
		return upper2;
	}

	public IPriceTick getLower1() {
		return lower1;
	}

	public IPriceTick getLower2() {
		return lower2;
	}

}
