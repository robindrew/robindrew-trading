package com.robindrew.trading.price.history;

import java.util.Set;

import com.robindrew.trading.IInstrument;

public interface IHistoryService {

	Set<? extends IInstrument> getInstruments();

	IInstrumentPriceHistory getPriceHistory(IInstrument instrument);

}
