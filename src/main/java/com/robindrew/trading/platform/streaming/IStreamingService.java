package com.robindrew.trading.platform.streaming;

import java.util.Set;

import com.robindrew.trading.IInstrument;

public interface IStreamingService extends AutoCloseable {

	boolean supports(IInstrument instrument);

	Set<IInstrumentPriceStream> getPriceStreams();

	IInstrumentPriceStream getPriceStream(IInstrument instrument);

	void register(IInstrumentPriceStream stream);

	void unregister(IInstrument instrument);

	void connect();

	boolean isConnected();

	@Override
	void close();

}
