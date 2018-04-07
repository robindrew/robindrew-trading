package com.robindrew.trading.price.candle.line.parser;

import java.nio.charset.Charset;

import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleLineParser {

	Charset getCharset();

	boolean skipLine(String line);

	IPriceCandle parseCandle(String line);

}
