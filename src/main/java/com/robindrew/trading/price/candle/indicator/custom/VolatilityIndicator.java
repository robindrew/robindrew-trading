package com.robindrew.trading.price.candle.indicator.custom;

import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.indicator.ValueIndicator;
import com.robindrew.trading.price.candle.interval.IPriceInterval;

/**
 * Volatility (VOL).
 * <p>
 * Calculated by adding the price range for each candle and dividing the total by the number of time periods.
 * </p>
 */
public class VolatilityIndicator extends ValueIndicator {

	public VolatilityIndicator(String name, IPriceInterval interval, int capacity) {
		super(name, interval, capacity);
	}

	@Override
	protected void calculate(List<IPriceCandle> candles) {

		long totalRange = 0;
		for (IPriceCandle candle : candles) {
			totalRange += candle.getHighLowRange();
		}
		setValue(totalRange / candles.size());
		setAvailable(getValue() > 0);
	}

}
