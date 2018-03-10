package com.robindrew.trading.price.close.calc;

import static com.robindrew.trading.price.candle.PriceCandles.getAverage;
import static com.robindrew.trading.price.candle.PriceCandles.getChange;

import com.robindrew.trading.price.close.IClosePrice;

/**
 * Relative Strength Index (RSI).
 */
public class RelativeStrengthIndexCalc extends AbstractStreamCalc {

	private long totalChange = 0;
	private long totalGain = 0;
	private long totalLoss = 0;
	private int count = 0;

	private IClosePrice previous = null;

	public RelativeStrengthIndexCalc(int periods) {
		super(1, periods);
	}

	@Override
	protected void nextPrice(IClosePrice price) {
		if (previous == null) {
			previous = price;
			return;
		}

		int change = getChange(previous, price);
		previous = price;

		count++;
		totalChange += change;
		if (change > 0) {
			totalGain += change;
		}
		if (change < 0) {
			totalLoss -= change;
		}
	}

	public int getCount() {
		return count;
	}

	public double getAverageChange() {
		return getAverage(totalChange, count);
	}

	public double getAverageGain() {
		return getAverage(totalGain, count);
	}

	public double getAverageLoss() {
		return getAverage(totalLoss, count);
	}

	public double getRelativeStrength() {
		return getAverageGain() / getAverageLoss();
	}

	public double getRelativeStrengthIndex() {
		return 100.0 - (100.0 / (1.0 + getRelativeStrength()));
	}
}
