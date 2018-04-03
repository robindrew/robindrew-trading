package com.robindrew.trading.price.tick.filter;

import com.robindrew.trading.price.tick.IPriceTick;

@FunctionalInterface
public interface IPriceTickFilter {

	boolean accept(IPriceTick candle);

}
