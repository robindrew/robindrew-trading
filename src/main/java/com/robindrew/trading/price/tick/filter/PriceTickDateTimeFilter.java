package com.robindrew.trading.price.tick.filter;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import java.time.LocalDateTime;

import com.robindrew.trading.price.tick.IPriceTick;

public class PriceTickDateTimeFilter implements IPriceTickFilter {

	private final LocalDateTime from;
	private final LocalDateTime to;

	public PriceTickDateTimeFilter(LocalDateTime from, LocalDateTime to) {
		if (from.isAfter(to)) {
			throw new IllegalArgumentException("from=" + from + ", to=" + to);
		}
		this.from = from;
		this.to = to;
	}

	public LocalDateTime getFrom() {
		return from;
	}

	public LocalDateTime getTo() {
		return to;
	}

	@Override
	public boolean accept(IPriceTick tick) {
		LocalDateTime open = toLocalDateTime(tick.getOpenTime());
		if (open.isBefore(from)) {
			return false;
		}
		LocalDateTime close = toLocalDateTime(tick.getCloseTime());
		if (close.isAfter(to)) {
			return false;
		}
		return true;
	}

}
