package com.robindrew.trading.backtest.platform.streaming;

import com.robindrew.trading.backtest.IBacktestInstrument;
import com.robindrew.trading.platform.streaming.IInstrumentPriceStream;

public interface IBacktestInstrumentPriceStream extends IInstrumentPriceStream<IBacktestInstrument>, Runnable {}
