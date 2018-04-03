package com.robindrew.trading.price.candle.interval;

import java.time.LocalDateTime;

import com.robindrew.common.date.UnitChrono;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.tick.IPriceTick;

public interface IPriceInterval {

	UnitChrono getUnitChrono();

	long getLength();

	long getTimePeriod(IPriceCandle candle);

	long getTimePeriod(IPriceTick tick);

	long getTimePeriod(long timeInMillis);

	LocalDateTime getDateTime(IPriceCandle candle);

	LocalDateTime getDateTime(IPriceTick tick);

	LocalDateTime getDateTime(long timeInMillis);

}