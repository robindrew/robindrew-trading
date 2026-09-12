package com.robindrew.trading.price.history;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.ITradingService;
import java.util.Set;

public interface IHistoryService extends ITradingService {

    Set<? extends IInstrument> getInstruments();

    IInstrumentPriceHistory getPriceHistory(IInstrument instrument);
}
