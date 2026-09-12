package com.robindrew.trading.backtest.platform.streaming;

import com.robindrew.common.util.Check;
import com.robindrew.trading.backtest.IBacktestInstrument;
import com.robindrew.trading.backtest.platform.history.BacktestHistoryService;
import com.robindrew.trading.platform.streaming.AbstractStreamingService;
import com.robindrew.trading.price.history.IInstrumentPriceHistory;

public class BacktestStreamingService extends AbstractStreamingService<IBacktestInstrument>
        implements IBacktestStreamingService {

    private final BacktestHistoryService history;

    public BacktestStreamingService(BacktestHistoryService history) {
        super(history.getProvider());
        this.history = Check.notNull("history", history);
    }

    @Override
    public boolean subscribeToPrices(IBacktestInstrument instrument) {
        IInstrumentPriceHistory priceHistory = history.getPriceHistory(instrument);
        BacktestInstrumentPriceStream stream = new BacktestInstrumentPriceStream(instrument, priceHistory);
        registerStream(stream);
        return true;
    }

    @Override
    public boolean unsubscribeFromPrices(IBacktestInstrument instrument) {
        unregisterStream(instrument);
        return true;
    }

    @Override
    public IBacktestInstrumentPriceStream getPriceStream(IBacktestInstrument instrument) {
        return (IBacktestInstrumentPriceStream) super.getPriceStream(instrument);
    }
}
