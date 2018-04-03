package com.robindrew.trading.price.tick.modifier;

import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.PriceTick;

/**
 * Multiply all tick prices by the given positive non-zero multiplier.
 */
public class PriceTickMultiplyModifier implements IPriceTickModifier {

	private static final int[] MULTIPLY_BY = { 1, 10, 100, 1000, 10000, 100000, 1000000 };

	private final int multiplyBy;
	private final int decimalPlaces;

	public PriceTickMultiplyModifier(int decimalPlaces) {
		if (decimalPlaces < 0 || decimalPlaces > 6) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}
		this.decimalPlaces = decimalPlaces;
		this.multiplyBy = MULTIPLY_BY[decimalPlaces];
	}

	@Override
	public IPriceTick modify(IPriceTick tick) {
		if (multiplyBy == 1) {
			return tick;
		}

		long timestamp = tick.getTimestamp();

		int bid = tick.getBidPrice() * multiplyBy;
		int ask = tick.getAskPrice() * multiplyBy;

		return new PriceTick(bid, ask, timestamp, tick.getDecimalPlaces() + decimalPlaces);
	}

}
