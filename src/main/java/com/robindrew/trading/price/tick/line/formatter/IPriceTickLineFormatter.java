package com.robindrew.trading.price.tick.line.formatter;

import com.robindrew.trading.price.tick.IPriceTick;

public interface IPriceTickLineFormatter {

	String formatTick(IPriceTick tick, boolean includeEndOfLine);

}
