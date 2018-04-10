package com.robindrew.trading.price.tick.streaming;

/**
 * An asynchronous container for a streaming price.
 */
public interface IStreamingTickPrice {

	/**
	 * Returns the snapshot, or null if no prices received yet.
	 */
	IPriceTickSnapshot getSnapshot();

	/**
	 * Returns the update count.
	 */
	long getUpdateCount();

}