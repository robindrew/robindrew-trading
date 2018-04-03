package com.robindrew.trading.price.tick.io.stream.source;

import static com.google.common.base.Charsets.US_ASCII;
import static com.robindrew.common.text.Strings.bytes;

import java.io.File;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.io.Files;
import com.robindrew.trading.price.candle.io.line.source.FileLineSource;
import com.robindrew.trading.price.candle.io.line.source.FilteredLineSource;
import com.robindrew.trading.price.candle.io.line.source.ILineSource;
import com.robindrew.trading.price.candle.line.filter.ILineFilter;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.line.parser.IPriceTickLineParser;

public class PriceTickDirectoryStreamSource implements IPriceTickStreamSource {

	private static final Logger log = LoggerFactory.getLogger(PriceTickDirectoryStreamSource.class);

	private final File directory;
	private final IPriceTickLineParser parser;
	private final ILineFilter filter;
	private final LinkedList<File> files = new LinkedList<>();

	private IPriceTickStreamSource currentSource = null;

	public PriceTickDirectoryStreamSource(File directory, IPriceTickLineParser parser, ILineFilter filter) {
		if (directory == null) {
			throw new NullPointerException("directory");
		}
		if (parser == null) {
			throw new NullPointerException("parser");
		}
		if (filter == null) {
			throw new NullPointerException("filter");
		}

		this.directory = directory;
		this.parser = parser;
		this.filter = filter;

		this.files.addAll(new TreeSet<>(Files.listContents(directory)));
	}

	@Override
	public String getName() {
		return directory.getAbsolutePath();
	}

	@Override
	public void close() {
		files.clear();
		if (currentSource != null) {
			currentSource.close();
			currentSource = null;
		}
	}

	@Override
	public IPriceTick getNextTick() {

		// Is there a source?
		if (currentSource != null) {
			IPriceTick tick = currentSource.getNextTick();
			if (tick != null) {
				return tick;
			}
			currentSource = null;
		}

		// No more files?
		if (files.isEmpty()) {
			return null;
		}

		// Next file
		File file = files.removeFirst();
		log.info("Source File: " + file + " (" + bytes(file.length()) + ")");
		ILineSource lines = createLineSource(file, filter, US_ASCII);
		currentSource = new PriceTickLineStreamSource(lines, parser);

		// Each file should contain at least one tick!
		IPriceTick tick = currentSource.getNextTick();
		if (tick == null) {
			throw new IllegalStateException("File does not contain any ticks: " + file);
		}
		return tick;
	}

	protected ILineSource createLineSource(File file, ILineFilter filter, Charset charset) {
		return new FilteredLineSource(new FileLineSource(file, charset), filter);
	}

}
