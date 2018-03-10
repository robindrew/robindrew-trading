package com.robindrew.trading.price.candle.io.stream.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleLoggedStreamSource implements IPriceCandleStreamSource {

	private static final Logger log = LoggerFactory.getLogger(PriceCandleLoggedStreamSource.class);

	private final IPriceCandleStreamSource source;
	private final int frequency;

	private int counter = 0;

	public PriceCandleLoggedStreamSource(IPriceCandleStreamSource source, int frequency) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (frequency < 1) {
			throw new IllegalArgumentException("frequency=" + frequency);
		}
		this.source = source;
		this.frequency = frequency;
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
		if (next == null) {
			return next;
		}

		// Log the candle ...
		counter++;
		if (counter % frequency == 0) {
			log.info("#" + counter + " " + next);
		}

		return next;
	}

}
