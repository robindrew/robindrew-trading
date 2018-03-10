package com.robindrew.trading.price.candle.io.stream.source.iterator;

import java.util.Iterator;

import com.robindrew.common.util.Java;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;

public class PriceCandleStreamSourceIterator implements Iterator<IPriceCandle> {

	private final IPriceCandleStreamSource source;

	private boolean finished = false;
	private IPriceCandle next = null;

	public PriceCandleStreamSourceIterator(IPriceCandleStreamSource source) {
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
				next = source.getNextCandle();
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
	public IPriceCandle next() {
		if (finished) {
			throw new IllegalStateException("Do not call next() if hasNext() returns false, no more candles");
		}
		if (next == null) {
			throw new IllegalStateException("Do not call next() before hasNext()");
		}
		IPriceCandle candle = next;
		next = null;
		return candle;
	}

}
