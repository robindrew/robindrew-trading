package com.robindrew.trading.price.candle.tick;

public class TickPriceCandleFactory implements ITickPriceCandleFactory {

	@Override
	public ITickPriceCandle create(int bidPrice, int askPrice, long timestamp, int decimalPlaces) {
		return new TickPriceCandle(bidPrice, askPrice, timestamp, decimalPlaces);
	}

	@Override
	public void release(ITickPriceCandle candle) {
		// Nothing to do
	}

}
