package com.robindrew.trading.price.candle.format.pcf;

import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import com.google.common.io.PatternFilenameFilter;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.format.pif.PifFormat;
import com.robindrew.trading.price.candle.interval.IPriceCandleInterval;
import com.robindrew.trading.price.candle.interval.PriceCandleIntervals;

/**
 * The PCF (PriceCandleFormat) file format is much faster to read than traditional text formats. The file size is
 * similar to or better than the compressed line format, but candles are deserialized directly without decompression.
 * Read and write times are also very fast, however as a binary format it is not human readable.
 * <ul>
 * <li>Candles are minute-level accuracy.</li>
 * <li>Candle times are in UTC.</li>
 * <li>Files are monthly.</li>
 * </ul>
 * @see PifFormat
 */
public final class PcfFormat {

	public static final FilenameFilter FILENAME_FILTER = new PatternFilenameFilter(".+\\.pcf");
	public static final IPriceCandleInterval MONTHLY = PriceCandleIntervals.MONTHLY;
	public static final DateTimeFormatter FORMATTER = buildDateTimeFormatter();
	public static final String FILE_EXTENSION = ".pcf";

	public static final String getFilename(LocalDate date) {
		return date.format(FORMATTER) + FILE_EXTENSION;
	}

	public static boolean isPcfFile(File file) {
		return file.getName().endsWith(FILE_EXTENSION);
	}

	private static DateTimeFormatter buildDateTimeFormatter() {
		DateTimeFormatterBuilder format = new DateTimeFormatterBuilder();
		format.appendPattern("yyyy-MM");
		format.parseDefaulting(ChronoField.DAY_OF_MONTH, 1);
		format.parseDefaulting(ChronoField.HOUR_OF_DAY, 0);
		format.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0);
		format.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0);
		format.parseDefaulting(ChronoField.MILLI_OF_SECOND, 0);
		return format.toFormatter();
	}

	public static final LocalDate getMonth(File file) {
		return getMonth(file.getName());
	}

	public static final LocalDate getMonth(String filename) {
		int index = filename.lastIndexOf(FILE_EXTENSION);
		return LocalDate.parse(filename.substring(0, index), FORMATTER);
	}

	public static final LocalDate getMonth(IPriceCandle candle) {
		return getLocalDateTime(candle).toLocalDate();
	}

	private static final LocalDateTime getLocalDateTime(IPriceCandle candle) {
		return MONTHLY.getDateTime(candle);
	}

	public static final long getNormalizedMonth(IPriceCandle candle) {
		return MONTHLY.getTimePeriod(candle);
	}

	private PcfFormat() {
	}

}
