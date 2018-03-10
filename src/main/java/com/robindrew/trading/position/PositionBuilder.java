package com.robindrew.trading.position;

import static com.robindrew.trading.trade.TradeDirection.BUY;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.trade.TradeDirection;

public class PositionBuilder {

	private String id = null;
	private IInstrument instrument = null;
	private TradeDirection direction = null;
	private LocalDateTime openDate = null;
	private CurrencyCode currency = CurrencyCode.GBP;
	private BigDecimal openPrice = null;
	private BigDecimal profitLimitPrice = null;
	private BigDecimal stopLossPrice = null;
	private BigDecimal tradeSize = null;

	public PositionBuilder setId(String id) {
		this.id = Check.notEmpty("id", id);
		return this;
	}

	public PositionBuilder setInstrument(IInstrument instrument) {
		this.instrument = Check.notNull("instrument", instrument);
		return this;
	}

	public PositionBuilder setDirection(TradeDirection direction) {
		this.direction = Check.notNull("direction", direction);
		return this;
	}

	public PositionBuilder setCurrency(CurrencyCode currency) {
		this.currency = Check.notNull("currency", currency);
		return this;
	}

	public PositionBuilder setOpenDate(LocalDateTime date) {
		this.openDate = Check.notNull("date", date);
		return this;
	}

	public PositionBuilder setOpenPrice(BigDecimal price) {
		if (price.doubleValue() <= 0.0) {
			throw new IllegalArgumentException("price=" + price);
		}
		this.openPrice = Check.notNull("price", price);
		return this;
	}

	public PositionBuilder setStopLossPrice(BigDecimal price) {
		if (price.doubleValue() <= 0.0) {
			throw new IllegalArgumentException("price=" + price);
		}
		this.stopLossPrice = Check.notNull("price", price);
		return this;
	}

	public PositionBuilder setProfitLimitPrice(BigDecimal price) {
		if (price.doubleValue() <= 0.0) {
			throw new IllegalArgumentException("price=" + price);
		}
		this.profitLimitPrice = Check.notNull("price", price);
		return this;
	}

	public PositionBuilder setTradeSize(BigDecimal tradeSize) {
		if (tradeSize.doubleValue() <= 0.0) {
			throw new IllegalArgumentException("tradeSize=" + tradeSize);
		}
		this.tradeSize = Check.notNull("tradeSize", tradeSize);
		return this;
	}

	public IPosition build() {
		if (id == null) {
			throw new IllegalStateException("id not set");
		}
		if (instrument == null) {
			throw new IllegalStateException("instrument not set");
		}
		if (direction == null) {
			throw new IllegalStateException("direction not set");
		}
		if (openDate == null) {
			throw new IllegalStateException("openDate not set");
		}
		if (currency == null) {
			throw new IllegalStateException("currency not set");
		}
		if (openPrice == null) {
			throw new IllegalStateException("openPrice not set");
		}
		if (profitLimitPrice == null) {
			throw new IllegalStateException("profitLimitPrice not set");
		}
		if (stopLossPrice == null) {
			throw new IllegalStateException("openPrice not set");
		}
		if (tradeSize == null) {
			throw new IllegalStateException("tradeSize not set");
		}

		if (direction.equals(BUY)) {
			if (stopLossPrice.compareTo(openPrice) >= 0) {
				throw new IllegalStateException("openPrice=" + openPrice + ", direction=" + direction + ", stopLossPrice=" + stopLossPrice);
			}
			if (profitLimitPrice.compareTo(openPrice) <= 0) {
				throw new IllegalStateException("openPrice=" + openPrice + ", direction=" + direction + ", profitLimitPrice=" + profitLimitPrice);
			}
		} else {
			if (stopLossPrice.compareTo(openPrice) <= 0) {
				throw new IllegalStateException("openPrice=" + openPrice + ", direction=" + direction + ", stopLossPrice=" + stopLossPrice);
			}
			if (profitLimitPrice.compareTo(openPrice) >= 0) {
				throw new IllegalStateException("openPrice=" + openPrice + ", direction=" + direction + ", profitLimitPrice=" + profitLimitPrice);
			}
		}

		Position position = new Position();
		position.id = this.id;
		position.instrument = this.instrument;
		position.direction = this.direction;
		position.tradeSize = this.tradeSize;
		position.openDate = this.openDate;
		position.currency = this.currency;
		position.openPrice = this.openPrice;
		position.stopLossPrice = this.stopLossPrice;
		position.profitLimitPrice = this.profitLimitPrice;
		return position;
	}

	private class Position implements IPosition {

		private String id;
		private IInstrument instrument;
		private TradeDirection direction;
		private LocalDateTime openDate;
		private CurrencyCode currency;
		private BigDecimal openPrice;
		private BigDecimal tradeSize;
		private BigDecimal stopLossPrice;
		private BigDecimal profitLimitPrice;

		@Override
		public String getId() {
			return id;
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
		public LocalDateTime getOpenDate() {
			return openDate;
		}

		@Override
		public CurrencyCode getTradeCurrency() {
			return currency;
		}

		@Override
		public BigDecimal getOpenPrice() {
			return openPrice;
		}

		@Override
		public BigDecimal getTradeSize() {
			return tradeSize;
		}

		@Override
		public BigDecimal getProfitLimitPrice() {
			return profitLimitPrice;
		}

		@Override
		public BigDecimal getStopLossPrice() {
			return stopLossPrice;
		}

		@Override
		public String toString() {
			return getId() + "(" + getDirection() + " " + getTradeSize() + "@" + getOpenPrice() + ")";
		}
	}
}
