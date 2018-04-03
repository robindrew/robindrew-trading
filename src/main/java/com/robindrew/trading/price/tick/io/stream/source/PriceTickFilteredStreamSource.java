package com.robindrew.trading.price.tick.io.stream.source;

import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.filter.IPriceTickFilter;

public class PriceTickFilteredStreamSource implements IPriceTickStreamSource {

	private final IPriceTickStreamSource source;
	private final IPriceTickFilter filter;

	public PriceTickFilteredStreamSource(IPriceTickStreamSource source, IPriceTickFilter filter) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (filter == null) {
			throw new NullPointerException("filter");
		}
		this.source = source;
		this.filter = filter;
	}

	@Override
	public String getName() {
		return source.getName();
	}

	@Override
	public void close() {
		source.close();
	}

	@Override
	public IPriceTick getNextTick() {
		IPriceTick next = source.getNextTick();

		// NOTE: Do not use recursion, depth of the source is a complete unknown!
		while (next != null && !filter.accept(next)) {
			next = source.getNextTick();
		}
		return next;
	}

}
