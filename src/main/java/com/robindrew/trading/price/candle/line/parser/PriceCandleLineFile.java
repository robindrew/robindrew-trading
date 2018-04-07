package com.robindrew.trading.price.candle.line.parser;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleLineFile implements Iterable<IPriceCandle> {

	private final File file;
	private final IPriceCandleLineParser parser;

	public PriceCandleLineFile(File file, IPriceCandleLineParser parser) {
		this.file = Check.notNull("file", file);
		this.parser = Check.notNull("parser", parser);
	}

	public File getFile() {
		return file;
	}

	public IPriceCandleLineParser getParser() {
		return parser;
	}

	@Override
	public Iterator<IPriceCandle> iterator() {
		return new PriceCandleLineFileIterator(file, parser);
	}

	public List<IPriceCandle> toList() {
		return Lists.newArrayList(this);
	}
}
