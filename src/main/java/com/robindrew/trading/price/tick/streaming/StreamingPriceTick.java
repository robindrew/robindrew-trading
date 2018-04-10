package com.robindrew.trading.price.tick.streaming;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.robindrew.trading.price.tick.IPriceTick;

/**
 * Asynchronous container for the latest in a stream of prices. Gives
 */
public class StreamingPriceTick implements IStreamingTickPrice {

	private final AtomicReference<PriceTickSnapshot> priceSnapshot = new AtomicReference<PriceTickSnapshot>();
	private final AtomicLong count = new AtomicLong(0);

	@Override
	public PriceTickSnapshot getSnapshot() {
		return priceSnapshot.get();
	}

	@Override
	public long getUpdateCount() {
		return count.get();
	}

	public void update(IPriceTick tick) {
		update(tick, tick.getCloseTime());
	}

	public void update(IPriceTick tick, long timestamp) {
		PriceTickSnapshot snapshot = priceSnapshot.get();
		if (snapshot == null) {
			snapshot = new PriceTickSnapshot(tick, timestamp);
		} else {
			snapshot = snapshot.update(tick, timestamp);
		}
		priceSnapshot.set(snapshot);
		count.incrementAndGet();
	}

}
