package com.robindrew.trading.price;

import java.math.BigDecimal;

public class Mid {

	private static final BigDecimal TWO = new BigDecimal(2);
	
	public static final float getMid(float bid, float ask) {

		// It is expected that ask will be more than bid ...
		if (ask > bid) {
			return ((ask - bid) / 2) + bid;
		}

		// but just in case!
		else {
			return ((bid - ask) / 2) + ask;
		}
	}

	public static final BigDecimal getMid(BigDecimal bid, BigDecimal ask) {

		// It is expected that ask will be more than bid ...
		if (ask.doubleValue() > bid.doubleValue()) {
			return ask.subtract(bid).divide(TWO).add(bid);
		}

		// but just in case!
		else {
			return bid.subtract(ask).divide(TWO).add(ask);
		}
	}

	private final long timestamp;
	private final float price;

	public Mid(float price, long timestamp) {
		if (price <= 0.0) {
			throw new IllegalArgumentException("price=" + price);
		}
		this.price = price;
		this.timestamp = timestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public float getPrice() {
		return price;
	}

}
