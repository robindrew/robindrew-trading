package com.robindrew.trading.trade.window;

import java.time.LocalDateTime;

import com.robindrew.trading.price.candle.IPriceCandle;

public interface ITradingWindow {

	boolean contains(LocalDateTime date);

	boolean contains(IPriceCandle candle);

}
