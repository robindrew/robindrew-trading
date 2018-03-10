package com.robindrew.trading.price.candle.io.stream.source;

import static com.robindrew.common.text.Strings.number;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandles;
import com.robindrew.trading.price.candle.io.list.source.IPriceCandleListSource;

public class PriceCandleStreamToListSource implements IPriceCandleListSource {

	private static final Logger log = LoggerFactory.getLogger(PriceCandleStreamToListSource.class);

	private final IPriceCandleStreamSource source;

	public PriceCandleStreamToListSource(IPriceCandleStreamSource source) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		this.source = source;
	}

	@Override
	public String getName() {
		return source.getName();
	}

	@Override
	public void close() {
		source.close();
	}

	@Override
	public List<IPriceCandle> getNextCandles() {
		log.debug("Reading all candles from {}", getName());
		Stopwatch timer = Stopwatch.createStarted();

		List<IPriceCandle> list = Lists.newArrayList(PriceCandles.iterable(source));

		timer.stop();
		log.debug("Read {} candles from {} in {}", number(list), getName(), timer);
		return list;
	}

}
