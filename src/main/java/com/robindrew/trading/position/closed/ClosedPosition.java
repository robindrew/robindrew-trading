package com.robindrew.trading.position.closed;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.common.text.Strings;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.position.Positions;
import com.robindrew.trading.position.market.MarketPrice;
import com.robindrew.trading.trade.TradeDirection;

public class ClosedPosition implements IClosedPosition {

	private final IPosition position;
	private final LocalDateTime closeDate;
	private final BigDecimal closePrice;

	public ClosedPosition(IPosition position, LocalDateTime closeDate, BigDecimal closePrice) {
		this.position = Check.notNull("position", position);
		this.closeDate = Check.notNull("closeDate", closeDate);
		this.closePrice = Check.notNull("closePrice", closePrice);
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
	public BigDecimal getProfitLimitPrice() {
		return position.getProfitLimitPrice();
	}

	@Override
	public BigDecimal getStopLossPrice() {
		return position.getStopLossPrice();
	}

	@Override
	public LocalDateTime getCloseDate() {
		return closeDate;
	}

	@Override
	public BigDecimal getClosePrice() {
		return closePrice;
	}

	@Override
	public BigDecimal getProfit() {
		return Positions.getProfit(position, new MarketPrice(closePrice));
	}

	@Override
	public BigDecimal getLoss() {
		return Positions.getLoss(position, new MarketPrice(closePrice));
	}

	@Override
	public boolean isProfit() {
		return Positions.isProfit(position, new MarketPrice(closePrice));
	}

	@Override
	public boolean isLoss() {
		return !isProfit();
	}

	@Override
	public String toString() {
		return Strings.toString(this);
	}

}
