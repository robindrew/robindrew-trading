package com.robindrew.trading.trade.balance;

import static com.robindrew.common.util.Check.notNull;

import com.robindrew.trading.trade.cash.Cash;
import com.robindrew.trading.trade.cash.ICash;

public class Balance {

	public static Balance fromCash(int amount) {
		return new Balance(new Cash(amount));
	}

	private volatile ICash balance;

	public Balance(ICash funds) {
		this.balance = new Cash(funds, true);
	}

	public ICash get() {
		return balance;
	}

	public void set(ICash balance) {
		this.balance = notNull("balance", balance);
	}

	public void add(ICash cash) {
		this.balance = get().add(cash);
	}

	public void subtract(ICash cash) {
		this.balance = get().subtract(cash);
	}

	@Override
	public String toString() {
		return balance.toString();
	}

}
