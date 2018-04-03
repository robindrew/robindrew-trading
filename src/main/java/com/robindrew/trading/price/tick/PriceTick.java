package com.robindrew.trading.price.tick;

import org.junit.runner.notification.RunListener.ThreadSafe;

@ThreadSafe
public class PriceTick extends AbstractPriceTick {

	private final int bidPrice;
	private final int askPrice;
	private final long timestamp;
	private final byte decimalPlaces;

	public PriceTick(int bidPrice, int askPrice, long timestamp, int decimalPlaces) {
		if (bidPrice <= 0) {
			throw new IllegalArgumentException("bidPrice=" + bidPrice);
		}
		if (askPrice <= 0) {
			throw new IllegalArgumentException("askPrice=" + askPrice);
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

}
