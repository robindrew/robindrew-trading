package com.robindrew.trading.price.candle.analysis;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import java.util.List;

public interface IPriceCandleAnalysis {

    void performAnalysis(IInstrument instrument, List<IPriceCandle> candles);
}
