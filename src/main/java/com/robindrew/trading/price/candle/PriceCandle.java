package com.robindrew.trading.price.candle;

import org.junit.runner.notification.RunListener.ThreadSafe;

@ThreadSafe
public class PriceCandle extends AbstractPriceCandle {

	private final int openPrice;
	private final int highPrice;
	private final int lowPrice;
	private final int closePrice;
	private final long openTime;
	private final long closeTime;
	private final byte decimalPlaces;

	public PriceCandle(int openPrice, int highPrice, int lowPrice, int closePrice, long openTime, long closeTime, int decimalPlaces) {
		if (openPrice <= 0) {
			throw new IllegalArgumentException("openPrice=" + openPrice);
		}
		if (highPrice <= 0) {
			throw new IllegalArgumentException("highPrice=" + highPrice);
		}
		if (lowPrice <= 0) {
			throw new IllegalArgumentException("lowPrice=" + lowPrice);
		}
		if (closePrice <= 0) {
			throw new IllegalArgumentException("closePrice=" + closePrice);
		}

		// Sanity checks
		if (highPrice < openPrice) {
			throw new IllegalArgumentException("highPrice=" + highPrice + ", openPrice=" + openPrice);
		}
		if (highPrice < closePrice) {
			throw new IllegalArgumentException("highPrice=" + highPrice + ", closePrice=" + closePrice);
		}
		if (lowPrice > openPrice) {
			throw new IllegalArgumentException("lowPrice=" + lowPrice + ", openPrice=" + openPrice);
		}
		if (lowPrice > closePrice) {
			throw new IllegalArgumentException("lowPrice=" + lowPrice + ", closePrice=" + closePrice);
		}
		if (openTime > closeTime) {
			throw new IllegalArgumentException("openTime=" + openTime + ", closeTime=" + closeTime);
		}
		if (decimalPlaces < 0 || decimalPlaces > 6) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}

		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.decimalPlaces = (byte) decimalPlaces;
	}

	@Override
	public int getOpenPrice() {
		return openPrice;
	}

	@Override
	public int getClosePrice() {
		return closePrice;
	}

	@Override
	public int getHighPrice() {
		return highPrice;
	}

	@Override
	public int getLowPrice() {
		return lowPrice;
	}

	@Override
	public long getOpenTime() {
		return openTime;
	}

	@Override
	public long getCloseTime() {
		return closeTime;
	}

	@Override
	public int getDecimalPlaces() {
		return decimalPlaces;
	}

}
