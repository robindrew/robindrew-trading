package com.robindrew.trading.platform;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.positions.IPositionService;
import com.robindrew.trading.platform.streaming.IStreamingService;
import com.robindrew.trading.price.history.IHistoryService;

public interface ITradingPlatform<I extends IInstrument> {

	IHistoryService getHistoryService();

	IStreamingService<I> getStreamingService();

	IPositionService getPositionService();
}
