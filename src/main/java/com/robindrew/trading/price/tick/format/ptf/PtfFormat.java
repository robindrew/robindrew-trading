package com.robindrew.trading.price.tick.format.ptf;

import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import com.google.common.io.PatternFilenameFilter;
import com.robindrew.trading.price.candle.format.pcf.PcfFormat;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.interval.PriceIntervals;
import com.robindrew.trading.price.tick.IPriceTick;

/**
 * The PTF (PriceTickFormat) file format is much faster to read than traditional text formats. The file size is similar
 * to or better than the compressed line format, but ticks are deserialized directly without decompression. Read and
 * write times are also very fast, however as a binary format it is not human readable.
 * <ul>
 * <li>Tick times are in UTC.</li>
 * <li>Files are monthly.</li>
 * </ul>
 * @see PcfFormat
 */
public class PtfFormat {

	public static final FilenameFilter FILENAME_FILTER = new PatternFilenameFilter(".+\\.ptf");
	public static final String FILE_EXTENSION = ".ptf";
	public static final IPriceInterval MONTHLY = PriceIntervals.MONTHLY;
	public static final DateTimeFormatter MONTH_FORMATTER = buildDateTimeFormatter();

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

	public static final String getFilename(LocalDate date) {
		return date.format(MONTH_FORMATTER) + FILE_EXTENSION;
	}

	public static boolean isPtfFile(File file) {
		return file.getName().endsWith(FILE_EXTENSION);
	}

	public static final LocalDate getMonth(File file) {
		return getMonth(file.getName());
	}

	public static final LocalDate getMonth(String filename) {
		int index = filename.lastIndexOf(FILE_EXTENSION);
		return LocalDate.parse(filename.substring(0, index), MONTH_FORMATTER);
	}

	public static final LocalDate getMonth(IPriceTick tick) {
		return getLocalDateTime(tick).toLocalDate();
	}

	private static final LocalDateTime getLocalDateTime(IPriceTick tick) {
		return MONTHLY.getDateTime(tick);
	}

	public static final long getNormalizedMonth(IPriceTick tick) {
		return MONTHLY.getTimePeriod(tick);
	}

}
