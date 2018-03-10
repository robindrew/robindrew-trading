package com.robindrew.trading.price.candle.format.pcf.source;

import java.time.LocalDate;
import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPcfSource extends Comparable<IPcfSource> {

	String getName();

	LocalDate getMonth();

	List<IPriceCandle> read();

	void write(List<IPriceCandle> candles);

	int size();

	boolean exists();

	boolean create();
}
