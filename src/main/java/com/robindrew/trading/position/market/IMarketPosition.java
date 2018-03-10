package com.robindrew.trading.position.market;

import java.math.BigDecimal;

import com.robindrew.trading.position.IPosition;

public interface IMarketPosition extends IPosition {

	IMarketPrice getMarketPrice();

	BigDecimal getProfit();

	BigDecimal getLoss();

	boolean isProfit();

	boolean isLoss();

}
