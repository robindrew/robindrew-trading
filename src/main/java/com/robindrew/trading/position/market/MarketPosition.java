package com.robindrew.trading.position.market;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.Positions;
import com.robindrew.trading.trade.TradeDirection;

public class MarketPosition implements IMarketPosition {

	private final IPosition position;
	private final IMarketPrice marketPrice;

	public MarketPosition(IPosition position, IMarketPrice marketPrice) {
		this.position = Check.notNull("position", position);
		this.marketPrice = Check.notNull("marketPrice", marketPrice);
	}

	@Override
	public IMarketPrice getMarketPrice() {
		return marketPrice;
	}

	@Override
	public String getId() {
		return position.getId();
	}

	@Override
	public TradeDirection getDirection() {
		return position.getDirection();
	}

	@Override
	public LocalDateTime getOpenDate() {
		return position.getOpenDate();
	}

	@Override
	public CurrencyCode getTradeCurrency() {
		return position.getTradeCurrency();
	}

	@Override
	public BigDecimal getOpenPrice() {
		return position.getOpenPrice();
	}

	@Override
	public BigDecimal getTradeSize() {
		return position.getTradeSize();
	}

	@Override
	public IInstrument getInstrument() {
		return position.getInstrument();
	}

	@Override
	public BigDecimal getProfit() {
		return Positions.getProfit(position, marketPrice);
	}

	@Override
	public BigDecimal getLoss() {
		return Positions.getLoss(position, marketPrice);
	}

	@Override
	public boolean isProfit() {
		return Positions.isProfit(position, marketPrice);
	}

	@Override
	public boolean isLoss() {
		return !isProfit();
	}

	@Override
	public BigDecimal getProfitLimitPrice() {
		return position.getProfitLimitPrice();
	}

	@Override
	public BigDecimal getStopLossPrice() {
		return position.getStopLossPrice();
	}

}
