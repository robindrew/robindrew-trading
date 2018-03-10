package com.robindrew.trading.trade.window;

import java.time.LocalTime;

public interface ITradingHours {

	boolean contains(LocalTime time);

}
