package com.robindrew.trading.price.candle.indicator.standard;

import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.indicator.AbstractIndicator;
import com.robindrew.trading.price.candle.interval.IPriceCandleInterval;

/**
 * An indicator that attempts to detect the formation of a Triangle pattern.
 */
public class TriangleIndicator extends AbstractIndicator {

	public TriangleIndicator(String name, IPriceCandleInterval interval, int capacity) {
		super(name, interval, capacity);
	}

	@Override
	public boolean isAvailable() {
		return false;
	}

	@Override
	protected void calculate(List<IPriceCandle> list) {

	}

}
