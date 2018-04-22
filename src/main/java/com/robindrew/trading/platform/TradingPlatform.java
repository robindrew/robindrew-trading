package com.robindrew.trading.platform;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.positions.IPositionService;
import com.robindrew.trading.platform.streaming.IStreamingService;
import com.robindrew.trading.price.history.IHistoryService;

public abstract class TradingPlatform<I extends IInstrument> implements ITradingPlatform<I> {

	@Override
	public IHistoryService getHistoryService() {
		throw new UnsupportedOperationException("History service not available");
	}

	@Override
	public IStreamingService<I> getStreamingService() {
		throw new UnsupportedOperationException("Streaming service not available");
	}

	@Override
	public IPositionService getPositionService() {
		throw new UnsupportedOperationException("Position service not available");
	}

}
