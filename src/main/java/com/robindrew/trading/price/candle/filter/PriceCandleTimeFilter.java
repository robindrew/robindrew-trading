package com.robindrew.trading.price.candle.filter;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleTimeFilter implements IPriceCandleFilter {

	private final LocalTime from;
	private final LocalTime to;

	public PriceCandleTimeFilter(LocalTime from, LocalTime to) {
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
	public boolean accept(IPriceCandle candle) {
		LocalDateTime open = toLocalDateTime(candle.getOpenTime());
		if (open.toLocalTime().isBefore(from)) {
			return false;
		}
		LocalDateTime close = toLocalDateTime(candle.getCloseTime());
		if (close.toLocalTime().isAfter(to)) {
			return false;
		}
		return true;
	}

}
