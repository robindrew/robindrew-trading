package com.robindrew.trading.price.tick.io.stream.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.price.tick.IPriceTick;

public class PriceTickLoggedStreamSource implements IPriceTickStreamSource {

	private static final Logger log = LoggerFactory.getLogger(PriceTickLoggedStreamSource.class);

	private final IPriceTickStreamSource source;
	private final int frequency;

	private int counter = 0;

	public PriceTickLoggedStreamSource(IPriceTickStreamSource source, int frequency) {
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
	public IPriceTick getNextTick() {
		IPriceTick next = source.getNextTick();
		if (next == null) {
			return next;
		}

		// Log the tick ...
		counter++;
		if (counter % frequency == 0) {
			log.info("#" + counter + " " + next);
		}

		return next;
	}

}
