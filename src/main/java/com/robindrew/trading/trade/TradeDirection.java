package com.robindrew.trading.trade;

public enum TradeDirection {

	BUY(true), SELL(false);

	private final boolean buy;

	private TradeDirection(boolean buy) {
		this.buy = buy;
	}

	public boolean isBuy() {
		return buy;
	}

	public boolean isSell() {
		return !buy;
	}

	public TradeDirection invert() {
		return buy ? SELL : BUY;
	}

	public static TradeDirection getTradeType(double price, double stopLoss) {
		return stopLoss < price ? BUY : SELL;
	}

}
