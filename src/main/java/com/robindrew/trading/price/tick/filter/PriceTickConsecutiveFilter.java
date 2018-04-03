package com.robindrew.trading.price.tick.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.text.Strings;
import com.robindrew.trading.price.tick.IPriceTick;

public class PriceTickConsecutiveFilter implements IPriceTickFilter {

	private static final Logger log = LoggerFactory.getLogger(PriceTickConsecutiveFilter.class);

	private final int logThreshold;

	private IPriceTick previous = null;
	private int skipped = 0;

	public PriceTickConsecutiveFilter(int logThreshold) {
		if (logThreshold < 1) {
			throw new IllegalArgumentException("logThreshold=" + logThreshold);
		}
		this.logThreshold = logThreshold;
	}

	@Override
	public boolean accept(IPriceTick next) {
		if (previous != null) {

			// Test if two ticks in a row are consecutive
			if (next.getOpenTime() < previous.getCloseTime()) {
				skipped++;
				if (skipped % logThreshold == 0) {
					log.info("Filtered out " + Strings.number(skipped) + " non-consecutive ticks");
				}
				return false;
			}
		}
		previous = next;
		return true;
	}

}
