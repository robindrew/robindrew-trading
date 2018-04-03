package com.robindrew.trading.price.tick.io.stream.source;

import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.checker.IPriceTickChecker;

public class PriceTickCheckerStreamSource implements IPriceTickStreamSource {

	private final IPriceTickStreamSource source;
	private final IPriceTickChecker checker;
	private IPriceTick previous = null;

	public PriceTickCheckerStreamSource(IPriceTickStreamSource source, IPriceTickChecker checker) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (checker == null) {
			throw new NullPointerException("checker");
		}
		this.source = source;
		this.checker = checker;
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

		// Apply the checker if we have a previous tick to check against
		if (previous != null) {
			checker.check(previous, next);
		}

		previous = next;
		return next;
	}

}
