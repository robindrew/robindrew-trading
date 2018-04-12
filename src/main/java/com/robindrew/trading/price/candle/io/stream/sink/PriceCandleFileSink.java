package com.robindrew.trading.price.candle.io.stream.sink;

import java.io.File;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.list.filter.PriceCandleListSortedFilter;
import com.robindrew.trading.price.candle.io.list.sink.DailyFilePriceCandleListConsumer;
import com.robindrew.trading.price.candle.io.list.sink.PriceCandleListFilteredSink;
import com.robindrew.trading.price.candle.line.formatter.PriceCandleLineFormatter;

public class PriceCandleFileSink implements IPriceCandleStreamSink {

	private final IInstrument instrument;
	private final File directory;
	private final DailyFilePriceCandleListConsumer fileSink;
	private final PriceCandleListFilteredSink filteredSink;
	private final QueuedPriceCandleSink queueSink;

	public PriceCandleFileSink(IInstrument instrument, File directory) {
		this.instrument = instrument;
		this.directory = directory;

		String filename = toFilename(instrument.getName());
		this.fileSink = new DailyFilePriceCandleListConsumer(directory, filename, new PriceCandleLineFormatter());
		this.filteredSink = new PriceCandleListFilteredSink(fileSink, new PriceCandleListSortedFilter());
		this.queueSink = new QueuedPriceCandleSink(instrument, filteredSink);
	}

	private String toFilename(String name) {
		return name.replace("#", "").replace("/", "_");
	}

	public IInstrument getInstrument() {
		return instrument;
	}

	public File getDirectory() {
		return directory;
	}

	@Override
	public String getName() {
		return directory.getName();
	}

	@Override
	public void close() {
		queueSink.close();
	}

	public void start() {
		queueSink.start();
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		queueSink.putNextCandle(candle);
	}

}
