package com.robindrew.trading.platform.streaming;

import java.util.Set;

import com.robindrew.trading.IInstrument;

public interface IStreamingService<I extends IInstrument> extends AutoCloseable {

	boolean isSubscribed(I instrument);

	boolean supports(I instrument);

	boolean subscribe(I instrument);

	boolean unsubscribe(I instrument);

	Set<IInstrumentPriceStream<I>> getPriceStreams();

	IInstrumentPriceStream<I> getPriceStream(I instrument);

	void connect();

	boolean isConnected();

	@Override
	void close();

}
