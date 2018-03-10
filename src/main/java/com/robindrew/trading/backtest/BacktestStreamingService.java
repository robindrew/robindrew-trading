package com.robindrew.trading.backtest;

import java.util.concurrent.atomic.AtomicBoolean;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.streaming.IInstrumentPriceStream;
import com.robindrew.trading.platform.streaming.StreamingService;

public class BacktestStreamingService extends StreamingService {

	private final AtomicBoolean connected = new AtomicBoolean(false);

	@Override
	public void register(IInstrumentPriceStream stream) {
		super.registerStream(stream);
	}

	@Override
	public void unregister(IInstrument instrument) {
		super.unregisterStream(instrument);
	}

	@Override
	public void connect() {
		connected.set(true);
	}

	@Override
	public boolean isConnected() {
		return connected.get();
	}

}
