package com.robindrew.trading.price.history;

import java.time.LocalDateTime;
import java.util.List;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.interval.IPriceCandleInterval;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;

public interface IInstrumentPriceHistory {

	IInstrument getInstrument();

	List<IPriceCandle> getPriceCandles(LocalDateTime from, LocalDateTime to);

	IPriceCandleStreamSource getStreamSource(LocalDateTime from, LocalDateTime to);

	IPriceCandleStreamSource getStreamSource();

	List<IPriceCandle> getLatestPrices(IPriceCandleInterval interval, int count);
}
