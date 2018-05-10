package com.robindrew.trading.price.candle.format;

import java.util.Collection;
import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceSource {

	String getName();

	PriceFormat getFormat();

	List<? extends IPriceCandle> read();

	void write(Collection<? extends IPriceCandle> candles);

	int size();

	boolean exists();

	boolean create();

}
