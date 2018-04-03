package com.robindrew.trading.price.tick.checker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.price.tick.IPriceTick;

public class PriceTickRangeChecker implements IPriceTickChecker {

	private static final Logger log = LoggerFactory.getLogger(PriceTickRangeChecker.class);

	private final int minPrice;
	private final int maxPrice;

	private boolean logFailure = true;
	private boolean throwFailure = true;

	public PriceTickRangeChecker(int minPrice, int maxPrice) {
		if (minPrice < 1) {
			throw new IllegalArgumentException("minPrice=" + minPrice);
		}
		if (maxPrice <= minPrice) {
			throw new IllegalArgumentException("minPrice=" + minPrice + ", maxPrice=" + maxPrice);
		}
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
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
	public boolean check(IPriceTick previous, IPriceTick current) {
		return check(current);
	}

	public boolean check(IPriceTick tick) {
		if (!check(tick, tick.getOpenPrice())) {
			return false;
		}
		if (!check(tick, tick.getHighPrice())) {
			return false;
		}
		if (!check(tick, tick.getLowPrice())) {
			return false;
		}
		if (!check(tick, tick.getClosePrice())) {
			return false;
		}
		return true;
	}

	private boolean check(IPriceTick tick, int price) {
		if (!isValid(price)) {
			String message = "Tick price not within range (" + minPrice + " to " + maxPrice + "): " + tick;
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

	public boolean isValid(int price) {
		return minPrice <= price && price <= maxPrice;
	}

}
