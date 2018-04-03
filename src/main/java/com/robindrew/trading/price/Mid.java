package com.robindrew.trading.price;

import java.math.BigDecimal;

public class Mid {

	private static final BigDecimal TWO = new BigDecimal(2);

	public static final long getMid(long bid, long ask) {
		if (bid == ask) {
			return bid;
		}
		if (bid < ask) {
			return bid + ((ask - bid) / 2);
		} else {
			return ask + ((bid - ask) / 2);
		}
	}

	public static final int getMid(int bid, int ask) {
		if (bid == ask) {
			return bid;
		}
		if (bid < ask) {
			return bid + ((ask - bid) / 2);
		} else {
			return ask + ((bid - ask) / 2);
		}
	}

	public static final float getMid(float bid, float ask) {
		if (bid == ask) {
			return bid;
		}
		if (bid < ask) {
			return bid + ((ask - bid) / 2);
		} else {
			return ask + ((bid - ask) / 2);
		}
	}

	public static final double getMid(double bid, double ask) {
		if (bid == ask) {
			return bid;
		}
		if (bid < ask) {
			return bid + ((ask - bid) / 2);
		} else {
			return ask + ((bid - ask) / 2);
		}
	}

	public static final BigDecimal getMid(BigDecimal bid, BigDecimal ask) {
		if (bid.equals(ask)) {
			return bid;
		}
		if (bid.compareTo(ask) < 0) {
			return ask.subtract(bid).divide(TWO).add(bid);
		} else {
			return bid.subtract(ask).divide(TWO).add(ask);
		}
	}

	private Mid() {
	}
}
