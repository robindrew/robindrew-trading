package com.robindrew.trading.price.candle.format.pcf.source;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.history.IInstrumentPriceHistory;
import com.robindrew.trading.price.history.IHistoryService;

public class PcfSourceHistoryService implements IHistoryService {

	private final IPcfSourceManager manager;
	private final Map<IInstrument, IInstrumentPriceHistory> sourceMap = new ConcurrentHashMap<>();

	public PcfSourceHistoryService(IPcfSourceManager manager) {
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
			IPcfSourceSet sourceSet = manager.getSourceSet(instrument);
			sourceMap.putIfAbsent(instrument, new PcfHistoryPriceSource(instrument, sourceSet));
			source = sourceMap.get(instrument);
		}
		return source;
	}

}
