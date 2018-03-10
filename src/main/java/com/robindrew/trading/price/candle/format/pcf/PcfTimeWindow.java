package com.robindrew.trading.price.candle.format.pcf;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.robindrew.common.date.range.LocalDateRange;

/**
 * A PCF time window is monthly. Days are always normalized to the first day of the month (1).
 */
public class PcfTimeWindow extends LocalDateRange implements IPcfTimeWindow {

	public static LocalDate normalize(LocalDate date) {
		if (date.getDayOfMonth() == 1) {
			return date;
		}
		return LocalDate.of(date.getYear(), date.getMonth(), 1);
	}

	private static LocalDate monthNow() {
		return normalize(LocalDate.now());
	}

	private static LocalDate monthPast() {
		return LocalDate.of(1990, 1, 1);
	}

	public PcfTimeWindow(LocalDate from, LocalDate to) {
		super(normalize(from), normalize(to));
	}

	public PcfTimeWindow(LocalDateTime from, LocalDateTime to) {
		this(from.toLocalDate(), to.toLocalDate());
	}

	public PcfTimeWindow() {
		this(monthPast(), monthNow());
	}

	@Override
	public boolean contains(LocalDate date) {
		date = normalize(date);
		return super.contains(date);
	}

}
