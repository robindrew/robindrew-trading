package com.robindrew.trading.price.candle.interval.candle;

import com.google.common.collect.ListMultimap;
import com.robindrew.trading.price.candle.IPriceCandle;
import java.time.LocalDateTime;
import java.util.List;

public interface IPriceCandleLocalDatePartitioner {

    ListMultimap<LocalDateTime, IPriceCandle> partition(List<IPriceCandle> candles);
}
