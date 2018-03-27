package com.robindrew.trading.platform.streaming.latest;

import java.util.Optional;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.trade.TradeDirection;

/**
 * An immutable price snapshot. References the previous price (if available). Direction is {@link TradeDirection#BUY} if
 * previous price not available.
 */
public interface IPriceSnapshot {

	long getTimestamp();

	Optional<IPriceCandle> getPrevious();

	IPriceCandle getLatest();

	TradeDirection getDirection();

}
