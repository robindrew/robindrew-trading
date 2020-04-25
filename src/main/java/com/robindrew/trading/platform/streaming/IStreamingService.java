package com.robindrew.trading.platform.streaming;

import java.util.Set;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.ITradingService;

public interface IStreamingService<I extends IInstrument> extends ITradingService, AutoCloseable {

	boolean isSubscribedInstrument(I instrument);

	boolean canStreamPrices(I instrument);

	boolean subscribeToPrices(I instrument);

	boolean unsubscribeFromPrices(I instrument);

	Set<I> getSubscribedInstruments();

	Set<IInstrumentPriceStream<I>> getPriceStreams();

	IInstrumentPriceStream<I> getPriceStream(I instrument);

	void close();

}
