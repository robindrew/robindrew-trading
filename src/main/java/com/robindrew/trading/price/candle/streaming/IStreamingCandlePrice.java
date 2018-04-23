package com.robindrew.trading.price.candle.streaming;

import java.util.List;

/**
 * An asynchronous container for a streaming price.
 */
public interface IStreamingCandlePrice {

	/**
	 * Returns the latest snapshot, or null if no prices received yet.
	 */
	IPriceCandleSnapshot getSnapshot();

	/**
	 * Returns all available snapshots.
	 */
	List<IPriceCandleSnapshot> getSnapshotHistory();

	/**
	 * Returns the update count.
	 */
	long getUpdateCount();

}