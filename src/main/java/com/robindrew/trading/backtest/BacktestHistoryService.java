package com.robindrew.trading.backtest;

import java.util.Set;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceManager;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceSet;
import com.robindrew.trading.price.candle.format.pcf.source.PcfHistoryPriceSource;
import com.robindrew.trading.price.history.IHistoryService;
import com.robindrew.trading.price.history.IInstrumentPriceHistory;

public class BacktestHistoryService implements IHistoryService {

	private final IPcfSourceManager manager;

	public BacktestHistoryService(IPcfSourceManager manager) {
		this.manager = manager;
	}

	@Override
	public Set<IInstrument> getInstruments() {
		return manager.getInstruments();
	}

	@Override
	public IInstrumentPriceHistory getPriceHistory(IInstrument instrument) {
		IPcfSourceSet set = manager.getSourceSet(instrument);
		return new PcfHistoryPriceSource(instrument, set);
	}

}
