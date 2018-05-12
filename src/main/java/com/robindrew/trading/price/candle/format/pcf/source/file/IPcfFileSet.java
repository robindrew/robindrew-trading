package com.robindrew.trading.price.candle.format.pcf.source.file;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceSet;

public interface IPcfFileSet extends IPcfSourceSet {

	/**
	 * Returns all the existing sources for the given instrument.
	 * @return the sources.
	 */
	@Override
	Set<? extends IPcfFile> getSources();

	/**
	 * Returns all the existing sources for the given instrument.
	 * @param from the date to get sources from.
	 * @param to the date to get sources to.
	 * @return the sources.
	 */
	@Override
	Set<? extends IPcfFile> getSources(LocalDateTime from, LocalDateTime to);

	/**
	 * Returns the source for the given instrument and month.
	 * @param month the month.
	 * @return the source.
	 */
	@Override
	IPcfFile getSource(LocalDate month);

	/**
	 * Returns the source for the given instrument and month.
	 * @param month the month.
	 * @param create true to create the source if it does not exist.
	 * @return the source.
	 */
	@Override
	IPcfFile getSource(LocalDate month, boolean create);

}
