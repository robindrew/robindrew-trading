package com.robindrew.trading.price.tick.line.parser;

import com.robindrew.trading.price.tick.IPriceTick;

public interface IPriceTickLineParser {

	boolean skipLine(String line);

	IPriceTick parseTick(String line);

}
