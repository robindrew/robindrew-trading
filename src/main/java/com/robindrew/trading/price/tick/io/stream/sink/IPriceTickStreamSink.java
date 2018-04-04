package com.robindrew.trading.price.tick.io.stream.sink;

import com.robindrew.common.io.INamedCloseable;
import com.robindrew.trading.price.tick.IPriceTick;

public interface IPriceTickStreamSink extends INamedCloseable {

	void putNextTick(IPriceTick tick);
	
}
