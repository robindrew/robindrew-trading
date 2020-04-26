package com.robindrew.trading.strategy;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.provider.ITradingInstrument;

/**
 * The {@link LatestPriceTradingStrategy} is an extension to the standard event-based trading strategy. The strategy
 * consumes price ticks while running in its own thread and examining price changes on a best-effort basis.
 */
public interface ILatestPriceTradingStrategy<I extends ITradingInstrument> extends ITradingStrategy<I>, Runnable {

	void handleLatestCandle(IPriceCandle candle);

}
