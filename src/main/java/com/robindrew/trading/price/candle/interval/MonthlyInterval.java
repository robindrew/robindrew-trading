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
public class MonthlyInterval implements IPriceCandleInterval {

	private static final long intervalInMillis = days(7).toMillis();

	private static final LocalTime ZERO = LocalTime.of(0, 0, 0, 0);

	public static LocalDate normalize(LocalDate date, int interval) {

		int year = date.getYear();
		int month = date.getMonthValue();
		int day = date.getDayOfMonth();

		// Always normalize the date to the first day of the month
		if (day != 1) {
			day = 1;
			date = LocalDate.of(year, month, day);
		}

		// Apply the interval
		if (interval > 1) {
			int oldMonth = month;
			if (month % interval > 0) {
				month = ((month / interval) + 1) * interval;
			}
			month = month - (interval - 1);
			if (month != oldMonth) {
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

	public MonthlyInterval(int interval) {
		if (interval < 1 || interval > 12) {
			throw new IllegalArgumentException("interval=" + interval);
		}
		if (12 % interval != 0) {
			throw new IllegalArgumentException("interval=" + interval);
		}
		this.interval = interval;
	}

	public int getInterval() {
		return interval;
	}

	@Override
	public long getLength() {
		return intervalInMillis;
	}

	@Override
	public String toString() {
		return interval + "MON";
	}

	@Override
	public UnitChrono getUnitChrono() {
		return new UnitChrono(interval, ChronoUnit.MONTHS);
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
