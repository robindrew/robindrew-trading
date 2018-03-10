package com.robindrew.trading.price.candle.io.stream.source.iterator;

import java.util.Iterator;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;

public class PriceCandleStreamSourceIterable implements Iterable<IPriceCandle> {

	private final IPriceCandleStreamSource source;

	public PriceCandleStreamSourceIterable(IPriceCandleStreamSource source) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		this.source = source;
	}

	@Override
	public Iterator<IPriceCandle> iterator() {
		return new PriceCandleStreamSourceIterator(source);
	}

}
