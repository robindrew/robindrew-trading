package com.robindrew.trading.price.candle.analysis;

import java.util.List;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleAnalysis {

	void performAnalysis(IInstrument instrument, List<IPriceCandle> candles);

}
