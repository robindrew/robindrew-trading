package com.robindrew.trading.price.candle.format.ptf.source;

import java.time.LocalDate;
import java.util.List;

import com.robindrew.trading.price.candle.ITickPriceCandle;
import com.robindrew.trading.price.candle.format.IPriceSource;

public interface IPtfSource extends IPriceSource, Comparable<IPtfSource> {

	LocalDate getDay();

	@Override
	List<ITickPriceCandle> read();

}
