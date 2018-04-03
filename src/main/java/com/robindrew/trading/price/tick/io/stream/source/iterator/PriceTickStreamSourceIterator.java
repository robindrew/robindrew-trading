package com.robindrew.trading.price.tick.io.stream.source.iterator;

import java.util.Iterator;

import com.robindrew.common.util.Java;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.io.stream.source.IPriceTickStreamSource;

public class PriceTickStreamSourceIterator implements Iterator<IPriceTick> {

	private final IPriceTickStreamSource source;

	private boolean finished = false;
	private IPriceTick next = null;

	public PriceTickStreamSourceIterator(IPriceTickStreamSource source) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		this.source = source;
	}

	@Override
	public boolean hasNext() {
		if (finished) {
			return false;
		}
		if (next == null) {

			try {
				next = source.getNextTick();
			} catch (Exception e) {
				handleException(e);
				next = null;
			}

			if (next == null) {
				finished = true;
				return false;
			}
		}
		return true;
	}

	protected void handleException(Exception e) {
		throw Java.propagate(e);
	}

	@Override
	public IPriceTick next() {
		if (finished) {
			throw new IllegalStateException("Do not call next() if hasNext() returns false, no more ticks");
		}
		if (next == null) {
			throw new IllegalStateException("Do not call next() before hasNext()");
		}
		IPriceTick tick = next;
		next = null;
		return tick;
	}

}
