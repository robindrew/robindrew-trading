package com.robindrew.trading.price.candle.format.ptf.source;

import com.robindrew.trading.price.candle.format.IPriceSource;
import com.robindrew.trading.price.candle.tick.ITickPriceCandle;
import com.robindrew.trading.price.candle.tick.ITickPriceCandleFactory;
import java.time.LocalDate;
import java.util.List;

public interface IPtfSource extends IPriceSource, Comparable<IPtfSource> {

    /**
     * Each source contains a single day of data (at most).
     */
    LocalDate getDay();

    @Override
    List<ITickPriceCandle> read();

    List<ITickPriceCandle> read(ITickPriceCandleFactory factory);
}
