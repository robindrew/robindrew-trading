package com.robindrew.trading.price.candle.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.text.Strings;
import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleConsecutiveFilter implements IPriceCandleFilter {

	private static final Logger log = LoggerFactory.getLogger(PriceCandleConsecutiveFilter.class);

	private final int logThreshold;

	private IPriceCandle previous = null;
	private int skipped = 0;

	public PriceCandleConsecutiveFilter(int logThreshold) {
		if (logThreshold < 1) {
			throw new IllegalArgumentException("logThreshold=" + logThreshold);
		}
		this.logThreshold = logThreshold;
	}

	@Override
	public boolean accept(IPriceCandle next) {
		if (previous != null) {

			// Test if two candles in a row are consecutive
			if (next.getOpenTime() < previous.getCloseTime()) {
				skipped++;
				if (skipped % logThreshold == 0) {
					log.info("Filtered out " + Strings.number(skipped) + " non-consecutive candles");
				}
				return false;
			}
		}
		previous = next;
		return true;
	}

}
