package com.robindrew.trading.price.candle.io.stream.source;

import static java.util.Collections.emptyList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.io.list.source.IPriceCandleListSource;

public class PriceCandleIntervalStreamToListSource implements IPriceCandleListSource {

	private final IPriceCandleStreamSource source;
	private final IPriceInterval interval;

	private boolean finished = false;
	private IPriceCandle next = null;

	public PriceCandleIntervalStreamToListSource(IPriceCandleStreamSource source, IPriceInterval interval) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (interval == null) {
			throw new NullPointerException("interval");
		}
		this.source = source;
		this.interval = interval;
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
		if (finished) {
			return emptyList();
		}

		if (next == null) {
			next = source.getNextCandle();
			if (next == null) {
				finished = true;
				return emptyList();
			}
		}
		LocalDateTime date = interval.getDateTime(next);

		List<IPriceCandle> candles = new ArrayList<>();
		candles.add(next);

		while (true) {
			next = source.getNextCandle();
			if (next == null) {
				finished = true;
				break;
			}
			if (!date.equals(interval.getDateTime(next))) {
				break;
			}
			candles.add(next);
		}

		return candles;
	}

}
