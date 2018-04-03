package com.robindrew.trading.price.tick.checker;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.PriceTickDateComparator;

public class PriceTickSortedChecker implements IPriceTickChecker {

	private static final Logger log = LoggerFactory.getLogger(PriceTickSortedChecker.class);

	private final Comparator<IPriceTick> comparator;
	private boolean logFailure = true;
	private boolean throwFailure = true;

	public PriceTickSortedChecker(Comparator<IPriceTick> comparator) {
		if (comparator == null) {
			throw new NullPointerException("comparator");
		}
		this.comparator = comparator;
	}

	public PriceTickSortedChecker() {
		this(new PriceTickDateComparator());
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
	public boolean check(IPriceTick tick1, IPriceTick tick2) {

		// Oh dear ...
		if (comparator.compare(tick1, tick2) > 0) {
			String message = "Ticks not sorted, tick1=" + tick1 + ", tick2=" + tick2;
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
