package com.robindrew.trading.price.indicator;

import java.util.Deque;
import java.util.LinkedList;

import com.google.common.base.Optional;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.decimal.Decimal;
import com.robindrew.trading.price.decimal.Decimals;

/**
 * Relative Strength Index (RSI).
 */
public class RelativeStrengthIndex implements IPriceCandleStreamIndicator<Decimal> {

	public double getRelativeStrength(double averageGain, double averageLoss) {
		return averageGain / averageLoss;
	}

	public static double getRelativeStrengthIndex(double relativeStrength) {
		return 100.0 - (100.0 / (1.0 + relativeStrength));
	}

	private final int periods;
	private final Deque<IPriceCandle> candles = new LinkedList<>();
	private int capacity;
	private double averageGain = Double.NaN;
	private double averageLoss = Double.NaN;

	public RelativeStrengthIndex(int periods) {
		if (periods < 1) {
			throw new IllegalArgumentException("periods=" + periods);
		}
		this.periods = periods;
		this.capacity = periods + 1;
	}

	public int getPeriods() {
		return periods;
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		candles.addLast(candle);
		if (candles.size() > getCapacity()) {
			candles.removeFirst();
		}
	}

	@Override
	public String getName() {
		return "RelativeStrengthIndex";
	}

	@Override
	public void close() {
		candles.clear();
	}

	@Override
	public Optional<Decimal> getIndicator() {

		// Initial calculation
		if (Double.isNaN(averageGain)) {
			if (candles.size() < getCapacity()) {
				return Optional.absent();
			}

			int gain = 0;
			int loss = 0;
			int previous = 0;

			for (IPriceCandle candle : candles) {
				int next = candle.getMidClosePrice();
				if (previous > 0) {
					int change = next - previous;
					if (change >= 0) {
						gain += change;
					} else {
						loss -= change;
					}
				}
				previous = next;
			}

			averageGain = gain / periods;
			averageLoss = loss / periods;

			// Cleanup
			while (candles.size() > 1) {
				candles.removeFirst();
				capacity = 2;
			}
		}

		// All subsequent calculations
		else {

			int previous = candles.getFirst().getMidClosePrice();
			int next = candles.getLast().getMidClosePrice();

			int latestGain = 0;
			int latestLoss = 0;
			int change = next - previous;

			if (change >= 0) {
				latestGain = change;
			} else {
				latestLoss = -change;
			}

			averageGain = (averageGain * (periods - 1) + latestGain) / periods;
			averageLoss = (averageLoss * (periods - 1) + latestLoss) / periods;
		}

		double strength = getRelativeStrength(averageGain, averageLoss);
		double rsi = getRelativeStrengthIndex(strength);

		int value = Decimals.doubleToInt(rsi, 2, false);
		return Optional.of(new Decimal(value, 2));
	}
}
