package com.robindrew.trading.price.tick.io.stream.source;

import com.robindrew.trading.price.PriceException;
import com.robindrew.trading.price.candle.io.line.source.ILineSource;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.line.parser.IPriceTickLineParser;

public class PriceTickLineStreamSource implements IPriceTickStreamSource {

	private final ILineSource lineSource;
	private final IPriceTickLineParser parser;

	public PriceTickLineStreamSource(ILineSource lineSource, IPriceTickLineParser parser) {
		if (lineSource == null) {
			throw new NullPointerException("lineSource");
		}
		if (parser == null) {
			throw new NullPointerException("parser");
		}
		this.lineSource = lineSource;
		this.parser = parser;
	}

	@Override
	public String getName() {
		return lineSource.getName();
	}

	@Override
	public void close() {
		lineSource.close();
	}

	@Override
	public IPriceTick getNextTick() {
		String line = nextLine();

		// No more ticks?
		if (line == null) {
			return null;
		}
		if (parser.skipLine(line)) {
			return getNextTick();
		}

		return parseTick(line);
	}

	private IPriceTick parseTick(String line) {
		try {
			return parser.parseTick(line);
		} catch (Exception e) {
			throw new PriceException("Failed to parse line from: " + lineSource.getName() + ", line: '" + line + "'", e);
		}
	}

	private String nextLine() {
		try {
			return lineSource.getNextLine();
		} catch (Exception e) {
			throw new PriceException("Failed to read next line from: " + lineSource.getName(), e);
		}
	}

}
