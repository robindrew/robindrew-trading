package com.robindrew.trading.price.candle.interval.candle;

import java.time.LocalDateTime;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.interval.IPriceInterval;

public class PriceCandleIntervalPartitioner implements IPriceCandleLocalDatePartitioner {

	private final IPriceInterval interval;

	public PriceCandleIntervalPartitioner(IPriceInterval interval) {
		if (interval == null) {
			throw new NullPointerException("interval");
		}
		this.interval = interval;
	}

	public IPriceInterval getInterval() {
		return interval;
	}

	@Override
	public ListMultimap<LocalDateTime, IPriceCandle> partition(List<IPriceCandle> candles) {
		ListMultimap<LocalDateTime, IPriceCandle> map = ArrayListMultimap.create();
		for (IPriceCandle candle : candles) {
			LocalDateTime date = interval.getDateTime(candle);
			map.put(date, candle);
		}
		return map;
	}

}
