package com.robindrew.trading.price.tick.io.stream.source;

import com.robindrew.common.io.INamedCloseable;
import com.robindrew.trading.price.tick.IPriceTick;

public interface IPriceTickStreamSource extends INamedCloseable {

	IPriceTick getNextTick();

}
