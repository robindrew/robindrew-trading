package com.robindrew.trading.platform.streaming.latest;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.trade.TradeDirection;

public interface ILatestPrice {

	IPriceCandle getPrice();

	TradeDirection getDirection();

	long getUpdateTime();

	long getUpdateCount();

}