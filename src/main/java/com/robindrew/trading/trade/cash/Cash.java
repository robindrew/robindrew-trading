package com.robindrew.trading.trade.cash;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Cash implements ICash {

	private static final DecimalFormat FORMAT = new DecimalFormat("0.00");

	private final BigDecimal cash;
	private final boolean positiveOnly;

	public Cash(BigDecimal cash, boolean positiveOnly) {
		this.cash = cash;
		this.positiveOnly = positiveOnly;

		if (positiveOnly && !isPositive()) {
			throw new IllegalArgumentException("cash is negative: " + cash);
		}
	}

	public Cash(ICash cash, boolean positiveOnly) {
		this(cash.get(), positiveOnly);
	}

	public Cash(int cash, boolean positiveOnly) {
		this(new BigDecimal(cash), positiveOnly);
	}

	public Cash(int cash) {
		this(cash, true);
	}

	public Cash(BigDecimal cash) {
		this(cash, true);
	}

	@Override
	public String toString() {
		return FORMAT.format(cash);
	}

	@Override
	public BigDecimal get() {
		return cash;
	}

	@Override
	public boolean isPositive() {
		return cash.doubleValue() >= 0.0;
	}

	@Override
	public ICash multiply(BigDecimal amount) {
		if (amount.doubleValue() <= 0.0) {
			throw new IllegalArgumentException("amount is negative: " + amount);
		}
		return new Cash(cash.multiply(amount), positiveOnly);
	}

	@Override
	public ICash divide(BigDecimal amount) {
		if (amount.doubleValue() <= 0.0) {
			throw new IllegalArgumentException("amount is negative: " + amount);
		}
		return new Cash(cash.divide(amount), positiveOnly);
	}

	@Override
	public ICash add(BigDecimal amount) {
		if (amount.doubleValue() < 0.0) {
			throw new IllegalArgumentException("amount is negative: " + amount);
		}
		return new Cash(cash.add(amount), positiveOnly);
	}

	@Override
	public ICash subtract(BigDecimal amount) {
		if (amount.doubleValue() < 0.0) {
			throw new IllegalArgumentException("amount is negative: " + amount);
		}
		return new Cash(cash.subtract(amount), positiveOnly);
	}

	@Override
	public ICash multiply(ICash cash) {
		return multiply(cash.get());
	}

	@Override
	public ICash divide(ICash cash) {
		return divide(cash.get());
	}

	@Override
	public ICash add(ICash cash) {
		return add(cash.get());
	}

	@Override
	public ICash subtract(ICash cash) {
		return subtract(cash.get());
	}

	@Override
	public ICash negate() {
		return new Cash(cash.negate(), positiveOnly);
	}

}
