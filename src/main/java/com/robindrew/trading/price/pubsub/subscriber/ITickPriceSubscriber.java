package com.robindrew.trading.price.pubsub.subscriber;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.pubsub.ITickPriceListener;
import com.robindrew.trading.provider.ITradingProvider;

public interface ITickPriceSubscriber {

	void subscribe(ITradingProvider provider, IInstrument instrument, ITickPriceListener listener);

}
