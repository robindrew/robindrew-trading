package com.robindrew.trading.platform;

import com.robindrew.trading.platform.account.IAccountService;
import com.robindrew.trading.platform.positions.IPositionService;
import com.robindrew.trading.platform.streaming.IStreamingService;
import com.robindrew.trading.price.history.IHistoryService;
import com.robindrew.trading.provider.ITradingInstrument;

public interface ITradingPlatform<I extends ITradingInstrument> {

	IAccountService getAccountService();

	IHistoryService getHistoryService();

	IStreamingService<I> getStreamingService();

	IPositionService getPositionService();
}
