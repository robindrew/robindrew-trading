package com.robindrew.trading.price.candle.streaming;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.date.UnitTime;
import com.robindrew.trading.price.candle.IPriceCandle;

/**
 * Asynchronous container for the latest in a stream of prices. Gives
 */
public class StreamingPriceCandle implements IStreamingCandlePrice {

	private static final UnitTime DEFAULT_SINCE = new UnitTime(15, MINUTES);

	private final AtomicLong count = new AtomicLong(0);
	private final Deque<PriceCandleSnapshot> snapshots = new LinkedList<>();
	private final long since;

	public StreamingPriceCandle(long since) {
		if (since < 1) {
			throw new IllegalArgumentException("since=" + since);
		}
		this.since = since;
	}

	public StreamingPriceCandle(UnitTime since) {
		this(since.getTime(MILLISECONDS));
	}

	public StreamingPriceCandle(long time, TimeUnit unit) {
		this(unit.toMillis(time));
	}

	public StreamingPriceCandle() {
		this(DEFAULT_SINCE);
	}

	@Override
	public PriceCandleSnapshot getSnapshot() {
		synchronized (snapshots) {
			if (snapshots.isEmpty()) {
				return null;
			}
			return snapshots.getLast();
		}
	}

	@Override
	public long getUpdateCount() {
		return count.get();
	}

	public void update(IPriceCandle candle) {
		update(candle, candle.getCloseTime());
	}

	public void update(IPriceCandle candle, long timestamp) {
		PriceCandleSnapshot snapshot = getSnapshot();
		if (snapshot == null) {
			snapshot = new PriceCandleSnapshot(candle, timestamp);
		} else {
			snapshot = snapshot.update(candle, timestamp);
		}
		synchronized (snapshots) {
			if (addNextSnapshot(snapshot)) {
				clearPreviousSnapshots();
			}
		}
		count.incrementAndGet();
	}

	private boolean addNextSnapshot(PriceCandleSnapshot snapshot) {
		if (snapshots.isEmpty() || snapshots.getLast().getTimestamp() <= snapshot.getTimestamp()) {
			snapshots.addLast(snapshot);
			return true;
		}
		return false;
	}

	private void clearPreviousSnapshots() {
		long limit = System.currentTimeMillis() - since;
		while (snapshots.size() > 1 && snapshots.getFirst().getTimestamp() < limit) {
			snapshots.removeFirst();
		}
	}

	@Override
	public List<IPriceCandleSnapshot> getSnapshotHistory() {
		synchronized (snapshots) {
			clearPreviousSnapshots();

			// Copy the list
			return ImmutableList.copyOf(snapshots);
		}
	}

}
