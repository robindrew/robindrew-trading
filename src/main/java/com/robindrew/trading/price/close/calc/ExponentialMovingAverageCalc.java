package com.robindrew.trading.price.close.calc;

import static com.robindrew.trading.price.candle.PriceCandles.getAverage;
import static com.robindrew.trading.price.candle.PriceCandles.getSmoothingConstant;

import com.robindrew.trading.price.close.IClosePrice;

/**
 * Exponential Moving Average (EMA).
 */
public class ExponentialMovingAverageCalc extends AbstractStreamCalc {

	private final double smoothingConstant;
	private final int periods;

	private long total = 0;
	private int count = 0;
	private double movingAverage = 0;

	public ExponentialMovingAverageCalc(int periods) {
		super(1, periods * 2);
		this.periods = periods;
		this.smoothingConstant = getSmoothingConstant(periods);
	}

	@Override
	protected void nextPrice(IClosePrice price) {

		// Firstly we must do a Simple Moving Average
		if (count < periods) {
			count++;
			total += price.getClosePrice();
			movingAverage = getAverage(total, count);
			return;
		}

		// Then we apply the Exponential Moving Average
		movingAverage = ((price.getClosePrice() - movingAverage) * smoothingConstant) + movingAverage;
	}

	public double getExponentialMovingAverage() {
		return movingAverage;
	}

}
