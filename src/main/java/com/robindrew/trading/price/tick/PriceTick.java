package com.robindrew.trading.price.tick;

import java.math.BigDecimal;

import com.robindrew.common.text.Strings;
import com.robindrew.trading.price.Mid;

public class PriceTick implements IPriceTick {

	private final long timestamp;
	private final BigDecimal ask;
	private final BigDecimal bid;

	public PriceTick(long timestamp, BigDecimal bid, BigDecimal ask) {
		if (timestamp <= 0) {
			throw new IllegalArgumentException("timestamp=" + timestamp);
		}
		this.timestamp = timestamp;
		this.bid = bid;
		this.ask = ask;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public BigDecimal getBid() {
		return bid;
	}

	@Override
	public BigDecimal getAsk() {
		return ask;
	}

	@Override
	public String toString() {
		return Strings.toString(this);
	}

	@Override
	public BigDecimal getMid() {
		return Mid.getMid(bid, ask);
	}

}
