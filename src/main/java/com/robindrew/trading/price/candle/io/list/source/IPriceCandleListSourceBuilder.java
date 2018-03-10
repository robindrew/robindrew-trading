package com.robindrew.trading.price.candle.io.list.source;

import java.util.List;
import java.util.function.Supplier;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.checker.IPriceCandleChecker;
import com.robindrew.trading.price.candle.io.list.filter.IPriceCandleListFilter;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;

public interface IPriceCandleListSourceBuilder extends Supplier<IPriceCandleListSource> {

	IPriceCandleListSourceBuilder setBaseSource(IPriceCandleListSource source);

	IPriceCandleListSourceBuilder setStreamSource(IPriceCandleStreamSource source);

	IPriceCandleListSourceBuilder filterBy(IPriceCandleListFilter filter);

	IPriceCandleListSourceBuilder checkWith(IPriceCandleChecker checker);

	IPriceCandleListSourceBuilder setCandles(List<IPriceCandle> candles);

}
