package com.robindrew.trading.platform;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.account.IAccountService;
import com.robindrew.trading.platform.positions.IPositionService;
import com.robindrew.trading.platform.streaming.IStreamingService;
import com.robindrew.trading.price.history.IHistoryService;

public interface ITradingPlatformServices<I extends IInstrument> extends IAccountService, IPositionService, IHistoryService, IStreamingService<I> {

}
