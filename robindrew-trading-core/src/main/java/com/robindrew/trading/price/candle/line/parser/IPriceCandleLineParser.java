package com.robindrew.trading.price.candle.line.parser;

import com.robindrew.trading.price.candle.IPriceCandle;
import java.nio.charset.Charset;

public interface IPriceCandleLineParser {

    Charset getCharset();

    boolean skipLine(String line);

    IPriceCandle parseCandle(String line);
}
