package com.robindrew.trading.trade;

public enum TradeDirection {

	BUY, SELL;

	public boolean isBuy() {
		return name().equals("BUY");
	}

	public boolean isSell() {
		return name().equals("SELL");
	}

	public TradeDirection invert() {
		return isBuy() ? SELL : BUY;
	}

	public static TradeDirection getTradeType(double price, double stopLoss) {
		return stopLoss < price ? BUY : SELL;
	}

}
