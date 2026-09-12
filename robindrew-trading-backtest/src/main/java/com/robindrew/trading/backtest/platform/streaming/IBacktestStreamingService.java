package com.robindrew.trading.backtest.platform.streaming;

import com.robindrew.trading.backtest.IBacktestInstrument;
import com.robindrew.trading.platform.streaming.IStreamingService;

public interface IBacktestStreamingService extends IStreamingService<IBacktestInstrument> {

    @Override
    IBacktestInstrumentPriceStream getPriceStream(IBacktestInstrument instrument);
}
