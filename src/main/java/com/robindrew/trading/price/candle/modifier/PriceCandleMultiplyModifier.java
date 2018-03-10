package com.robindrew.trading.price.candle.modifier;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandle;

/**
 * Multiply all candle prices by the given positive non-zero multiplier.
 */
public class PriceCandleMultiplyModifier implements IPriceCandleModifier {

	private static final int[] MULTIPLY_BY = { 1, 10, 100, 1000, 10000, 100000, 1000000 };

	private final int multiplyBy;
	private final int decimalPlaces;

	public PriceCandleMultiplyModifier(int decimalPlaces) {
		if (decimalPlaces < 0 || decimalPlaces > 6) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}
		this.decimalPlaces = decimalPlaces;
		this.multiplyBy = MULTIPLY_BY[decimalPlaces];
	}

	@Override
	public IPriceCandle modify(IPriceCandle candle) {
		if (multiplyBy == 1) {
			return candle;
		}

		long openTime = candle.getOpenTime();
		long closeTime = candle.getCloseTime();

		int open = candle.getOpenPrice() * multiplyBy;
		int high = candle.getHighPrice() * multiplyBy;
		int low = candle.getLowPrice() * multiplyBy;
		int close = candle.getClosePrice() * multiplyBy;

		return new PriceCandle(open, high, low, close, openTime, closeTime, candle.getDecimalPlaces() + decimalPlaces);
	}

}
