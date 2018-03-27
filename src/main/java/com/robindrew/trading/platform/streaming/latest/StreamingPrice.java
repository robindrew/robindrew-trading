package com.robindrew.trading.platform.streaming.latest;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.robindrew.trading.price.candle.IPriceCandle;

/**
 * Asynchronous container for the latest in a stream of prices. Gives
 */
public class StreamingPrice implements IStreamingPrice {

	private final AtomicReference<PriceSnapshot> priceSnapshot = new AtomicReference<PriceSnapshot>();
	private final AtomicLong count = new AtomicLong(0);

	@Override
	public PriceSnapshot getSnapshot() {
		return priceSnapshot.get();
	}

	@Override
	public long getUpdateCount() {
		return count.get();
	}

	public void update(IPriceCandle candle) {
		update(candle, candle.getCloseTime());
	}

	public void update(IPriceCandle candle, long timestamp) {
		PriceSnapshot snapshot = priceSnapshot.get();
		if (snapshot == null) {
			snapshot = new PriceSnapshot(candle, timestamp);
		} else {
			snapshot = snapshot.update(candle, timestamp);
		}
		priceSnapshot.set(snapshot);
		count.incrementAndGet();
	}

}
