package com.robindrew.trading.price.tick.io.stream.source.iterator;

import java.util.Iterator;

import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.io.stream.source.IPriceTickStreamSource;

public class PriceTickStreamSourceIterable implements Iterable<IPriceTick> {

	private final IPriceTickStreamSource source;

	public PriceTickStreamSourceIterable(IPriceTickStreamSource source) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		this.source = source;
	}

	@Override
	public Iterator<IPriceTick> iterator() {
		return new PriceTickStreamSourceIterator(source);
	}

}
