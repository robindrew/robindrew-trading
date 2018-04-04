package com.robindrew.trading.price.tick.format.ptf.source.file;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.format.ptf.PtfFormat;
import com.robindrew.trading.price.tick.io.stream.sink.IPriceTickStreamSink;

public class PtfFileStreamSink implements IPriceTickStreamSink {

	private final File directory;
	private boolean writePartialFile = false;

	private final List<IPriceTick> ticks = new ArrayList<>();
	private LocalDate previousMonth = null;

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
			writePtfFile(previousMonth);
		}
	}

	@Override
	public void putNextTick(IPriceTick tick) {

		LocalDate nextMonth = PtfFormat.getMonth(tick);
		if (previousMonth == null || previousMonth.equals(nextMonth)) {
			ticks.add(tick);
		}

		else {
			writePtfFile(previousMonth);
		}

		previousMonth = nextMonth;
	}

	private void writePtfFile(LocalDate month) {
		if (ticks.isEmpty()) {
			return;
		}

		String filename = PtfFormat.getFilename(month);
		PtfFile file = new PtfFile(new File(directory, filename), month);
		file.write(ticks);

		ticks.clear();
	}

}
