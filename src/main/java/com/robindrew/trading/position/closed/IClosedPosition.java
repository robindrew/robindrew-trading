package com.robindrew.trading.position.closed;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.robindrew.trading.position.IPosition;

public interface IClosedPosition extends IPosition {

	LocalDateTime getCloseDate();

	BigDecimal getClosePrice();

	BigDecimal getProfit();

	BigDecimal getLoss();

	boolean isProfit();

	boolean isLoss();

}
