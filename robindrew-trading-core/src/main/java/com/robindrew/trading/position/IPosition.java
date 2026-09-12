package com.robindrew.trading.position;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.trade.TradeDirection;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IPosition {

    String getId();

    TradeDirection getDirection();

    LocalDateTime getOpenDate();

    CurrencyCode getTradeCurrency();

    BigDecimal getOpenPrice();

    BigDecimal getTradeSize();

    IInstrument getInstrument();

    BigDecimal getProfitLimitPrice();

    BigDecimal getStopLossPrice();
}
