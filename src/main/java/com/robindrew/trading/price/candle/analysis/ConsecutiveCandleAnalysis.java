package com.robindrew.trading.price.candle.analysis;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ListMultimap;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.interval.IPriceCandleInterval;
import com.robindrew.trading.price.candle.interval.PriceCandleIntervalPartitioner;
import com.robindrew.trading.price.candle.interval.PriceCandleIntervals;

public class ConsecutiveCandleAnalysis implements IPriceCandleAnalysis {

	private static final Logger log = LoggerFactory.getLogger(ConsecutiveCandleAnalysis.class);

	@Override
	public void performAnalysis(IInstrument instrument, List<IPriceCandle> candles) {
		IPriceCandleInterval interval = PriceCandleIntervals.HOURLY;

		ListMultimap<LocalDateTime, IPriceCandle> partitions = new PriceCandleIntervalPartitioner(interval).partition(candles);

		for (LocalDateTime date : new TreeSet<>(partitions.keySet())) {
			List<IPriceCandle> list = partitions.get(date);

			long total = 0;
			int count = 0;
			for (IPriceCandle candle : list) {
				count++;
				total += candle.getHighLowRange();
			}

			log.info("[Date] " + date + " -> " + (total / count));
		}

	}

}
