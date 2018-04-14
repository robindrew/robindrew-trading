package com.robindrew.trading.price.candle.format.ptf.source;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.history.IHistoryService;
import com.robindrew.trading.price.history.IInstrumentPriceHistory;

public class PtfSourceHistoryService implements IHistoryService {

	private final IPtfSourceManager manager;
	private final Map<IInstrument, IInstrumentPriceHistory> sourceMap = new ConcurrentHashMap<>();

	public PtfSourceHistoryService(IPtfSourceManager manager) {
		this.manager = Check.notNull("manager", manager);
	}

	@Override
	public Set<IInstrument> getInstruments() {
		return manager.getInstruments();
	}

	@Override
	public IInstrumentPriceHistory getPriceHistory(IInstrument instrument) {
		IInstrumentPriceHistory source = sourceMap.get(instrument);
		if (source == null) {
			IPtfSourceSet sourceSet = manager.getSourceSet(instrument);
			sourceMap.putIfAbsent(instrument, new PtfHistoryPriceSource(instrument, sourceSet));
			source = sourceMap.get(instrument);
		}
		return source;
	}

}
