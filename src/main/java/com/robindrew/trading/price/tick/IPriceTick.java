package com.robindrew.trading.price.tick;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.decimal.IDecimal;

public interface IPriceTick extends IPriceCandle {

	int getBidPrice();

	int getAskPrice();

	int getMidPrice();

	int getDecimalPlaces();

	long getTimestamp();

	IDecimal getBid();

	IDecimal getAsk();

	IDecimal getMid();

}
