package com.robindrew.trading.price.candle.io.list.sink;

import static com.google.common.base.Charsets.UTF_8;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.date.Dates;
import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.line.formatter.IPriceCandleLineFormatter;

public class DailyFilePriceCandleListConsumer implements IPriceCandleListSink {

	private static final Logger log = LoggerFactory.getLogger(DailyFilePriceCandleListConsumer.class);

	private final File directory;
	private final String filename;
	private final IPriceCandleLineFormatter formatter;

	public DailyFilePriceCandleListConsumer(File directory, String filename, IPriceCandleLineFormatter formatter) {
		this.directory = Check.existsDirectory("directory", directory);
		this.filename = Check.notEmpty("filename", filename);
		this.formatter = Check.notNull("formatter", formatter);
	}

	public File getDirectory() {
		return directory;
	}

	@Override
	public void putNextCandles(List<IPriceCandle> candles) {
		if (candles.isEmpty()) {
			return;
		}

		LocalDate date = getDate(candles);
		writeCandles(candles, date);
	}

	private LocalDate getDate(List<IPriceCandle> candles) {
		IPriceCandle first = candles.get(0);
		return Dates.toLocalDateTime(first.getOpenTime()).toLocalDate();
	}

	private void writeCandles(List<IPriceCandle> candles, LocalDate date) {
		String lines = toLines(candles);

		// We push all the lines out in one single write
		// Hopefully this will minimise write time and avoid partial writes if
		// the application crashes
		File file = getFile(date);
		if (!file.exists()) {
			log.info("Writing candles to file: {}", filename);
		}
		boolean append = true;
		try {
			try (FileOutputStream output = new FileOutputStream(file, append)) {
				output.write(lines.getBytes(UTF_8));
			}
		} catch (Exception e) {
			log.warn("Failed to write candles to file: " + filename, e);
		}
	}

	private File getFile(LocalDate date) {
		return new File(directory, filename + "." + date + ".txt");
	}

	private String toLines(List<IPriceCandle> candles) {
		StringBuilder lines = new StringBuilder();
		for (IPriceCandle candle : candles) {
			lines.append(formatter.formatCandle(candle, true));
		}
		return lines.toString();
	}

	@Override
	public String getName() {
		return filename;
	}

	@Override
	public void close() {
		// Nothing to do
	}

}
