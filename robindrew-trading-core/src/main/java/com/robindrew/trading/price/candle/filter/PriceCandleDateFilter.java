package com.robindrew.trading.price.candle.filter;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import com.robindrew.trading.price.candle.IPriceCandle;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PriceCandleDateFilter implements IPriceCandleFilter {

    private final LocalDate from;
    private final LocalDate to;

    public PriceCandleDateFilter(LocalDate from, LocalDate to) {
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
    public boolean accept(IPriceCandle candle) {
        LocalDateTime open = toLocalDateTime(candle.getOpenTime());
        if (open.toLocalDate().isBefore(from)) {
            return false;
        }
        LocalDateTime close = toLocalDateTime(candle.getCloseTime());
        if (close.toLocalDate().isAfter(to)) {
            return false;
        }
        return true;
    }
}
