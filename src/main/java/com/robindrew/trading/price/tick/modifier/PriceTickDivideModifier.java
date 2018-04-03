package com.robindrew.trading.price.tick.modifier;

import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.PriceTick;

/**
 * Divide all tick prices by the given positive non-zero divisor.
 */
public class PriceTickDivideModifier implements IPriceTickModifier {

	private static final int[] DIVIDE_BY = { 1, 10, 100, 1000, 10000, 100000, 1000000 };

	private final int divideBy;
	private final int decimalPlaces;

	public PriceTickDivideModifier(int decimalPlaces) {
		if (decimalPlaces < 0 || decimalPlaces > 6) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}
		this.decimalPlaces = decimalPlaces;
		this.divideBy = DIVIDE_BY[decimalPlaces];
	}

	@Override
	public IPriceTick modify(IPriceTick tick) {
		if (divideBy == 1) {
			return tick;
		}

		long timestamp = tick.getTimestamp();

		int bid = tick.getBidPrice() / divideBy;
		int ask = tick.getAskPrice() / divideBy;

		return new PriceTick(bid, ask, timestamp, tick.getDecimalPlaces() - decimalPlaces);
	}

}
