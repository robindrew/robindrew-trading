package com.robindrew.trading.trade.funds;

public class AccountFunds {

	private volatile ICash funds;

	public AccountFunds(ICash funds) {
		this.funds = new Cash(funds, true);
	}

	public ICash getCash() {
		return funds;
	}

	public void addFunds(ICash cash) {
		this.funds = getCash().add(cash);
	}

	public void subtractFunds(ICash cash) {
		this.funds = getCash().subtract(cash);
	}

	@Override
	public String toString() {
		return funds.toString();
	}

}
