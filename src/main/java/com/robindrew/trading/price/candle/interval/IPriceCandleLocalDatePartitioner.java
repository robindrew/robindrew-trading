package com.robindrew.trading.price.candle.interval;

import java.time.LocalDateTime;
import java.util.List;

import com.google.common.collect.ListMultimap;
import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleLocalDatePartitioner {

	ListMultimap<LocalDateTime, IPriceCandle> partition(List<IPriceCandle> candles);

}
