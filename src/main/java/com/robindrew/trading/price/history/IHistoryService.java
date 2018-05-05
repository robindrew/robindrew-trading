package com.robindrew.trading.price.history;

import java.util.Set;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.ITradingService;

public interface IHistoryService extends ITradingService {

	Set<? extends IInstrument> getInstruments();

	IInstrumentPriceHistory getPriceHistory(IInstrument instrument);

}
