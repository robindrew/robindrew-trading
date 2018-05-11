package com.robindrew.trading.price.candle.io.stream.source;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleListBackedStreamSource implements IPriceCandleStreamSource {

	private final List<IPriceCandle> list;
	private final String name;
	private int index = 0;

	public PriceCandleListBackedStreamSource(String name, Collection<? extends IPriceCandle> candles) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		if (candles.isEmpty()) {
			throw new IllegalArgumentException("candles is empty");
		}

		this.name = name;
		this.list = new ArrayList<>(candles);
	}

	public PriceCandleListBackedStreamSource(Collection<? extends IPriceCandle> candles) {
		this("PriceCandleCollectionSource", candles);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void close() {
		index = 0;
		list.clear();
	}

	@Override
	public IPriceCandle getNextCandle() {
		if (index == list.size()) {
			return null;
		}
		return list.get(index++);
	}

}
