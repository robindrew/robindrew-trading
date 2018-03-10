package com.robindrew.trading.price.candle.io.list.source;

import java.util.List;

import com.robindrew.common.io.INamedCloseable;
import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleListSource extends INamedCloseable {

	List<IPriceCandle> getNextCandles();

}
