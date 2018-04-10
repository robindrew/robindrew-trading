package com.robindrew.trading.strategy;

import com.robindrew.trading.price.tick.IPriceTick;

/**
 * The {@link LatestPriceTradingStrategy} is an extension to the standard event-based trading strategy. The strategy
 * consumes price ticks while running in its own thread and examining price changes on a best-effort basis.
 */
public interface ILatestPriceTradingStrategy extends ITradingStrategy, Runnable {

	void handleLatestTick(IPriceTick tick);

}
