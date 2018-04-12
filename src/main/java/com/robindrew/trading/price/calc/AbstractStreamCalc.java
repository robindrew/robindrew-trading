package com.robindrew.trading.price.calc;

import com.robindrew.trading.price.candle.IPriceCandle;

public abstract class AbstractStreamCalc implements IPriceCandleStreamCalc {

	private final int minPrices;
	private final int maxPrices;

	public AbstractStreamCalc(int minPrices, int maxPrices) {
		if (minPrices < 1) {
			throw new IllegalArgumentException("minPrices=" + minPrices);
		}
		if (maxPrices < minPrices) {
			throw new IllegalArgumentException("minPrices=" + minPrices + ", maxPrices=" + maxPrices);
		}
		this.minPrices = minPrices;
		this.maxPrices = maxPrices;
	}

	@Override
	public int getMinPrices() {
		return minPrices;
	}

	@Override
	public int getMaxPrices() {
		return maxPrices;
	}

	@Override
	public void next(IPriceCandle price, int index) {
		if (index >= getMaxPrices()) {
			return;
		}
		nextPrice(price);
	}

	protected abstract void nextPrice(IPriceCandle price);

}
