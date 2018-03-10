package com.robindrew.trading.price.candle;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import org.junit.runner.notification.RunListener.ThreadSafe;

@ThreadSafe
public class PriceCandleInstant extends AbstractPriceCandle {

	private final int price;
	private final long instant;
	private final byte decimalPlaces;

	public PriceCandleInstant(int price, long instant, int decimalPlaces) {
		if (price <= 0) {
			throw new IllegalArgumentException("price=" + price);
		}
		if (decimalPlaces < 0 || decimalPlaces > 8) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}

		this.price = price;
		this.instant = instant;
		this.decimalPlaces = (byte) decimalPlaces;
	}

	@Override
	public int getOpenPrice() {
		return price;
	}

	@Override
	public int getClosePrice() {
		return price;
	}

	@Override
	public int getHighPrice() {
		return price;
	}

	@Override
	public int getLowPrice() {
		return price;
	}

	@Override
	public long getOpenTime() {
		return instant;
	}

	@Override
	public long getCloseTime() {
		return instant;
	}

	@Override
	public boolean hasClosedUp() {
		return false;
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PriceCandleInstant[");
		builder.append(getClosePrice()).append('|');
		builder.append(toLocalDateTime(getCloseTime())).append(']');
		return builder.toString();
	}

	@Override
	public int getDecimalPlaces() {
		return decimalPlaces;
	}
}
