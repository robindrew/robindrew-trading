package com.robindrew.trading.position.order;

import java.math.BigDecimal;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.common.text.Strings;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.trade.TradeDirection;

public class PositionOrder implements IPositionOrder {

	private final IInstrument instrument;
	private final TradeDirection direction;
	private final CurrencyCode tradeCurrency;
	private final BigDecimal tradeSize;
	private final BigDecimal stopLossDistance;
	private final BigDecimal profitLimitDistance;

	public PositionOrder(IInstrument instrument, TradeDirection direction, CurrencyCode tradeCurrency, BigDecimal tradeSize, BigDecimal stopLossDistance, BigDecimal profitLimitDistance) {
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
	public BigDecimal getStopLossDistance() {
		return stopLossDistance;
	}

	@Override
	public BigDecimal getProfitLimitDistance() {
		return profitLimitDistance;
	}

	@Override
	public String toString() {
		return Strings.toString(this);
	}

}
