package com.robindrew.trading.price.candle.io.stream.source.iterator;

import static com.robindrew.common.util.Check.notNull;

import java.util.Iterator;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;

public class PriceCandleStreamSourceIterator implements Iterator<IPriceCandle> {

	private final IPriceCandleStreamSource wrapped;
	private IPriceCandle nextCandle = null;
	private boolean finished = false;

	public PriceCandleStreamSourceIterator(IPriceCandleStreamSource wrapped) {
		this.wrapped = notNull("wrapped", wrapped);
	}

	@Override
	public boolean hasNext() {
		if (finished) {
			return false;
		}

		// Already have next candle ready?
		if (nextCandle != null) {
			return true;
		}

		// Prepare next candle
		nextCandle = wrapped.getNextCandle();
		if (nextCandle != null) {
			return true;
		}

		// No more candles, finished.
		finished = true;
		return false;
	}

	@Override
	public IPriceCandle next() {
		if (!hasNext()) {
			throw new IllegalStateException("No more candles");
		}
		IPriceCandle next = nextCandle;
		nextCandle = null;
		return next;
	}

}
