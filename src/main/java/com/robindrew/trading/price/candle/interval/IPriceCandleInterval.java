package com.robindrew.trading.price.candle.interval;

import java.time.LocalDateTime;

import com.robindrew.common.date.UnitChrono;
import com.robindrew.trading.price.candle.IPriceCandle;

public interface IPriceCandleInterval {

	UnitChrono getUnitChrono();

	long getLength();

	long getTimePeriod(IPriceCandle candle);

	long getTimePeriod(long timeInMillis);

	LocalDateTime getDateTime(IPriceCandle candle);

	LocalDateTime getDateTime(long timeInMillis);

}