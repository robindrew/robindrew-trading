package com.robindrew.trading.price.candle.io.stream.source;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandleDateComparator;
import com.robindrew.trading.price.candle.checker.IPriceCandleChecker;
import com.robindrew.trading.price.candle.checker.PriceCandleSanityChecker;
import com.robindrew.trading.price.candle.checker.PriceCandleSortedChecker;
import com.robindrew.trading.price.candle.filter.IPriceCandleFilter;
import com.robindrew.trading.price.candle.filter.PriceCandleConsecutiveFilter;
import com.robindrew.trading.price.candle.filter.PriceCandleCountFilter;
import com.robindrew.trading.price.candle.filter.PriceCandleDateFilter;
import com.robindrew.trading.price.candle.filter.PriceCandleDateTimeFilter;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;
import com.robindrew.trading.price.candle.format.pcf.source.PcfSourcesStreamSource;
import com.robindrew.trading.price.candle.format.pcf.source.file.IPcfFile;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFile;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSource;
import com.robindrew.trading.price.candle.format.ptf.source.PtfSourcesStreamSource;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.io.list.source.IPriceCandleListSourceBuilder;
import com.robindrew.trading.price.candle.io.list.source.PriceCandleListSourceBuilder;
import com.robindrew.trading.price.candle.modifier.IPriceCandleModifier;
import com.robindrew.trading.price.candle.modifier.PriceCandleDivideModifier;
import com.robindrew.trading.price.candle.modifier.PriceCandleMultiplyModifier;
import com.robindrew.trading.price.candle.tick.ITickPriceCandle;

public class PriceCandleStreamSourceBuilder implements IPriceCandleStreamSourceBuilder {

	private IPriceCandleStreamSource base;
	private IPriceInterval candleInterval;
	private final Set<IPriceCandleFilter> filterSet = new LinkedHashSet<>();
	private final Set<IPriceCandleChecker> checkerSet = new LinkedHashSet<>();
	private final Set<IPriceCandleModifier> modifierSet = new LinkedHashSet<>();

