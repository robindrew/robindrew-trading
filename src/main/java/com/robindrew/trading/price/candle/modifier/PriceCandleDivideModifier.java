package com.robindrew.trading.price.candle.modifier;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandle;

/**
 * Divide all candle prices by the given positive non-zero divisor.
 */
public class PriceCandleDivideModifier implements IPriceCandleModifier {

	private static final int[] DIVIDE_BY = { 1, 10, 100, 1000, 10000, 100000, 1000000 };

	private final int divideBy;
	private final int decimalPlaces;

	public PriceCandleDivideModifier(int decimalPlaces) {
		if (decimalPlaces < 0 || decimalPlaces > 6) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}
		this.decimalPlaces = decimalPlaces;
		this.divideBy = DIVIDE_BY[decimalPlaces];
	}

	@Override
	public IPriceCandle modify(IPriceCandle candle) {
		if (divideBy == 1) {
			return candle;
		}

		long openTime = candle.getOpenTime();
		long closeTime = candle.getCloseTime();

		int open = candle.getOpenPrice() / divideBy;
		int high = candle.getHighPrice() / divideBy;
		int low = candle.getLowPrice() / divideBy;
		int close = candle.getClosePrice() / divideBy;

		return new PriceCandle(open, high, low, close, openTime, closeTime, candle.getDecimalPlaces() - decimalPlaces);
	}

}
