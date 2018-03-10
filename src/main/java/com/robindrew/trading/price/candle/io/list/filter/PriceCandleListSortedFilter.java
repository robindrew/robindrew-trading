package com.robindrew.trading.price.candle.io.list.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandleDateComparator;

public class PriceCandleListSortedFilter implements IPriceCandleListFilter {

	private final Comparator<IPriceCandle> comparator;

	public PriceCandleListSortedFilter(Comparator<IPriceCandle> comparator) {
		if (comparator == null) {
			throw new NullPointerException("comparator");
		}
		this.comparator = comparator;
	}

	public PriceCandleListSortedFilter() {
		this(new PriceCandleDateComparator());
	}

	@Override
	public List<IPriceCandle> filter(List<IPriceCandle> candles) {
		candles = new ArrayList<>(candles);
		Collections.sort(candles, comparator);
		return candles;
	}

}
