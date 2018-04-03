package com.robindrew.trading.price.tick.io.list.source;

import java.util.List;

import com.robindrew.common.io.INamedCloseable;
import com.robindrew.trading.price.tick.IPriceTick;

public interface IPriceTickListSource extends INamedCloseable {

	List<IPriceTick> getNextTicks();

}
