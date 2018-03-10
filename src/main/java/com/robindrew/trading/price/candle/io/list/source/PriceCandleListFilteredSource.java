package com.robindrew.trading.price.candle.io.list.source;

import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.list.filter.IPriceCandleListFilter;

public class PriceCandleListFilteredSource implements IPriceCandleListSource {

	private final IPriceCandleListSource source;
	private final IPriceCandleListFilter filter;

	public PriceCandleListFilteredSource(IPriceCandleListSource source, IPriceCandleListFilter filter) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (filter == null) {
			throw new NullPointerException("filter");
		}
		this.source = source;
		this.filter = filter;
	}

	@Override
	public String getName() {
		return source.getName();
	}

	@Override
	public void close() {
		source.close();
	}

	@Override
	public List<IPriceCandle> getNextCandles() {
		List<IPriceCandle> candles = source.getNextCandles();
		return filter.filter(candles);
	}

}
