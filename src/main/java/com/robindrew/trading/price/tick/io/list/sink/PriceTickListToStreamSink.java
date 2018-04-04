package com.robindrew.trading.price.tick.io.list.sink;

import java.util.List;

import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.io.stream.sink.IPriceTickStreamSink;

public class PriceTickListToStreamSink implements IPriceTickListSink {

	private final IPriceTickStreamSink sink;

	public PriceTickListToStreamSink(IPriceTickStreamSink sink) {
		if (sink == null) {
			throw new NullPointerException("sink");
		}
		this.sink = sink;
	}

	@Override
	public String getName() {
		return sink.getName();
	}

	@Override
	public void close() {
		sink.close();
	}

	@Override
	public void putNextTicks(List<IPriceTick> ticks) {
		for (IPriceTick tick : ticks) {
			sink.putNextTick(tick);
		}
	}

}
