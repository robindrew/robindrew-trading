package com.robindrew.trading.price.candle.indicator.standard;

import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.indicator.ValueIndicator;
import com.robindrew.trading.price.candle.interval.IPriceCandleInterval;

/**
 * Simple Moving Average (SMA).
 * <p>
 * Calculated by adding the closing price of each candle and then dividing the total by the number of time periods.
 * </p>
 */
public class SmaIndicator extends ValueIndicator {

	public SmaIndicator(String name, IPriceCandleInterval interval, int capacity) {
		super(name, interval, capacity);
	}

	@Override
	protected void calculate(List<IPriceCandle> candles) {
		long total = 0;
		for (IPriceCandle candle : candles) {
			total += candle.getClosePrice();
		}
		setValue(total / candles.size());
	}

}
