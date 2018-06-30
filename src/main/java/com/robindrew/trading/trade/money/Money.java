package com.robindrew.trading.trade.money;

import static com.robindrew.common.util.Check.notNull;

import java.math.BigDecimal;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.common.util.Check;
import com.robindrew.trading.price.decimal.Decimal;
import com.robindrew.trading.price.decimal.IDecimal;

public class Money implements IMoney {

	private final CurrencyCode currency;
	private IDecimal value;

	public Money(IDecimal value, CurrencyCode currency) {
		this.value = Check.notNull("value", value);;
		this.currency = Check.notNull("currency", currency);
	}

	public Money(int value, CurrencyCode currency) {
		this(new Decimal(value, 0), currency);
	}

	public Money(BigDecimal value, CurrencyCode currency) {
		this(new Decimal(value), currency);
	}

	@Override
	public CurrencyCode getCurrency() {
		return currency;
	}

	@Override
	public IDecimal getValue() {
		return value;
	}

	public void setValue(IDecimal value) {
		this.value = notNull("value", value);
	}

	public void add(IDecimal value) {
		this.value = getValue().add(value);
	}

	public void subtract(IDecimal value) {
		this.value = getValue().subtract(value);
	}

	public void multiply(IDecimal value) {
		this.value = getValue().multiply(value);
	}

	@Override
	public String toString() {
		return currency + "[" + value + "]";
	}

	@Override
	public void add(BigDecimal value) {
		add(new Decimal(value));
	}

	@Override
	public void subtract(BigDecimal value) {
		subtract(new Decimal(value));
	}

	@Override
	public void multiply(BigDecimal value) {
		multiply(new Decimal(value));
	}

	@Override
	public void add(IMoney value) {
		if (!getCurrency().equals(value.getCurrency())) {
			throw new IllegalArgumentException("currencies do not match: this=" + this + ", value=" + value);
		}
		add(value.getValue());
	}

	@Override
	public void subtract(IMoney value) {
		if (!getCurrency().equals(value.getCurrency())) {
			throw new IllegalArgumentException("currencies do not match: this=" + this + ", value=" + value);
		}
		subtract(value.getValue());
	}

	@Override
	public void multiply(IMoney value) {
		if (!getCurrency().equals(value.getCurrency())) {
			throw new IllegalArgumentException("currencies do not match: this=" + this + ", value=" + value);
		}
		multiply(value.getValue());
	}

}
