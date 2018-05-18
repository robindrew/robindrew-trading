package com.robindrew.trading.price.candle.io.stream.sink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleLoggingStreamSink implements IPriceCandleStreamSink {

	private static final Logger log = LoggerFactory.getLogger(PriceCandleLoggingStreamSink.class);

	@Override
	public String getName() {
		return "PriceCandleLoggingStreamSink";
	}

	@Override
	public void close() {
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		log.info("putNextCandle({})", candle);
	}

}
