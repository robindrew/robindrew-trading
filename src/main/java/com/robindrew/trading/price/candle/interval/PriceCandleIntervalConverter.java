package com.robindrew.trading.price.candle.interval;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.util.Java;
import com.robindrew.trading.price.candle.checker.PriceCandleSanityChecker;
import com.robindrew.trading.price.candle.io.line.sink.FileLineSink;
import com.robindrew.trading.price.candle.io.stream.PriceCandleStreamPipe;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;
import com.robindrew.trading.price.candle.io.stream.sink.PriceCandleLineStreamSink;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleCheckerStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleDirectoryStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleIntervalStreamSource;
import com.robindrew.trading.price.candle.line.filter.ILineFilter;
import com.robindrew.trading.price.candle.line.parser.IPriceCandleLineParser;

public class PriceCandleIntervalConverter {

	private static final Logger log = LoggerFactory.getLogger(PriceCandleIntervalConverter.class);

	private final IPriceCandleLineParser parser;
	private final ILineFilter filter;
	private IPriceCandleInterval interval = PriceCandleIntervals.MINUTELY;

	public PriceCandleIntervalConverter(IPriceCandleLineParser parser, ILineFilter filter) {
		if (parser == null) {
			throw new NullPointerException("parser");
		}
		if (filter == null) {
			throw new NullPointerException("filter");
		}
		this.parser = parser;
		this.filter = filter;
	}

	public boolean convert(File sourceDirectory, File destinationFile, double maxPercentDiff) {
		if (destinationFile.exists()) {
			log.warn("File already exists: " + destinationFile);
			return false;
		}

		// Convert!
		try (IPriceCandleStreamSource files = new PriceCandleDirectoryStreamSource(sourceDirectory, parser, filter)) {
			try (IPriceCandleStreamSource intervalSource = new PriceCandleIntervalStreamSource(files, interval)) {
				try (IPriceCandleStreamSource checkerSource = new PriceCandleCheckerStreamSource(intervalSource, new PriceCandleSanityChecker(maxPercentDiff))) {
					try (IPriceCandleStreamSink sink = new PriceCandleLineStreamSink(new FileLineSink(destinationFile))) {
						new PriceCandleStreamPipe(checkerSource, sink).pipe();
						return true;
					}
				}
			}
		}

		// Attempt to clean away partially written files
		catch (Exception e) {
			if (destinationFile.exists()) {
				destinationFile.delete();
			}
			throw Java.propagate(e);
		}
	}
}
