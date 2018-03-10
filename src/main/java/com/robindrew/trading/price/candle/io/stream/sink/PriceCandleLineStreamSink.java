package com.robindrew.trading.price.candle.io.stream.sink;

import com.robindrew.trading.price.PriceException;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.line.sink.ILineSink;
import com.robindrew.trading.price.candle.line.formatter.IPriceCandleLineFormatter;
import com.robindrew.trading.price.candle.line.formatter.PriceCandleLineFormatter;

public class PriceCandleLineStreamSink implements IPriceCandleStreamSink {

	private final ILineSink lineSink;
	private final IPriceCandleLineFormatter formatter;

	public PriceCandleLineStreamSink(ILineSink lineSink, IPriceCandleLineFormatter formatter) {
		if (lineSink == null) {
			throw new NullPointerException("lineSink");
		}
		if (formatter == null) {
			throw new NullPointerException("formatter");
		}
		this.lineSink = lineSink;
		this.formatter = formatter;
	}

	public PriceCandleLineStreamSink(ILineSink lineSink) {
		this(lineSink, new PriceCandleLineFormatter());
	}

	@Override
	public String getName() {
		return lineSink.getName();
	}

	@Override
	public void close() {
		lineSink.close();
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		String line = formatter.formatCandle(candle, true);
		try {
			lineSink.putNextLine(line);
		} catch (Exception e) {
			throw new PriceException("Failed to write next line to: " + lineSink.getName() + ", line: '" + line + "'", e);
		}
	}

}
