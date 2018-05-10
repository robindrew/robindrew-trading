package com.robindrew.trading.price.candle.format.ptf.source.file;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedSet;

import com.robindrew.trading.price.candle.format.ptf.source.IPtfSourceSet;

public interface IPtfFileSet extends IPtfSourceSet {

	/**
	 * Returns all the existing sources for the given instrument.
	 * @return the sources.
	 */
	@Override
	SortedSet<IPtfFile> getSources();

	/**
	 * Returns all the existing sources for the given instrument.
	 * @param from the date to get sources from.
	 * @param to the date to get sources to.
	 * @return the sources.
	 */
	@Override
	Set<IPtfFile> getSources(LocalDateTime from, LocalDateTime to);

	/**
	 * Returns the source for the given instrument and month.
	 * @param month the month.
	 * @return the source.
	 */
	@Override
	IPtfFile getSource(LocalDate month);

	/**
	 * Returns the source for the given instrument and month.
	 * @param month the month.
	 * @param create true to create the source if it does not exist.
	 * @return the source.
	 */
	@Override
	IPtfFile getSource(LocalDate month, boolean create);

}
