package com.robindrew.trading.price.candle.format.ptf.source.file;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.format.ptf.PtfFormat;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public class PtfFileStreamSink implements IPriceCandleStreamSink {

	private final File directory;
	private boolean writePartialFile = false;

	private final List<IPriceCandle> candles = new ArrayList<>();
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
		if (writePartialFile && !candles.isEmpty()) {
			writePtfFile(previousMonth);
		}
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {

		LocalDate nextMonth = PtfFormat.getDay(candle);
		if (previousMonth == null || previousMonth.equals(nextMonth)) {
			candles.add(candle);
		}

		else {
			writePtfFile(previousMonth);
		}

		previousMonth = nextMonth;
	}

	private void writePtfFile(LocalDate month) {
		if (candles.isEmpty()) {
			return;
		}

		String filename = PtfFormat.getFilename(month);
		PtfFile file = new PtfFile(new File(directory, filename), month);
		file.write(candles);

		candles.clear();
	}

}
