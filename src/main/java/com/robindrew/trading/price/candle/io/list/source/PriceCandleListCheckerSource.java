package com.robindrew.trading.price.candle.io.list.source;

import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.checker.IPriceCandleChecker;

public class PriceCandleListCheckerSource implements IPriceCandleListSource {

	private final IPriceCandleListSource source;
	private final IPriceCandleChecker checker;

	public PriceCandleListCheckerSource(IPriceCandleListSource source, IPriceCandleChecker checker) {
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
	public List<IPriceCandle> getNextCandles() {
		List<IPriceCandle> candles = source.getNextCandles();

		IPriceCandle previous = null;
		for (IPriceCandle next : candles) {
			if (previous != null) {
				checker.check(previous, next);
			}
			previous = next;
		}
		return candles;
	}

}
