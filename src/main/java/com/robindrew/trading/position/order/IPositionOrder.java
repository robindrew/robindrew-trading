package com.robindrew.trading.position.order;

import java.math.BigDecimal;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.trade.TradeDirection;

public interface IPositionOrder {

	IInstrument getInstrument();

	TradeDirection getDirection();

	CurrencyCode getTradeCurrency();

	BigDecimal getTradeSize();

	int getProfitLimitDistance();

	int getStopLossDistance();

}
