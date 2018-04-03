package com.robindrew.trading.price.tick.filter;

import com.robindrew.trading.price.tick.IPriceTick;

public class PriceTickCountFilter implements IPriceTickFilter {

	private int count;

	public PriceTickCountFilter(int count) {
		if (count < 0) {
			throw new IllegalArgumentException("count=" + count);
		}
		this.count = count;
	}

	@Override
	public boolean accept(IPriceTick tick) {
		if (count > 0) {
			count--;
			return true;
		}
		return false;
	}

}
