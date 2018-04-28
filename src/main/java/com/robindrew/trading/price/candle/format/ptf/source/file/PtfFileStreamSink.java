package com.robindrew.trading.price.candle.format.ptf.source.file;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.format.ptf.PtfFormat;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public class PtfFileStreamSink implements IPriceCandleStreamSink {

	private static final Logger log = LoggerFactory.getLogger(PtfFileStreamSink.class);
	
	private final File directory;
	private boolean writePartialFile = false;

	private final List<IPriceCandle> candles = new ArrayList<>();
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
		if (writePartialFile && !candles.isEmpty()) {
			writePtfFile(previousDay);
		}
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {

		LocalDate nextDay = PtfFormat.getDay(candle);
		if (previousDay == null || previousDay.equals(nextDay)) {
			candles.add(candle);
		}

		else {
			writePtfFile(previousDay);
		}

		previousDay = nextDay;
	}

	private void writePtfFile(LocalDate day) {
		if (candles.isEmpty()) {
			return;
		}

		String filename = PtfFormat.getFilename(day);
		PtfFile file = new PtfFile(new File(directory, filename), day);
		file.write(candles);
		log.info("Wrote File: {}", file.getFile());

		candles.clear();
	}

}
