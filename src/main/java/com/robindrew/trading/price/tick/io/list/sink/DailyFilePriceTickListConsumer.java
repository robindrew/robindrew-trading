package com.robindrew.trading.price.tick.io.list.sink;

import static com.google.common.base.Charsets.UTF_8;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.date.Dates;
import com.robindrew.common.util.Check;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.line.formatter.IPriceTickLineFormatter;

public class DailyFilePriceTickListConsumer implements IPriceTickListSink {

	private static final Logger log = LoggerFactory.getLogger(DailyFilePriceTickListConsumer.class);

	private final File directory;
	private final String filename;
	private final IPriceTickLineFormatter formatter;

	public DailyFilePriceTickListConsumer(File directory, String filename, IPriceTickLineFormatter formatter) {
		this.directory = Check.existsDirectory("directory", directory);
		this.filename = Check.notEmpty("filename", filename);
		this.formatter = Check.notNull("formatter", formatter);
	}

	public File getDirectory() {
		return directory;
	}

	@Override
	public void putNextTicks(List<IPriceTick> ticks) {
		if (ticks.isEmpty()) {
			return;
		}

		LocalDate date = getDate(ticks);
		writeTicks(ticks, date);
	}

	private LocalDate getDate(List<IPriceTick> ticks) {
		IPriceTick first = ticks.get(0);
		return Dates.toLocalDateTime(first.getOpenTime()).toLocalDate();
	}

	private void writeTicks(List<IPriceTick> ticks, LocalDate date) {
		String lines = toLines(ticks);

		// We push all the lines out in one single write
		// Hopefully this will minimise write time and avoid partial writes if
		// the application crashes
		File file = getFile(date);
		if (!file.exists()) {
			log.info("Writing ticks to file: {}", filename);
		}
		boolean append = true;
		try {
			try (FileOutputStream output = new FileOutputStream(file, append)) {
				output.write(lines.getBytes(UTF_8));
			}
		} catch (Exception e) {
			log.warn("Failed to write ticks to file: " + filename, e);
		}
	}

	private File getFile(LocalDate date) {
		return new File(directory, filename + "." + date + ".txt");
	}

	private String toLines(List<IPriceTick> ticks) {
		StringBuilder lines = new StringBuilder();
		for (IPriceTick tick : ticks) {
			lines.append(formatter.formatTick(tick, true));
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
