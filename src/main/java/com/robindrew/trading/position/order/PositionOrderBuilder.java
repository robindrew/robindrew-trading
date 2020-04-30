package com.robindrew.trading.position.order;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.trade.TradeDirection;

public class PositionOrderBuilder {

	public static PositionOrderBuilder orderBuilder() {
		return new PositionOrderBuilder();
	}
	
	private IInstrument instrument = null;
	private TradeDirection direction = null;
	private CurrencyCode tradeCurrency = null;
	private BigDecimal tradeSize = null;
	private BigDecimal profitLimitDistance = ZERO;
	private BigDecimal stopLossDistance = ZERO;

	public PositionOrderBuilder instrument(IInstrument instrument) {
		this.instrument = Check.notNull("instrument", instrument);
		return this;
	}

	public PositionOrderBuilder direction(TradeDirection direction) {
		this.direction = Check.notNull("direction", direction);
		return this;
	}

	public PositionOrderBuilder tradeCurrency(CurrencyCode currency) {
		this.tradeCurrency = Check.notNull("currency", currency);
		return this;
	}

	public PositionOrderBuilder stopLossDistance(BigDecimal distance) {
		this.stopLossDistance = Check.notNull("distance", distance);
		return this;
	}

	public PositionOrderBuilder profitLimitPrice(BigDecimal distance) {
		this.profitLimitDistance = Check.notNull("distance", distance);
		return this;
	}

	public PositionOrderBuilder tradeSize(long tradeSize) {
		return tradeSize(new BigDecimal(tradeSize));
	}

	public PositionOrderBuilder tradeSize(BigDecimal tradeSize) {
		if (tradeSize.doubleValue() <= 0.0) {
			throw new IllegalArgumentException("tradeSize=" + tradeSize);
		}
		this.tradeSize = Check.notNull("tradeSize", tradeSize);
		return this;
	}

	public IPositionOrder build() {
		if (instrument == null) {
			throw new IllegalStateException("instrument not set");
		}
		if (direction == null) {
			throw new IllegalStateException("direction not set");
		}
		if (tradeCurrency == null) {
			throw new IllegalStateException("tradeCurrency not set");
		}
		if (profitLimitDistance == null) {
			throw new IllegalStateException("profitLimitPrice not set");
		}
		if (stopLossDistance == null) {
			throw new IllegalStateException("openPrice not set");
		}
		if (tradeSize == null) {
			throw new IllegalStateException("tradeSize not set");
		}

		return new PositionOrder(instrument, direction, tradeCurrency, tradeSize, stopLossDistance, profitLimitDistance);
	}
}
