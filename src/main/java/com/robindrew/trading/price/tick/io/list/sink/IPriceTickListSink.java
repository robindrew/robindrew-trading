package com.robindrew.trading.price.tick.io.list.sink;

import java.util.List;

import com.robindrew.common.io.INamedCloseable;
import com.robindrew.trading.price.tick.IPriceTick;

public interface IPriceTickListSink extends INamedCloseable {

	void putNextTicks(List<IPriceTick> ticks);

}
