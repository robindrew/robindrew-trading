package com.robindrew.trading.price.candle.line.formatter;

import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleLineFormatter {

	String formatCandle(IPriceCandle candle, boolean includeEndOfLine);

}
