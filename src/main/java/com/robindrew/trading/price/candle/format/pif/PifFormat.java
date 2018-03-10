package com.robindrew.trading.price.candle.format.pif;

import java.io.File;
import java.io.FilenameFilter;

import com.google.common.io.PatternFilenameFilter;
import com.robindrew.trading.price.candle.format.pcf.PcfFormat;

/**
 * The PIF (PriceInstantFormat) file format is much faster to read than traditional text formats. The file size is similar
 * to or better than the compressed line format, but candles are deserialized directly without decompression. Read and
 * write times are also very fast, however as a binary format it is not human readable.
 * <ul>
 * <li>Candles are minute-level accuracy.</li>
 * <li>Candle times are in UTC.</li>
 * <li>Files are monthly.</li>
 * </ul>
 * @see PcfFormat
 */
public class PifFormat {

	public static final FilenameFilter FILENAME_FILTER = new PatternFilenameFilter(".+\\.pif");
	public static final String FILE_EXTENSION = ".pif";

	public static boolean isPtfFile(File file) {
		return file.getName().endsWith(FILE_EXTENSION);
	}
}
