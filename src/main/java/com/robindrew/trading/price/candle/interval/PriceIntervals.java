package com.robindrew.trading.price.candle.interval;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class PriceIntervals {

	public static final IPriceInterval SECONDLY = interval(1, ChronoUnit.SECONDS);
	public static final IPriceInterval MINUTELY = interval(1, ChronoUnit.MINUTES);
	public static final IPriceInterval HOURLY = interval(1, ChronoUnit.HOURS);
	public static final IPriceInterval DAILY = interval(1, ChronoUnit.DAYS);
	public static final IPriceInterval WEEKLY = interval(1, ChronoUnit.WEEKS);
	public static final IPriceInterval MONTHLY = interval(1, ChronoUnit.MONTHS);
	public static final IPriceInterval YEARLY = interval(1, ChronoUnit.YEARS);

	public static IPriceInterval yearly(long years) {
		return interval(years, ChronoUnit.YEARS);
	}

	public static IPriceInterval monthly(long months) {
		return interval(months, ChronoUnit.MONTHS);
	}

	public static IPriceInterval weekly(long weeks) {
		return interval(weeks, ChronoUnit.WEEKS);
	}

	public static IPriceInterval daily(long days) {
		return interval(days, ChronoUnit.DAYS);
	}

	public static IPriceInterval hourly(long hours) {
		return interval(hours, ChronoUnit.HOURS);
	}

	public static IPriceInterval minutely(long minutes) {
		return interval(minutes, ChronoUnit.MINUTES);
	}

	public static IPriceInterval secondly(long seconds) {
		return interval(seconds, ChronoUnit.SECONDS);
	}

	public static IPriceInterval interval(long amount, ChronoUnit unit) {
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
