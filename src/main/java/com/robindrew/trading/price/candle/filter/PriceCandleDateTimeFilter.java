package com.robindrew.trading.price.candle.filter;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import java.time.LocalDateTime;

import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleDateTimeFilter implements IPriceCandleFilter {

	private final LocalDateTime from;
	private final LocalDateTime to;

	public PriceCandleDateTimeFilter(LocalDateTime from, LocalDateTime to) {
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
	public boolean accept(IPriceCandle candle) {
		LocalDateTime open = toLocalDateTime(candle.getOpenTime());
		if (open.isBefore(from)) {
			return false;
		}
		LocalDateTime close = toLocalDateTime(candle.getCloseTime());
		if (close.isAfter(to)) {
			return false;
		}
		return true;
	}

}
