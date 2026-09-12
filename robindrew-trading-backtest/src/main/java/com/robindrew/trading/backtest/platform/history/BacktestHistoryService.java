package com.robindrew.trading.backtest.platform.history;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderManager;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceSet;
import com.robindrew.trading.price.candle.format.pcf.source.PcfHistoryPriceSource;
import com.robindrew.trading.price.history.AbstractHistoryService;
import com.robindrew.trading.price.history.IInstrumentPriceHistory;
import java.util.Set;

public class BacktestHistoryService extends AbstractHistoryService {

    private final IPcfSourceProviderManager manager;

    public BacktestHistoryService(IPcfSourceProviderManager manager) {
        super(manager.getProvider());
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
