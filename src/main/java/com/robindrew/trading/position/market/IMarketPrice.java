package com.robindrew.trading.position.market;

import java.math.BigDecimal;

public interface IMarketPrice {

	BigDecimal getBid();

	BigDecimal getMid();

	BigDecimal getAsk();

}
