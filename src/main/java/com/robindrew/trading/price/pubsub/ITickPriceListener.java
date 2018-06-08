package com.robindrew.trading.price.pubsub;

public interface ITickPriceListener {

	void nextEvent(ITickPriceEvent event);
}
