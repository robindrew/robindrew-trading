package com.robindrew.trading.backtest.analysis;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public interface IBacktestAnalysis extends IPriceCandleStreamSink {

	IInstrument getInstrument();

}
