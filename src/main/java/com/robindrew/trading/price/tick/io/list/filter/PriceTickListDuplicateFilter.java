package com.robindrew.trading.price.tick.io.list.filter;

import static com.robindrew.common.text.Strings.number;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.price.tick.IPriceTick;

public class PriceTickListDuplicateFilter implements IPriceTickListFilter {

	private static final Logger log = LoggerFactory.getLogger(PriceTickListDuplicateFilter.class);

	@Override
	public List<IPriceTick> filter(List<IPriceTick> ticks) {
		int size = ticks.size();
		Set<IPriceTick> set = new LinkedHashSet<>(ticks);
		int duplicates = size - set.size();
		if (duplicates > 0) {
			log.warn("Filtered " + number(duplicates) + " duplicate ticks");
		}
		return new ArrayList<>(set);
	}

}
