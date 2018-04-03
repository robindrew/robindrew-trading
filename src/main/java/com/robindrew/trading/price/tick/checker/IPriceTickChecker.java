package com.robindrew.trading.price.tick.checker;

import com.robindrew.trading.price.tick.IPriceTick;

public interface IPriceTickChecker {

	boolean check(IPriceTick previous, IPriceTick current);

}
