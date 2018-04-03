package com.robindrew.trading.price.tick.io.list.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.PriceTickDateComparator;

public class PriceTickListSortedFilter implements IPriceTickListFilter {

	private final Comparator<IPriceTick> comparator;

	public PriceTickListSortedFilter(Comparator<IPriceTick> comparator) {
		if (comparator == null) {
			throw new NullPointerException("comparator");
		}
		this.comparator = comparator;
	}

	public PriceTickListSortedFilter() {
		this(new PriceTickDateComparator());
	}

	@Override
	public List<IPriceTick> filter(List<IPriceTick> ticks) {
		ticks = new ArrayList<>(ticks);
		Collections.sort(ticks, comparator);
		return ticks;
	}

}
