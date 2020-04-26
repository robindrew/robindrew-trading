package com.robindrew.trading.price.indicator;

import com.google.common.base.Optional;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.decimal.Decimal;
import com.robindrew.trading.price.decimal.Decimals;

/**
 * Exponential Moving Average (EMA).
 */
public class ExponentialMovingAverage implements IPriceCandleStreamIndicator<Decimal> {

	public static double getSmoothingConstant(int periods) {
		if (periods < 1) {
			throw new IllegalArgumentException("periods=" + periods);
		}
		return 2.0f / (periods + 1);
	}

	private static int nextExponentialMovingAverage(int nextPrice, int previousPrice, double smoothingConstant) {
		return Decimals.roundDoubleToInt(((nextPrice - previousPrice) * smoothingConstant) + previousPrice);
	}

	private final int periods;
	private final SimpleMovingAverage sma;
	private final double smoothing;

	private int latest = 0;
	private int decimalPlaces = 0;

	public ExponentialMovingAverage(int periods) {
		if (periods < 1) {
			throw new IllegalArgumentException("periods=" + periods);
		}
		this.periods = periods;
		this.sma = new SimpleMovingAverage(periods);
		this.smoothing = getSmoothingConstant(periods);
	}

	public int getPeriods() {
		return periods;
	}

	public double getSmoothingConstant() {
		return smoothing;
	}

	@Override
	public String getName() {
		return "ExponentialMovingAverage";
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		if (latest == 0) {

			// Our first EMA value is derived from the SMA
			sma.putNextCandle(candle);
			Optional<Decimal> indicator = sma.getIndicator();
			if (indicator.isPresent()) {
				Decimal value = indicator.get();
				latest = value.getValue();
				decimalPlaces = value.getDecimalPlaces();
				sma.close();
			}

		} else {
			if (decimalPlaces != candle.getDecimalPlaces()) {
				throw new IllegalArgumentException("expected " + decimalPlaces + " decimal places, candle=" + candle);
			}

			// Calculate next EMA value
			int closePrice = candle.getMidClosePrice();
			latest = nextExponentialMovingAverage(closePrice, latest, smoothing);
		}
	}

	@Override
	public void close() {
		sma.close();
	}

	@Override
	public Optional<Decimal> getIndicator() {
		if (latest == 0) {
			return Optional.absent();
		}
		return Optional.fromNullable(new Decimal(latest, decimalPlaces));
	}
}
