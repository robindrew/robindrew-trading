package com.robindrew.trading.price.candle.format.pcf.source.file;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.format.pcf.PcfFormat;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public class PcfFileStreamSink implements IPriceCandleStreamSink {

	private final File directory;
	private boolean writePartialFile = false;

	private final List<IPriceCandle> candles = new ArrayList<>();
	private LocalDate previousMonth = null;

	public PcfFileStreamSink(File directory) {
		this.directory = Check.existsDirectory("directory", directory);
	}

	@Override
	public String getName() {
		return "PcfFileStreamSink";
	}

	public void setWritePartialFile(boolean enable) {
		this.writePartialFile = enable;
	}

	@Override
	public void close() {
		if (writePartialFile && !candles.isEmpty()) {
			writePcfFile(previousMonth);
		}
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {

		LocalDate nextMonth = PcfFormat.getMonth(candle);
		if (previousMonth == null || previousMonth.equals(nextMonth)) {
			candles.add(candle);
		}

		else {
			writePcfFile(previousMonth);
		}

		previousMonth = nextMonth;
	}

	private void writePcfFile(LocalDate month) {
		if (candles.isEmpty()) {
			return;
		}

		String filename = PcfFormat.getFilename(month);
		PcfFile file = new PcfFile(new File(directory, filename), month);
		file.write(candles);

		candles.clear();
	}

}
