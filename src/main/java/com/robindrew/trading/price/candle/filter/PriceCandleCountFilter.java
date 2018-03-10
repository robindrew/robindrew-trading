package com.robindrew.trading.price.candle.filter;

import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleCountFilter implements IPriceCandleFilter {

	private int count;

	public PriceCandleCountFilter(int count) {
		if (count < 0) {
			throw new IllegalArgumentException("count=" + count);
		}
		this.count = count;
	}

	@Override
	public boolean accept(IPriceCandle candle) {
		if (count > 0) {
			count--;
			return true;
		}
		return false;
	}

}