	@Override
	public IPriceCandleStreamSourceBuilder setBaseSource(IPriceCandleStreamSource source) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (base != null) {
			throw new IllegalStateException("Base source already initialised");
		}
		base = source;
		return this;
	}

	@Override
	public IPriceCandleStreamSourceBuilder setPcfFile(String filename) {
		File file = new File(filename);
		IPcfFile pcf = new PcfFile(file);
		return setPcfSource(pcf);
	}

	@Override
	public IPriceCandleStreamSourceBuilder setPcfSource(IPcfSource source) {
		List<? extends IPriceCandle> candles = source.read();
		return setBaseSource(new PriceCandleListBackedStreamSource(candles));
	}

	@Override
	public IPriceCandleStreamSourceBuilder setPcfSources(Collection<? extends IPcfSource> sources) {
		return setBaseSource(new PcfSourcesStreamSource(sources));
	}

	@Override
	public IPriceCandleStreamSourceBuilder setPtfFile(String filename) {
		File file = new File(filename);
		IPcfFile pcf = new PcfFile(file);
		return setPcfSource(pcf);
	}

	@Override
	public IPriceCandleStreamSourceBuilder setPtfSource(IPtfSource source) {
		List<ITickPriceCandle> candles = source.read();
		return setBaseSource(new PriceCandleListBackedStreamSource(candles));
	}

	@Override
	public IPriceCandleStreamSourceBuilder setPtfSources(Collection<? extends IPtfSource> sources) {
		return setBaseSource(new PtfSourcesStreamSource(sources));
	}

	@Override
	public IPriceCandleStreamSourceBuilder setCandles(List<IPriceCandle> candles) {
		return setBaseSource(new PriceCandleListBackedStreamSource(candles));
	}

	@Override
	public IPriceCandleStreamSourceBuilder checkSortedByDate() {
		return checkSortedBy(new PriceCandleDateComparator());
	}

	@Override
	public IPriceCandleStreamSourceBuilder filterBy(IPriceCandleFilter filter) {
		if (filter == null) {
			throw new NullPointerException("filter");
		}
		filterSet.add(filter);
		return this;
	}

	@Override
	public IPriceCandleStreamSourceBuilder limit(int maxCandles) {
		IPriceCandleFilter filter = new PriceCandleCountFilter(maxCandles);
		return filterBy(filter);
	}

	@Override
	public IPriceCandleStreamSourceBuilder between(LocalDateTime from, LocalDateTime to) {
		IPriceCandleFilter filter = new PriceCandleDateTimeFilter(from, to);
		return filterBy(filter);
	}

	@Override
	public IPriceCandleStreamSourceBuilder between(LocalDate from, LocalDate to) {
		IPriceCandleFilter filter = new PriceCandleDateFilter(from, to);
		return filterBy(filter);
	}

	@Override
	public IPriceCandleStreamSourceBuilder modifyWith(IPriceCandleModifier modifier) {
		if (modifier == null) {
			throw new NullPointerException("modifier");
		}
		modifierSet.add(modifier);
		return this;
	}

	@Override
	public IPriceCandleStreamSourceBuilder checkWith(IPriceCandleChecker checker) {
		if (checker == null) {
			throw new NullPointerException("checker");
		}
		checkerSet.add(checker);
		return this;
	}

	@Override
	public IPriceCandleStreamSourceBuilder checkSortedBy(Comparator<IPriceCandle> comparator) {
		return checkWith(new PriceCandleSortedChecker(comparator));
	}

	@Override
	public IPriceCandleStreamSourceBuilder checkSanity(double maxPercentDiff) {
		return checkWith(new PriceCandleSanityChecker(maxPercentDiff));
	}

	@Override
	public IPriceCandleStreamSourceBuilder filterConsecutive(int logThreshold) {
		return filterBy(new PriceCandleConsecutiveFilter(logThreshold));
	}

	@Override
	public IPriceCandleStreamSourceBuilder setInterval(IPriceInterval interval) {
		if (interval == null) {
			throw new NullPointerException("interval");
		}
		candleInterval = interval;
		return this;
	}

	@Override
	public IPriceCandleStreamSourceBuilder multiplyBy(int multiplier) {
		return modifyWith(new PriceCandleMultiplyModifier(multiplier));
	}

	@Override
	public IPriceCandleStreamSourceBuilder divideBy(int divisor) {
		return modifyWith(new PriceCandleDivideModifier(divisor));
	}

	private IPriceCandleStreamSource getBaseSource() {
		if (base == null) {
			throw new IllegalStateException("No base source initialised (directory, file, etc)");
		}
		return base;
	}

	@Override
	public IPriceCandleStreamSource get() {
		IPriceCandleStreamSource source = getBaseSource();

		// Aggregate by inverval?
		if (candleInterval != null) {
			source = new PriceCandleIntervalStreamSource(source, candleInterval);
		}

		// Apply filters
		for (IPriceCandleFilter filter : filterSet) {
			source = new PriceCandleFilteredStreamSource(source, filter);
		}

		// Apply modifiers
		for (IPriceCandleModifier modifier : modifierSet) {
			source = new PriceCandleModifierStreamSource(source, modifier);
		}

		// Apply checkers
		for (IPriceCandleChecker checker : checkerSet) {
			source = new PriceCandleCheckerStreamSource(source, checker);
		}

		return source;
	}

	@Override
	public IPriceCandleListSourceBuilder asListSourceBuilder() {
		return new PriceCandleListSourceBuilder().setStreamSource(get());
	}

	@Override
	public List<IPriceCandle> getList() {
		try (PriceCandleStreamToListSource source = new PriceCandleStreamToListSource(get())) {
			return source.getNextCandles();
		}
	}

}
