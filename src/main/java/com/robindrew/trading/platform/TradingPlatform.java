package com.robindrew.trading.platform;

import com.robindrew.trading.platform.account.IAccountService;
import com.robindrew.trading.platform.positions.IPositionService;
import com.robindrew.trading.platform.streaming.IStreamingService;
import com.robindrew.trading.price.history.IHistoryService;
import com.robindrew.trading.provider.ITradingInstrument;

public abstract class TradingPlatform<I extends ITradingInstrument> implements ITradingPlatform<I> {

	@Override
	public IAccountService getAccountService() {
		throw new UnsupportedOperationException("Account service not available");
	}

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
