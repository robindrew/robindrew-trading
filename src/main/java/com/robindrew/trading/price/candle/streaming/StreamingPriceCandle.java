package com.robindrew.trading.price.candle.streaming;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.robindrew.trading.price.candle.IPriceCandle;

/**
 * Asynchronous container for the latest in a stream of prices. Gives
 */
public class StreamingPriceCandle implements IStreamingCandlePrice {

	private final AtomicReference<PriceCandleSnapshot> priceSnapshot = new AtomicReference<PriceCandleSnapshot>();
	private final AtomicLong count = new AtomicLong(0);

	@Override
	public PriceCandleSnapshot getSnapshot() {
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
		PriceCandleSnapshot snapshot = priceSnapshot.get();
		if (snapshot == null) {
			snapshot = new PriceCandleSnapshot(candle, timestamp);
		} else {
			snapshot = snapshot.update(candle, timestamp);
		}
		priceSnapshot.set(snapshot);
		count.incrementAndGet();
	}

}
