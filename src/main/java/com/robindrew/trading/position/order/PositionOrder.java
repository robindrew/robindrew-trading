package com.robindrew.trading.position.order;

import java.math.BigDecimal;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.trade.TradeDirection;

public class PositionOrder implements IPositionOrder {

	private final IInstrument instrument;
	private final TradeDirection direction;
	private final CurrencyCode tradeCurrency;
	private final BigDecimal tradeSize;
	private final int stopLossDistance;
	private final int profitLimitDistance;

	public PositionOrder(IInstrument instrument, TradeDirection direction, CurrencyCode tradeCurrency, BigDecimal tradeSize, int stopLossDistance, int profitLimitDistance) {
		this.instrument = instrument;
		this.direction = direction;
		this.tradeCurrency = tradeCurrency;
		this.tradeSize = tradeSize;
		this.stopLossDistance = stopLossDistance;
		this.profitLimitDistance = profitLimitDistance;
	}

	@Override
	public IInstrument getInstrument() {
		return instrument;
	}

	@Override
	public TradeDirection getDirection() {
		return direction;
	}

	@Override
	public CurrencyCode getTradeCurrency() {
		return tradeCurrency;
	}

	@Override
	public BigDecimal getTradeSize() {
		return tradeSize;
	}

	@Override
	public int getStopLossDistance() {
		return stopLossDistance;
	}

	@Override
	public int getProfitLimitDistance() {
		return profitLimitDistance;
	}

}
