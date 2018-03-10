package com.robindrew.trading.price.candle.io.stream.source;

import com.robindrew.trading.price.PriceException;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.line.source.ILineSource;
import com.robindrew.trading.price.candle.line.parser.IPriceCandleLineParser;

public class PriceCandleLineStreamSource implements IPriceCandleStreamSource {

	private final ILineSource lineSource;
	private final IPriceCandleLineParser parser;

	public PriceCandleLineStreamSource(ILineSource lineSource, IPriceCandleLineParser parser) {
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
	public IPriceCandle getNextCandle() {
		String line = nextLine();

		// No more candles?
		if (line == null) {
			return null;
		}
		if (parser.skipLine(line)) {
			return getNextCandle();
		}

		return parseCandle(line);
	}

	private IPriceCandle parseCandle(String line) {
		try {
			return parser.parseCandle(line);
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
