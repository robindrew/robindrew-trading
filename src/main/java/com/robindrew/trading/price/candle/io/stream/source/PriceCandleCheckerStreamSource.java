package com.robindrew.trading.price.candle.io.stream.source;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.checker.IPriceCandleChecker;

public class PriceCandleCheckerStreamSource implements IPriceCandleStreamSource {

	private final IPriceCandleStreamSource source;
	private final IPriceCandleChecker checker;
	private IPriceCandle previous = null;

	public PriceCandleCheckerStreamSource(IPriceCandleStreamSource source, IPriceCandleChecker checker) {
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
	public IPriceCandle getNextCandle() {
		IPriceCandle next = source.getNextCandle();
		if (next == null) {
			return next;
		}

		// Apply the checker if we have a previous candle to check against
		if (previous != null) {
			checker.check(previous, next);
		}

		previous = next;
		return next;
	}

}
