package com.robindrew.trading.price.candle;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import org.junit.runner.notification.RunListener.ThreadSafe;

@ThreadSafe
public class PriceCandleInstant extends AbstractPriceCandle implements IPriceCandleInstant {

	private final int bidPrice;
	private final int askPrice;
	private final long timestamp;
	private final byte decimalPlaces;

	public PriceCandleInstant(int bidPrice, int askPrice, long timestamp, int decimalPlaces) {
		if (bidPrice <= 0) {
			throw new IllegalArgumentException("bidPrice=" + bidPrice);
		}
		if (askPrice <= 0) {
			throw new IllegalArgumentException("askPrice=" + askPrice);
		}
		if (askPrice < bidPrice) {
			throw new IllegalArgumentException("bidPrice=" + bidPrice + ", askPrice=" + askPrice);
		}
		if (decimalPlaces < 0 || decimalPlaces > 8) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}

		this.bidPrice = bidPrice;
		this.askPrice = askPrice;
		this.timestamp = timestamp;
		this.decimalPlaces = (byte) decimalPlaces;
	}

	@Override
	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	@Override
	public int getMidPrice() {
		if (bidPrice == askPrice) {
			return bidPrice;
		} else {
			return bidPrice + ((askPrice - bidPrice) / 2);
		}
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public int getBidPrice() {
		return bidPrice;
	}

	@Override
	public int getAskPrice() {
		return askPrice;
	}

	@Override
	public int getOpenPrice() {
		return getMidPrice();
	}

	@Override
	public int getClosePrice() {
		return getMidPrice();
	}

	@Override
	public int getHighPrice() {
		return getMidPrice();
	}

	@Override
	public int getLowPrice() {
		return getMidPrice();
	}

	@Override
	public long getOpenTime() {
		return timestamp;
	}

	@Override
	public long getCloseTime() {
		return timestamp;
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
		builder.append(getBidPrice()).append('|');
		builder.append(getAskPrice()).append('|');
		builder.append(toLocalDateTime(getCloseTime())).append(']');
		return builder.toString();
	}

}
