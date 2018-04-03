package com.robindrew.trading.price.tick.io.list.filter;

import java.util.List;

import com.robindrew.trading.price.tick.IPriceTick;

@FunctionalInterface
public interface IPriceTickListFilter {

	List<IPriceTick> filter(List<IPriceTick> ticks);

}
