package com.robindrew.trading.position.market;

import java.math.BigDecimal;

import com.robindrew.common.util.Check;

public class MarketPrice implements IMarketPrice {

	private static final BigDecimal TWO = new BigDecimal(2);

	private final BigDecimal bid;
	private final BigDecimal ask;

	public MarketPrice(BigDecimal mid) {
		this.bid = Check.notNull("mid", mid);
		this.ask = mid;
	}

	public MarketPrice(BigDecimal bid, BigDecimal ask) {
		this.bid = Check.notNull("bid", bid);
		this.ask = Check.notNull("ask", ask);

		// Sanity check
		if (bid.compareTo(ask) > 0) {
			throw new IllegalArgumentException("bid=" + bid + ", ask=" + ask);
		}
	}

	@Override
	public BigDecimal getBid() {
		return bid;
	}

	@Override
	public BigDecimal getMid() {
		if (bid.equals(ask)) {
			return bid;
		}
		return ask.subtract(bid).divide(TWO).add(bid);
	}

	@Override
	public BigDecimal getAsk() {
		return ask;
	}

}
