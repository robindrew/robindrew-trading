package com.robindrew.trading.price.tick.line.parser;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;
import com.robindrew.common.util.Check;
import com.robindrew.trading.price.tick.IPriceTick;

public class PriceTickLineFile implements Iterable<IPriceTick> {

	private final File file;
	private final IPriceTickLineParser parser;

	public PriceTickLineFile(File file, IPriceTickLineParser parser) {
		this.file = Check.notNull("file", file);
		this.parser = Check.notNull("parser", parser);
	}

	public File getFile() {
		return file;
	}

	public IPriceTickLineParser getParser() {
		return parser;
	}

	@Override
	public Iterator<IPriceTick> iterator() {
		return new PriceTickLineFileIterator(file, parser);
	}

	public List<IPriceTick> toList() {
		return Lists.newArrayList(this);
	}
}
