package com.robindrew.trading.price.candle.io.list.filter;

import static com.robindrew.common.text.Strings.number;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleListDuplicateFilter implements IPriceCandleListFilter {

	private static final Logger log = LoggerFactory.getLogger(PriceCandleListDuplicateFilter.class);

	@Override
	public List<IPriceCandle> filter(List<? extends IPriceCandle> candles) {
		int size = candles.size();
		Set<IPriceCandle> set = new LinkedHashSet<>(candles);
		int duplicates = size - set.size();
		if (duplicates > 0) {
			log.warn("Filtered " + number(duplicates) + " duplicate candles");
		}
		return new ArrayList<>(set);
	}

}
