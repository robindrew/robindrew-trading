package com.robindrew.trading.price.candle.io.stream.source;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.filter.IPriceCandleFilter;

public class PriceCandleFilteredStreamSource implements IPriceCandleStreamSource {

	private final IPriceCandleStreamSource source;
	private final IPriceCandleFilter filter;

	public PriceCandleFilteredStreamSource(IPriceCandleStreamSource source, IPriceCandleFilter filter) {
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
	public IPriceCandle getNextCandle() {
		IPriceCandle next = source.getNextCandle();

		// NOTE: Do not use recursion, depth of the source is a complete unknown!
		while (next != null && !filter.accept(next)) {
			next = source.getNextCandle();
		}
		return next;
	}

}
