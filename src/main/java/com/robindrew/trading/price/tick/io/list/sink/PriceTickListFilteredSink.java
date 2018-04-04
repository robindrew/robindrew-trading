package com.robindrew.trading.price.tick.io.list.sink;

import java.util.List;

import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.io.list.filter.IPriceTickListFilter;

public class PriceTickListFilteredSink implements IPriceTickListSink {

	private final IPriceTickListSink sink;
	private final IPriceTickListFilter filter;

	public PriceTickListFilteredSink(IPriceTickListSink sink, IPriceTickListFilter filter) {
		if (sink == null) {
			throw new NullPointerException("sink");
		}
		if (filter == null) {
			throw new NullPointerException("filter");
		}
		this.sink = sink;
		this.filter = filter;
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
		ticks = filter.filter(ticks);
		sink.putNextTicks(ticks);
	}

}
