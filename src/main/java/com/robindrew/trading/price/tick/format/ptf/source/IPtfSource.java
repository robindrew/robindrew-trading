package com.robindrew.trading.price.tick.format.ptf.source;

import java.time.LocalDate;
import java.util.List;

import com.robindrew.trading.price.tick.IPriceTick;

public interface IPtfSource extends Comparable<IPtfSource> {

	String getName();

	LocalDate getMonth();

	List<IPriceTick> read();

	void write(List<IPriceTick> candles);

	int size();

	boolean exists();

	boolean create();
}
