package com.robindrew.trading.price.candle.format.pcf.source;

import java.time.LocalDate;

import com.robindrew.trading.price.candle.format.IPriceSource;

public interface IPcfSource extends IPriceSource, Comparable<IPcfSource> {

	LocalDate getMonth();

}
