package com.robindrew.trading.price.tick.io.stream.sink;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.io.list.filter.PriceTickListSortedFilter;
import com.robindrew.trading.price.tick.io.list.sink.DailyFilePriceTickListConsumer;
import com.robindrew.trading.price.tick.io.list.sink.PriceTickListFilteredSink;
import com.robindrew.trading.price.tick.line.formatter.PriceTickLineFormatter;

/**
 * An price tick file, writing every tick. Uses a queue to asynchronously write ticks to file.
 */
public class PriceTickFileSink implements IPriceTickStreamSink, AutoCloseable {

	private static final Logger log = LoggerFactory.getLogger(PriceTickFileSink.class);

	private final IInstrument instrument;
	private final File directory;
	private final DailyFilePriceTickListConsumer fileSink;
	private final PriceTickListFilteredSink filteredSink;
	private final QueuedPriceTickSink queueSink;
	
	public PriceTickFileSink(IInstrument instrument, File directory) {
		this.instrument = Check.notNull("instrument", instrument);
		this.directory = Check.existsDirectory("directory", directory);

		String filename = toFilename(instrument.getName());
		this.fileSink = new DailyFilePriceTickListConsumer(directory, filename, new PriceTickLineFormatter());
		this.filteredSink = new PriceTickListFilteredSink(fileSink, new PriceTickListSortedFilter());
		this.queueSink = new QueuedPriceTickSink(instrument, filteredSink);

		log.info("Writing price ticks for {} to {}", instrument, fileSink.getDirectory());
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
	public void close() {
		queueSink.close();
	}

	public void start() {
		queueSink.start();
	}

	@Override
	public String getName() {
		return queueSink.getName();
	}

	@Override
	public void putNextTick(IPriceTick tick) {
		queueSink.putNextTick(tick);
	}

}
