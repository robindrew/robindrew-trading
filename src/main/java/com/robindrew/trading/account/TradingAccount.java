package com.robindrew.trading.account;

import java.math.BigDecimal;

import com.robindrew.common.util.Check;

public class TradingAccount implements ITradingAccount {

	private final String id;
	private final BigDecimal balance;

	public TradingAccount(String id, BigDecimal balance) {
		this.id = Check.notEmpty("id", id);
		this.balance = Check.notNull("balance", balance);;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public BigDecimal getBalance() {
		return balance;
	}

	@Override
	public String toString() {
		return "TradingAccount[id=" + id + ", balance=" + balance + "]";
	}

}
