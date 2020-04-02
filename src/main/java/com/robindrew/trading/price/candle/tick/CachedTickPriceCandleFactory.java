package com.robindrew.trading.price.candle.tick;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Using a cached candle factory is very important for reducing GC overhead while reading millions of price ticks. To
 * use the factory you must explicitly maintain a reference to the factory so you can call the
 * {@link #release(ITickPriceCandle)} method for each tick that has been processed. The factory is thread-safe.
 */
public class CachedTickPriceCandleFactory implements ITickPriceCandleFactory {

	private static final Logger log = LoggerFactory.getLogger(CachedTickPriceCandleFactory.class);

	private int index = 0;
	private CachedTickPriceCandle[] cache;

	public CachedTickPriceCandleFactory(int initialCapacity) {
		if (initialCapacity < 1000) {
			throw new IllegalArgumentException("initialCapacity=" + initialCapacity);
		}
		cache = new CachedTickPriceCandle[initialCapacity];
	}

	public CachedTickPriceCandleFactory() {
		this(1000);
	}

	@Override
	public synchronized ITickPriceCandle create(int bidPrice, int askPrice, long timestamp, int decimalPlaces) {
		final CachedTickPriceCandle candle;
		if (index > 0) {
			candle = cache[--index];
		} else {
			candle = new CachedTickPriceCandle();
		}
		candle.set(bidPrice, askPrice, timestamp, decimalPlaces);
		return candle;
	}

	@Override
	public synchronized void release(ITickPriceCandle candle) {
		CachedTickPriceCandle cached = (CachedTickPriceCandle) candle;
		cached.release();
		checkCapacity();
		cache[index++] = cached;
	}

	private void checkCapacity() {
		if (index == cache.length) {
			int newLength = cache.length * 2;
			log.info("Increasing Cache Capacity {} -> {}", cache.length, newLength);
			CachedTickPriceCandle[] newCache = new CachedTickPriceCandle[newLength];
			System.arraycopy(cache, 0, newCache, 0, cache.length);
			cache = newCache;
		}
	}

}
