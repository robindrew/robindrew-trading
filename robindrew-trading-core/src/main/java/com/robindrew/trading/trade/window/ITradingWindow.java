package com.robindrew.trading.trade.window;

import com.robindrew.trading.price.candle.IPriceCandle;
import java.time.LocalDateTime;

public interface ITradingWindow {

    boolean contains(LocalDateTime date);

    boolean contains(IPriceCandle candle);
}
