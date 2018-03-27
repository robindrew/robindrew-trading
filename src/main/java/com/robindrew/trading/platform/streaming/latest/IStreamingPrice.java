package com.robindrew.trading.platform.streaming.latest;

/**
 * An asynchronous container for a streaming price.
 */
public interface IStreamingPrice {

	/**
	 * Returns the snapshot, or null if no prices received yet.
	 */
	IPriceSnapshot getSnapshot();

	/**
	 * Returns the update count.
	 */
	long getUpdateCount();

}