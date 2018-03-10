package com.robindrew.trading.price.candle.interval;

import static com.robindrew.common.date.UnitTime.days;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.robindrew.common.date.Dates;
import com.robindrew.common.date.UnitChrono;
import com.robindrew.trading.price.candle.IPriceCandle;

/**
 * The Monthly Interval.
 */
public class YearlyInterval implements IPriceCandleInterval {

	private static final long intervalInMillis = days(7).toMillis();

	private static final LocalTime ZERO = LocalTime.of(0, 0, 0, 0);

	public static LocalDate normalize(LocalDate date, int interval) {

		int year = date.getYear();
		int month = date.getMonthValue();
		int day = date.getDayOfMonth();

		// Always normalize the date to the first day of the first month of the year
		if (month != 1 || day != 1) {
			month = 1;
			day = 1;
			date = LocalDate.of(year, month, day);
		}

		// Apply the interval
		if (interval > 1) {
			int oldYear = year;
			if (year % interval > 0) {
				year = ((year / interval) + 1) * interval;
			}
			year = year - (interval - 1);
			if (year != oldYear) {
				date = LocalDate.of(year, month, day);
			}
		}

		return date;
	}

	public static LocalDateTime normalize(LocalDateTime dateTime, int interval) {
		LocalDate date = normalize(dateTime.toLocalDate(), interval);
		return LocalDateTime.of(date, ZERO);
	}

	private final int interval;

	public YearlyInterval(int interval) {
		if (interval < 1) {
			throw new IllegalArgumentException("interval=" + interval);
		}
		this.interval = interval;
	}

	@Override
	public String toString() {
		return interval + "YEAR";
	}

	public int getInterval() {
		return interval;
	}

	@Override
	public long getLength() {
		return intervalInMillis;
	}

	@Override
	public UnitChrono getUnitChrono() {
		return new UnitChrono(interval, ChronoUnit.YEARS);
	}

	@Override
	public long getTimePeriod(IPriceCandle candle) {
		return Dates.toMillis(getDateTime(candle.getOpenTime()));
	}

	@Override
	public long getTimePeriod(long timeInMillis) {
		return Dates.toMillis(getDateTime(timeInMillis));
	}

	@Override
	public LocalDateTime getDateTime(IPriceCandle candle) {
		return getDateTime(candle.getOpenTime());
	}

	@Override
	public LocalDateTime getDateTime(long timeInMillis) {
		return normalize(Dates.toLocalDateTime(timeInMillis), interval);
	}

}
