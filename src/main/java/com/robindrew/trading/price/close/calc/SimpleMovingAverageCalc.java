package com.robindrew.trading.price.close.calc;

import com.robindrew.trading.price.candle.PriceCandles;
import com.robindrew.trading.price.close.IClosePrice;

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
	protected void nextPrice(IClosePrice price) {
		total += price.getClosePrice();
		count++;
	}

	public double getSimpleMovingAverage() {
		return PriceCandles.getAverage(total, count);
	}

}
