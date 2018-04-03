package com.robindrew.trading.price.tick.filter;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.robindrew.trading.price.tick.IPriceTick;

public class PriceTickDateFilter implements IPriceTickFilter {

	private final LocalDate from;
	private final LocalDate to;

	public PriceTickDateFilter(LocalDate from, LocalDate to) {
		if (from.isAfter(to)) {
			throw new IllegalArgumentException("from=" + from + ", to=" + to);
		}
		this.from = from;
		this.to = to;
	}

	public LocalDate getFrom() {
		return from;
	}

	public LocalDate getTo() {
		return to;
	}

	@Override
	public boolean accept(IPriceTick tick) {
		LocalDateTime open = toLocalDateTime(tick.getOpenTime());
		if (open.toLocalDate().isBefore(from)) {
			return false;
		}
		LocalDateTime close = toLocalDateTime(tick.getCloseTime());
		if (close.toLocalDate().isAfter(to)) {
			return false;
		}
		return true;
	}

}
