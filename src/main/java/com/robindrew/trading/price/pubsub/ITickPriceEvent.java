package com.robindrew.trading.price.pubsub;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.tick.ITickPriceCandle;
import com.robindrew.trading.provider.ITradingProvider;

public interface ITickPriceEvent {

	ITradingProvider getProvider();

	IInstrument getInstrument();

	ITickPriceCandle getCandle();

}
