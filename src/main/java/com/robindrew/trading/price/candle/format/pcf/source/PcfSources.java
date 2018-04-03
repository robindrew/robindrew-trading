package com.robindrew.trading.price.candle.format.pcf.source;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import com.robindrew.trading.price.window.ITimeWindow;
import com.robindrew.trading.price.window.MonthlyTimeWindow;

public class PcfSources {

	public static <S extends IPcfSource> Set<S> filterSources(Collection<? extends S> sources, LocalDateTime from, LocalDateTime to) {
		ITimeWindow window = new MonthlyTimeWindow(from, to);
		return filterSources(sources, window);
	}

	public static <S extends IPcfSource> Set<S> filterSources(Collection<? extends S> sources, LocalDate from, LocalDate to) {
		ITimeWindow window = new MonthlyTimeWindow(from, to);
		return filterSources(sources, window);
	}

	public static <S extends IPcfSource> Set<S> filterSources(Collection<? extends S> sources, ITimeWindow window) {
		Set<S> filtered = new LinkedHashSet<>();
		for (S source : sources) {
			if (window.contains(source.getMonth())) {
				filtered.add(source);
			}
		}
		return filtered;
	}

}
