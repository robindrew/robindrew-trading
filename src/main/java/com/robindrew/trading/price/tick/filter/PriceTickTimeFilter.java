package com.robindrew.trading.price.tick.filter;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.robindrew.trading.price.tick.IPriceTick;

public class PriceTickTimeFilter implements IPriceTickFilter {

	private final LocalTime from;
	private final LocalTime to;

	public PriceTickTimeFilter(LocalTime from, LocalTime to) {
		if (from.isAfter(to)) {
			throw new IllegalArgumentException("from=" + from + ", to=" + to);
		}
		this.from = from;
		this.to = to;
	}

	public LocalTime getFrom() {
		return from;
	}

	public LocalTime getTo() {
		return to;
	}

	@Override
	public boolean accept(IPriceTick tick) {
		LocalDateTime open = toLocalDateTime(tick.getOpenTime());
		if (open.toLocalTime().isBefore(from)) {
			return false;
		}
		LocalDateTime close = toLocalDateTime(tick.getCloseTime());
		if (close.toLocalTime().isAfter(to)) {
			return false;
		}
		return true;
	}

}
