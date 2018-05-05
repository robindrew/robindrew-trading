package com.robindrew.trading.platform.streaming;

import java.util.Set;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.ITradingService;

public interface IStreamingService<I extends IInstrument> extends ITradingService, AutoCloseable {

	boolean isSubscribed(I instrument);

	boolean supports(I instrument);

	boolean subscribe(I instrument);

	boolean unsubscribe(I instrument);

	Set<IInstrumentPriceStream<I>> getPriceStreams();

	IInstrumentPriceStream<I> getPriceStream(I instrument);

}
