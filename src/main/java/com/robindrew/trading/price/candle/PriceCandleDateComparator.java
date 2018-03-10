package com.robindrew.trading.price.candle;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PriceCandleDateComparator implements Comparator<IPriceCandle> {

	private final boolean ascending;

	public PriceCandleDateComparator(boolean ascending) {
		this.ascending = ascending;
	}

	public PriceCandleDateComparator() {
		this(true);
	}

	@Override
	public int compare(IPriceCandle candle1, IPriceCandle candle2) {
		if (candle1.getOpenTime() >= candle2.getCloseTime()) {
			return ascending ? 1 : -1;
		}
		if (candle1.getCloseTime() <= candle2.getOpenTime()) {
			return ascending ? -1 : 1;
		}
		if (candle1.equals(candle2)) {
			return 0;
		}
		throw new IllegalArgumentException("Non equal, matching candles, candle1=" + candle1 + ", candle2=" + candle2);
	}

	public void sort(List<IPriceCandle> list) {
		Collections.sort(list, this);
	}

}
