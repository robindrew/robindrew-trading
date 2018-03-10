package com.robindrew.trading.price.candle.interval;

import static com.robindrew.common.date.UnitTime.days;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.ChronoUnit.DAYS;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.robindrew.common.date.Dates;
import com.robindrew.common.date.UnitChrono;
import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.IPriceCandle;

/**
 * The weekly interval, by default starting on a SUNDAY.
 */
public class WeeklyInterval implements IPriceCandleInterval {

	private static final long intervalInMillis = days(7).toMillis();

	public static int getDaysBetween(DayOfWeek from, DayOfWeek to) {
		return getDaysBetween(from.getValue(), to.getValue());
	}

	private static int getDaysBetween(int from, int to) {
		int days = 0;
		while (from != to) {
			days++;
			from++;
			if (from == 8) {
				from = 1;
			}
		}
		return days;
	}

	private static final LocalTime ZERO = LocalTime.of(0, 0, 0, 0);

	private final int interval;
	private final DayOfWeek firstDay;

	public WeeklyInterval(int interval, DayOfWeek firstDay) {
		this.interval = interval;
		this.firstDay = Check.notNull("firstDay", firstDay);

		// TODO: support intervals
		if (interval != 1) {
			throw new IllegalArgumentException("interval=" + interval);
		}
	}

	public WeeklyInterval(int amount) {
		this(amount, SUNDAY);
	}

	@Override
	public String toString() {
		return interval + "WEEK";
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
		return new UnitChrono(interval, ChronoUnit.WEEKS);
	}

	public DayOfWeek getFirstDay() {
		return firstDay;
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
		return normalize(Dates.toLocalDateTime(timeInMillis));
	}

	public LocalDate normalize(LocalDate date) {

		// Normalize to the first day!
		int days = getDaysBetween(getFirstDay(), date.getDayOfWeek());
		if (days > 0) {
			date = date.minus(days, DAYS);
		}

		// TODO: Remove after testing ...
		if (!date.getDayOfWeek().equals(getFirstDay())) {
			throw new IllegalStateException("Normalization failed! date=" + date + ", days=" + days + ", firstDay=" + getFirstDay());
		}

		int year = date.getYear();
		int month = date.getMonthValue();
		int day = date.getDayOfMonth();
		return LocalDate.of(year, month, day);
	}

	public LocalDateTime normalize(LocalDateTime dateTime) {
		LocalDate date = normalize(dateTime.toLocalDate());
		return LocalDateTime.of(date, ZERO);
	}

}
