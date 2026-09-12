package com.robindrew.trading.position.order;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.trade.TradeDirection;
import java.math.BigDecimal;

public interface IPositionOrder {

    IInstrument getInstrument();

    TradeDirection getDirection();

    CurrencyCode getTradeCurrency();

    BigDecimal getTradeSize();

    BigDecimal getProfitLimitDistance();

    BigDecimal getStopLossDistance();
}
