package com.robindrew.trading.position.closed;

import com.robindrew.trading.position.IPosition;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IClosedPosition extends IPosition {

    LocalDateTime getCloseDate();

    BigDecimal getClosePrice();

    BigDecimal getProfit();

    BigDecimal getLoss();

    boolean isProfit();

    boolean isLoss();
}
