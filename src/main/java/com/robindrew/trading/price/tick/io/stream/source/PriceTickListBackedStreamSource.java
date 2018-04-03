package com.robindrew.trading.price.tick.io.stream.source;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.robindrew.trading.price.tick.IPriceTick;

public class PriceTickListBackedStreamSource implements IPriceTickStreamSource {

	private final List<IPriceTick> list;
	private final String name;
	private int index = 0;

	public PriceTickListBackedStreamSource(String name, Collection<? extends IPriceTick> candles) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		if (candles.isEmpty()) {
			throw new IllegalArgumentException("candles is empty");
		}

		this.name = name;
		this.list = ImmutableList.copyOf(candles);
	}

	public PriceTickListBackedStreamSource(Collection<? extends IPriceTick> candles) {
		this("PriceTickCollectionSource", candles);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void close() {
		list.clear();
	}

	@Override
	public IPriceTick getNextTick() {
		if (index == list.size()) {
			return null;
		}
		return list.get(index++);
	}

}
