package com.robindrew.trading.price.candle.io.stream.sink;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.list.sink.IPriceCandleListSink;
import com.robindrew.trading.price.candle.io.list.source.IPriceCandleListSource;

public class RollingCacheStreamSink implements IPriceCandleStreamSink, IPriceCandleListSink, IPriceCandleListSource {

	private final LinkedList<IPriceCandle> cache = new LinkedList<>();
	private final boolean headFirst;
	private final String name;
	private final int capacity;

	public RollingCacheStreamSink(String name, int capacity, boolean headFirst) {
		if (name == null) {
			throw new NullPointerException("name");
		}
		if (capacity < 1) {
			throw new IllegalArgumentException("capacity=" + capacity);
		}
		this.name = name;
		this.capacity = capacity;
		this.headFirst = headFirst;
	}

	@Override
	public String getName() {
		return name;
	}

	public int getCapacity() {
		return capacity;
	}

	public boolean isHeadFirst() {
		return headFirst;
	}

	@Override
	public void close() {
		synchronized (this) {
			cache.clear();
		}
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		if (candle == null) {
			throw new NullPointerException("candle");
		}
		synchronized (this) {
			if (headFirst) {
				cache.addFirst(candle);
			} else {
				cache.addLast(candle);
			}
			if (cache.size() > capacity) {
				if (headFirst) {
					cache.removeLast();
				} else {
					cache.removeFirst();
				}
			}
		}
	}

	@Override
	public List<IPriceCandle> getNextCandles() {
		synchronized (this) {
			return new ArrayList<>(cache);
		}
	}

	@Override
	public void putNextCandles(List<? extends IPriceCandle> candles) {
		synchronized (this) {
			for (IPriceCandle candle : candles) {
				putNextCandle(candle);
			}
		}
	}

}
