package com.robindrew.trading.trade.window;

import static com.robindrew.common.date.Dates.toLocalDateTime;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

import com.robindrew.trading.price.candle.IPriceCandle;

public class TradingWindow implements ITradingWindow {

	private final Map<DayOfWeek, ITradingHours> hoursPerDay = new EnumMap<>(DayOfWeek.class);

	public TradingWindow() {
		openAllDay(MONDAY);
		openAllDay(TUESDAY);
		openAllDay(WEDNESDAY);
		openAllDay(THURSDAY);
		openAllDay(FRIDAY);
		openAllDay(SATURDAY);
		openAllDay(SUNDAY);
	}

	public void closedAtWeekends() {
		closedAllDay(SATURDAY);
		closedAllDay(SUNDAY);
	}

	public void openAllDay(DayOfWeek day) {
		setOpeningHours(day, AllDay.OPEN);
	}

	public void closedAllDay(DayOfWeek day) {
		setOpeningHours(day, AllDay.CLOSED);
	}

	public void setOpeningHours(DayOfWeek day, LocalTime openTime, LocalTime closeTime) {
		setOpeningHours(day, new TradingHours(openTime, closeTime));
	}

	public void setOpeningHours(DayOfWeek day, ITradingHours hours) {
		if (day == null) {
			throw new NullPointerException("day");
		}
		if (hours == null) {
			throw new NullPointerException("hours");
		}
		hoursPerDay.put(day, hours);
	}

	@Override
	public boolean contains(LocalDateTime date) {
		DayOfWeek day = date.getDayOfWeek();
		ITradingHours hours = getHours(day);
		return hours.contains(date.toLocalTime());
	}

	public ITradingHours getHours(DayOfWeek day) {
		return hoursPerDay.get(day);
	}

	@Override
	public boolean contains(IPriceCandle candle) {
		LocalDateTime start = toLocalDateTime(candle.getOpenTime());
		LocalDateTime finish = toLocalDateTime(candle.getCloseTime());
		return contains(start) && contains(finish);
	}

}
