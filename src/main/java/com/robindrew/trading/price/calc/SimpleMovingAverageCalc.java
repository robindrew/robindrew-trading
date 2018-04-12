package com.robindrew.trading.price.calc;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandles;

/**
 * Simple Moving Average (SMA).
 */
public class SimpleMovingAverageCalc extends AbstractStreamCalc {

	private long total = 0;
	private int count = 0;

	public SimpleMovingAverageCalc(int periods) {
		super(1, periods);
	}

	@Override
	protected void nextPrice(IPriceCandle price) {
		total += price.getMidClosePrice();
		count++;
	}

	public double getSimpleMovingAverage() {
		return PriceCandles.getAverage(total, count);
	}

}
