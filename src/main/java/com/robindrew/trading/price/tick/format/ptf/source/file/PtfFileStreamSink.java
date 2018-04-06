package com.robindrew.trading.price.tick.format.ptf.source.file;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.format.ptf.PtfFormat;
import com.robindrew.trading.price.tick.io.stream.sink.IPriceTickStreamSink;

public class PtfFileStreamSink implements IPriceTickStreamSink {

	private static final Logger log = LoggerFactory.getLogger(PtfFileStreamSink.class);

	private final File directory;
	private boolean writePartialFile = false;

	private final List<IPriceTick> ticks = new ArrayList<>();
	private LocalDate previousDay = null;

	public PtfFileStreamSink(File directory) {
		this.directory = Check.existsDirectory("directory", directory);
	}

	@Override
	public String getName() {
		return "PtfDirectoryStreamSink";
	}

	public void setWritePartialFile(boolean enable) {
		this.writePartialFile = enable;
	}

	@Override
	public void close() {
		if (writePartialFile && !ticks.isEmpty()) {
			writePtfFile(previousDay);
		}
	}

	@Override
	public void putNextTick(IPriceTick tick) {

		LocalDate nextDay = PtfFormat.getDay(tick);
		if (previousDay == null || previousDay.equals(nextDay)) {
			ticks.add(tick);
		}

		else {
			writePtfFile(previousDay);
		}

		previousDay = nextDay;
	}

	private void writePtfFile(LocalDate month) {
		if (ticks.isEmpty()) {
			return;
		}

		String filename = PtfFormat.getFilename(month);
		File file = new File(directory, filename);
		log.info("Writing Ticks to {}", file);
		PtfFile ptf = new PtfFile(file, month);
		ptf.write(ticks);

		ticks.clear();
	}

}
