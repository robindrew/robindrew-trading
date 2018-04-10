package com.robindrew.trading.price.candle.streaming;

/**
 * An asynchronous container for a streaming price.
 */
public interface IStreamingCandlePrice {

	/**
	 * Returns the snapshot, or null if no prices received yet.
	 */
	IPriceCandleSnapshot getSnapshot();

	/**
	 * Returns the update count.
	 */
	long getUpdateCount();

}