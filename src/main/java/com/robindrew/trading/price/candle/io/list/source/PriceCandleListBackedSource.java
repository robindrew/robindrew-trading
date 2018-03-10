package com.robindrew.trading.price.candle.io.list.source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleListBackedSource implements IPriceCandleListSource {

	private final String name;
	private final List<IPriceCandle> sourceList = new ArrayList<>();
	private boolean closed = false;

	public PriceCandleListBackedSource(String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		this.name = name;
	}

	public PriceCandleListBackedSource() {
		this.name = getClass().getSimpleName();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void close() {
		closed = true;
		sourceList.clear();
	}

	@Override
	public List<IPriceCandle> getNextCandles() {
		if (closed) {
			throw new IllegalStateException("Source closed: " + getName());
		}
		return new ArrayList<>(sourceList);
	}

	public PriceCandleListBackedSource add(IPriceCandle candle) {
		sourceList.add(candle);
		return this;
	}

	public PriceCandleListBackedSource addAll(Collection<? extends IPriceCandle> candles) {
		sourceList.addAll(candles);
		return this;
	}

}
