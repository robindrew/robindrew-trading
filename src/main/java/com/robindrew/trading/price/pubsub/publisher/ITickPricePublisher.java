package com.robindrew.trading.price.pubsub.publisher;

import com.robindrew.trading.price.pubsub.ITickPriceEvent;

public interface ITickPricePublisher {

	void publish(ITickPriceEvent event);

}
