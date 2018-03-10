package com.robindrew.trading.price.candle.charts;

import java.util.List;
import java.util.function.Supplier;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleChartBuilder extends Supplier<IPriceCandleChart> {

	IPriceCandleChartBuilder setInstrument(IInstrument instrument);

	IPriceCandleChartBuilder setCandles(List<IPriceCandle> candles);

	IPriceCandleChartBuilder setDimensions(int width, int height);

	IPriceCandleChartBuilder setDateAxisLabel(String label);

	IPriceCandleChartBuilder setPriceAxisLabel(String label);

}
