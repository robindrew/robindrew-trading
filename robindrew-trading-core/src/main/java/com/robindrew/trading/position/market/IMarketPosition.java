package com.robindrew.trading.position.market;

import com.robindrew.trading.position.IPosition;
import java.math.BigDecimal;

public interface IMarketPosition extends IPosition {

    IMarketPrice getMarketPrice();

    BigDecimal getProfit();

    BigDecimal getLoss();

    boolean isProfit();

    boolean isLoss();
}
