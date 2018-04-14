package com.robindrew.trading.price.candle.format.ptf.source;

import java.time.LocalDate;
import java.util.List;

import com.robindrew.trading.price.candle.ITickPriceCandle;
import com.robindrew.trading.price.candle.format.IPriceCandleSource;

public interface IPtfSource extends IPriceCandleSource, Comparable<IPtfSource> {

	LocalDate getDay();

	@Override
	List<ITickPriceCandle> read();

}
