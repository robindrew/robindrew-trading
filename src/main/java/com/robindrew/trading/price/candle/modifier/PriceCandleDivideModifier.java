package com.robindrew.trading.price.candle.modifier;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.MidPriceCandle;

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

		int open = candle.getMidOpenPrice() / divideBy;
		int high = candle.getMidHighPrice() / divideBy;
		int low = candle.getMidLowPrice() / divideBy;
		int close = candle.getMidClosePrice() / divideBy;

		return new MidPriceCandle(open, high, low, close, openTime, closeTime, candle.getDecimalPlaces() - decimalPlaces);
	}

}
