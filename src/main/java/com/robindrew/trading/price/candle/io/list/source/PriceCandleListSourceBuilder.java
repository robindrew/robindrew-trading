package com.robindrew.trading.price.candle.io.list.source;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.checker.IPriceCandleChecker;
import com.robindrew.trading.price.candle.io.list.filter.IPriceCandleListFilter;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleStreamToListSource;

public class PriceCandleListSourceBuilder implements IPriceCandleListSourceBuilder {

	private IPriceCandleListSource base;
	private final Set<IPriceCandleListFilter> filterSet = new LinkedHashSet<>();
	private final Set<IPriceCandleChecker> checkerSet = new LinkedHashSet<>();

	@Override
	public PriceCandleListSourceBuilder setBaseSource(IPriceCandleListSource source) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (base != null) {
			throw new IllegalStateException("Base source already initialised");
		}
		base = source;
		return this;
	}

	@SuppressWarnings("resource")
	@Override
	public IPriceCandleListSourceBuilder setCandles(List<IPriceCandle> candles) {
		PriceCandleListBackedSource source = new PriceCandleListBackedSource().addAll(candles);
		return setBaseSource(source);
	}

	@Override
	public IPriceCandleListSourceBuilder setStreamSource(IPriceCandleStreamSource source) {
		return setBaseSource(new PriceCandleStreamToListSource(source));
	}

	@Override
	public IPriceCandleListSourceBuilder filterBy(IPriceCandleListFilter filter) {
		if (filter == null) {
			throw new NullPointerException("filter");
		}
		filterSet.add(filter);
		return this;
	}

	@Override
	public IPriceCandleListSourceBuilder checkWith(IPriceCandleChecker checker) {
		if (checker == null) {
			throw new NullPointerException("checker");
		}
		checkerSet.add(checker);
		return this;
	}

	private IPriceCandleListSource getBaseSource() {
		if (base == null) {
			throw new IllegalStateException("No base source initialised (directory, file, etc)");
		}
		return base;
	}

	@Override
	public IPriceCandleListSource get() {
		IPriceCandleListSource source = getBaseSource();

		// Apply filters
		for (IPriceCandleListFilter filter : filterSet) {
			source = new PriceCandleListFilteredSource(source, filter);
		}

		// Apply checkers
		for (IPriceCandleChecker checker : checkerSet) {
			source = new PriceCandleListCheckerSource(source, checker);
		}

		return source;
	}

}
