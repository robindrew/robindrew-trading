package com.robindrew.trading.price.candle.interval;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.util.date.UnitChrono;
import java.time.LocalDateTime;

public interface IPriceInterval {

    UnitChrono getUnitChrono();

    long getLength();

    long getTimePeriod(IPriceCandle candle);

    long getTimePeriod(long timeInMillis);

    LocalDateTime getDateTime(IPriceCandle candle);

    LocalDateTime getDateTime(long timeInMillis);
}
