package com.robindrew.trading.price.tick.streaming;

import java.util.Optional;

import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.trade.TradeDirection;

/**
 * An immutable price snapshot. References the previous price (if available). Direction is {@link TradeDirection#BUY} if
 * previous price not available.
 */
public interface IPriceTickSnapshot {

	long getTimestamp();

	Optional<IPriceTick> getPrevious();

	IPriceTick getLatest();

	TradeDirection getDirection();

}
