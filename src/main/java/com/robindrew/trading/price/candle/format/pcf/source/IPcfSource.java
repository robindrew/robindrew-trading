package com.robindrew.trading.price.candle.format.pcf.source;

import java.time.LocalDate;

import com.robindrew.trading.price.candle.format.IPriceCandleSource;

public interface IPcfSource extends IPriceCandleSource, Comparable<IPcfSource> {

	LocalDate getMonth();

}
