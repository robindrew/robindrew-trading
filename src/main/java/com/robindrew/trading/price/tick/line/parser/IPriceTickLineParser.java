package com.robindrew.trading.price.tick.line.parser;

import java.nio.charset.Charset;

import com.robindrew.trading.price.tick.IPriceTick;

public interface IPriceTickLineParser {

	Charset getCharset();
	
	boolean skipLine(String line);

	IPriceTick parseTick(String line);

}
