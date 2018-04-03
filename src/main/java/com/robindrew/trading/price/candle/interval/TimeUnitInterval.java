package com.robindrew.trading.price.candle.interval;

import static com.robindrew.common.date.Dates.toChronoUnit;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import com.robindrew.common.date.Dates;
import com.robindrew.common.date.UnitChrono;
import com.robindrew.common.date.UnitTime;
import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.tick.IPriceTick;

public class TimeUnitInterval implements IPriceInterval {

	public static final TimeUnitInterval ONE_SECOND = new TimeUnitInterval(1, SECONDS);
	public static final TimeUnitInterval ONE_MINUTE = new TimeUnitInterval(1, MINUTES);
	public static final TimeUnitInterval ONE_HOUR = new TimeUnitInterval(1, HOURS);
	public static final TimeUnitInterval ONE_DAY = new TimeUnitInterval(1, DAYS);

	public static final TimeUnitInterval hours(int number) {
		return new TimeUnitInterval(number, HOURS);
	}

	public static final TimeUnitInterval minutes(int number) {
		return new TimeUnitInterval(number, MINUTES);
	}

	public static final TimeUnitInterval days(int number) {
		return new TimeUnitInterval(number, DAYS);
	}

	private final long intervalInMillis;
	private final long interval;
	private final TimeUnit unit;

	public TimeUnitInterval(UnitTime interval) {
		this(interval.getTime(), interval.getUnit());
	}

	public TimeUnitInterval(long interval, TimeUnit unit) {
		this.unit = Check.notIn("unit", unit, NANOSECONDS, MICROSECONDS);
		this.interval = interval;
		this.intervalInMillis = unit.toMillis(interval);
	}

	@Override
	public String toString() {
		switch (unit) {
			case MILLISECONDS:
				return interval + "MS";
			case SECONDS:
				return interval + "SEC";
			case MINUTES:
				return interval + "MIN";
			case HOURS:
				return interval + "HOUR";
			case DAYS:
				return interval + "DAY";
			default:
				throw new IllegalStateException("Unsupported unit: " + unit);
		}
	}

	public long getIntervalInMillis() {
		return intervalInMillis;
	}

	@Override
	public UnitChrono getUnitChrono() {
		return new UnitChrono(interval, toChronoUnit(unit));
	}

	@Override
	public long getLength() {
		return intervalInMillis;
	}

	@Override
	public long getTimePeriod(long timeInMillis) {
		return (timeInMillis / intervalInMillis) * intervalInMillis;
	}

	@Override
	public long getTimePeriod(IPriceCandle candle) {
		return getTimePeriod(candle.getOpenTime());
	}

	@Override
	public LocalDateTime getDateTime(IPriceCandle candle) {
		long timePeriod = getTimePeriod(candle);
		return Dates.toLocalDateTime(timePeriod);
	}

	@Override
	public long getTimePeriod(IPriceTick tick) {
		return getTimePeriod(tick.getTimestamp());
	}

	@Override
	public LocalDateTime getDateTime(IPriceTick tick) {
		long timePeriod = getTimePeriod(tick);
		return Dates.toLocalDateTime(timePeriod);
	}

	@Override
	public LocalDateTime getDateTime(long timeInMillis) {
		long timePeriod = getTimePeriod(timeInMillis);
		return Dates.toLocalDateTime(timePeriod);
	}

}
