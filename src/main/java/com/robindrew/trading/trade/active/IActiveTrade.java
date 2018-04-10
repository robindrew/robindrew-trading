package com.robindrew.trading.trade.active;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.io.stream.sink.IPriceTickStreamSink;

public interface IActiveTrade extends IPriceTickStreamSink, Runnable {

	IInstrument getInstrument();

	IPriceTick getLatestPrice();

}
