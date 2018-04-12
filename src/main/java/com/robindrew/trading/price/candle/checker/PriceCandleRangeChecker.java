package com.robindrew.trading.price.candle.checker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleRangeChecker implements IPriceCandleChecker {

	private static final Logger log = LoggerFactory.getLogger(PriceCandleRangeChecker.class);

	private final int minPrice;
	private final int maxPrice;

	private boolean logFailure = true;
	private boolean throwFailure = true;

	public PriceCandleRangeChecker(int minPrice, int maxPrice) {
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
	public boolean check(IPriceCandle previous, IPriceCandle current) {
		return check(current);
	}

	public boolean check(IPriceCandle candle) {
		if (!check(candle, candle.getMidOpenPrice())) {
			return false;
		}
		if (!check(candle, candle.getMidHighPrice())) {
			return false;
		}
		if (!check(candle, candle.getMidLowPrice())) {
			return false;
		}
		if (!check(candle, candle.getMidClosePrice())) {
			return false;
		}
		return true;
	}

	private boolean check(IPriceCandle candle, int price) {
		if (!isValid(price)) {
			String message = "Candle price not within range (" + minPrice + " to " + maxPrice + "): " + candle;
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
