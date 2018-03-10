package com.robindrew.trading.price.candle.line.parser;

import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleLineParser {

	boolean skipLine(String line);

	IPriceCandle parseCandle(String line);

}
