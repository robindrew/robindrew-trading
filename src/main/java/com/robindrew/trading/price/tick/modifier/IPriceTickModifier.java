package com.robindrew.trading.price.tick.modifier;

import com.robindrew.trading.price.tick.IPriceTick;

public interface IPriceTickModifier {

	IPriceTick modify(IPriceTick tick);

}
