package com.robindrew.trading.price.candle.checker;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandleDateComparator;

public class PriceCandleSortedChecker implements IPriceCandleChecker {

	private static final Logger log = LoggerFactory.getLogger(PriceCandleSortedChecker.class);

	private final Comparator<IPriceCandle> comparator;
	private boolean logFailure = true;
	private boolean throwFailure = true;

	public PriceCandleSortedChecker(Comparator<IPriceCandle> comparator) {
		if (comparator == null) {
			throw new NullPointerException("comparator");
		}
		this.comparator = comparator;
	}

	public PriceCandleSortedChecker() {
		this(new PriceCandleDateComparator());
	}

	public boolean isLogFailure() {
		return logFailure;
	}

	public boolean isThrowFailure() {
		return throwFailure;
	}

	public void setLogFailure(boolean enable) {
		this.logFailure = enable;
	}

	public void setThrowFailure(boolean enable) {
		this.throwFailure = enable;
	}

	@Override
	public boolean check(IPriceCandle candle1, IPriceCandle candle2) {

		// Oh dear ...
		if (comparator.compare(candle1, candle2) > 0) {
			String message = "Candles not sorted, candle1=" + candle1 + ", candle2=" + candle2;
			if (logFailure) {
				log.warn(message);
			}
			if (throwFailure) {
				throw new IllegalStateException(message);
			}
			return false;
		}

		return true;
	}

}
