package com.robindrew.trading.price.candle.tick;


public interface ITickPriceCandleFactory {

	ITickPriceCandle create(int bidPrice, int askPrice, long timestamp, int decimalPlaces);
	
	void release(ITickPriceCandle candle);
}
