package com.robindrew.trading.price.candle.indicator;

import java.util.concurrent.atomic.AtomicLong;

import com.robindrew.trading.price.candle.interval.IPriceCandleInterval;

public abstract class ValueIndicator extends AbstractIndicator {

	private final AtomicLong value = new AtomicLong(0);

	protected ValueIndicator(String name, IPriceCandleInterval interval, int capacity) {
		super(name, interval, capacity);
	}

	public long getValue() {
		if (!isAvailable()) {
			throw new IllegalStateException("value not set");
		}
		return value.get();
	}

	protected void setValue(long value) {
		this.value.set(value);
		setAvailable(true);
	}

}
