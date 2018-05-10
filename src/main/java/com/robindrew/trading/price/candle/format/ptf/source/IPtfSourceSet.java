package com.robindrew.trading.price.candle.format.ptf.source;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedSet;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;

public interface IPtfSourceSet {

	/**
	 * Returns the available days of prices.
	 * @return the available days of prices.
	 */
	SortedSet<LocalDate> getDays();

	/**
	 * Returns the underyling instrument.
	 * @return the underyling instrument.
	 */
	IInstrument getInstrument();

	/**
	 * Returns all the existing sources for the given instrument.
	 * @return the sources.
	 */
	Set<? extends IPtfSource> getSources();

	/**
	 * Returns all the existing sources for the given instrument.
	 * @param from the date to get sources from.
	 * @param to the date to get sources to.
	 * @return the sources.
	 */
	Set<? extends IPtfSource> getSources(LocalDateTime from, LocalDateTime to);

	/**
	 * Returns the source for the given instrument and month.
	 * @param date the date.
	 * @return the source.
	 */
	IPtfSource getSource(LocalDate date);

	/**
	 * Returns the source for the given instrument and month.
	 * @param month the month.
	 * @param create true to create the source if it does not exist.
	 * @return the source.
	 */
	IPtfSource getSource(LocalDate date, boolean create);

	IPriceCandleStreamSource asStreamSource(LocalDateTime from, LocalDateTime to);

}
