package com.robindrew.trading.price.candle.io;

import static com.robindrew.common.text.Strings.number;
import static com.robindrew.trading.price.candle.io.stream.sink.PriceCandleDataStreamSink.createFileDataSink;
import static com.robindrew.trading.price.candle.io.stream.source.PriceCandleDataStreamSource.createLineSource;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.robindrew.common.util.Java;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.list.filter.PriceCandleListSortedFilter;
import com.robindrew.trading.price.candle.io.list.sink.IPriceCandleListSink;
import com.robindrew.trading.price.candle.io.list.sink.PriceCandleListToStreamSink;
import com.robindrew.trading.price.candle.io.list.source.IPriceCandleListSource;
import com.robindrew.trading.price.candle.io.list.source.PriceCandleListFilteredSource;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleDataStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleStreamToListSource;
import com.robindrew.trading.price.candle.line.formatter.IPriceCandleLineFormatter;
import com.robindrew.trading.price.candle.line.formatter.PriceCandleLineFormatter;

public class PriceCandleSources {

	private static final Logger log = LoggerFactory.getLogger(PriceCandleSources.class);

	public static void writeCandlesToLineWriter(IPriceCandleStreamSource source, Writer writer) {
		IPriceCandleLineFormatter formatter = new PriceCandleLineFormatter();

		try {
			while (true) {
				IPriceCandle candle = source.getNextCandle();
				if (candle == null) {
					break;
				}

				// Build and write the line
				String line = formatter.formatCandle(candle, true);
				writer.write(line);
			}
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	public static void writeCandlesToLineFile(IPriceCandleStreamSource source, File file) {
	}

	public static void writeCandlesToDataFile(File toFile, List<IPriceCandle> candleList) {
		if (candleList.isEmpty()) {
			throw new IllegalArgumentException("candleList is empty");
		}
		int decimalPlaces = candleList.get(0).getDecimalPlaces();

		log.info("Writing " + number(candleList) + " candles to file " + toFile);
		Stopwatch timer = Stopwatch.createStarted();

		try (IPriceCandleListSink sink = new PriceCandleListToStreamSink(createFileDataSink(toFile, new PriceCandleDataSerializer(decimalPlaces)))) {
			sink.putNextCandles(candleList);
		}

		timer.stop();
		log.info("Written " + number(candleList) + " candles to file " + toFile + " in " + timer);
	}

	public static List<IPriceCandle> readStreamToSortedList(IPriceCandleStreamSource stream) {
		log.info("Reading candles from " + stream.getName());
		Stopwatch timer = Stopwatch.createStarted();

		final List<IPriceCandle> candleList;
		try (IPriceCandleListSource list = new PriceCandleListFilteredSource(new PriceCandleStreamToListSource(stream), new PriceCandleListSortedFilter())) {
			candleList = list.getNextCandles();
		}

		timer.stop();
		log.info("Read " + number(candleList) + " candles from " + stream.getName() + " in " + timer);
		return candleList;
	}

	public static List<IPriceCandle> readCandlesFromDataFile(File fromFile, int decimalPlaces) {
		try (PriceCandleDataStreamSource stream = createLineSource(fromFile, new PriceCandleDataSerializer(decimalPlaces))) {
			return PriceCandleSources.readStreamToSortedList(stream);
		}
	}

}
