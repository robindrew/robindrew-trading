package com.robindrew.trading.price.history;

import java.time.LocalDateTime;
import java.util.List;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;

public interface IInstrumentPriceHistory {

	IInstrument getInstrument();

	List<? extends IPriceCandle> getPriceCandles(LocalDateTime from, LocalDateTime to);

	IPriceCandleStreamSource getStreamSource(LocalDateTime from, LocalDateTime to);

	IPriceCandleStreamSource getStreamSource();

	List<? extends IPriceCandle> getLatestPrices(IPriceInterval interval, int count);
}
