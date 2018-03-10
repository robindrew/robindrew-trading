package com.robindrew.trading.price.candle.interval;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class PriceCandleIntervals {

	public static final IPriceCandleInterval SECONDLY = interval(1, ChronoUnit.SECONDS);
	public static final IPriceCandleInterval MINUTELY = interval(1, ChronoUnit.MINUTES);
	public static final IPriceCandleInterval HOURLY = interval(1, ChronoUnit.HOURS);
	public static final IPriceCandleInterval DAILY = interval(1, ChronoUnit.DAYS);
	public static final IPriceCandleInterval WEEKLY = interval(1, ChronoUnit.WEEKS);
	public static final IPriceCandleInterval MONTHLY = interval(1, ChronoUnit.MONTHS);
	public static final IPriceCandleInterval YEARLY = interval(1, ChronoUnit.YEARS);

	public static IPriceCandleInterval interval(long amount, ChronoUnit unit) {
		switch (unit) {
			case NANOS:
				return new TimeUnitInterval(amount, TimeUnit.NANOSECONDS);
			case MICROS:
				return new TimeUnitInterval(amount, TimeUnit.MICROSECONDS);
			case MILLIS:
				return new TimeUnitInterval(amount, TimeUnit.MILLISECONDS);
			case SECONDS:
				return new TimeUnitInterval(amount, TimeUnit.SECONDS);
			case MINUTES:
				return new TimeUnitInterval(amount, TimeUnit.MINUTES);
			case HOURS:
				return new TimeUnitInterval(amount, TimeUnit.HOURS);
			case DAYS:
				return new TimeUnitInterval(amount, TimeUnit.DAYS);
			case WEEKS:
				return new WeeklyInterval((int) amount);
			case MONTHS:
				return new MonthlyInterval((int) amount);
			case YEARS:
				return new YearlyInterval((int) amount);
			default:
				throw new IllegalArgumentException("unit not supported: " + unit);
		}
	}

}
