package com.robindrew.trading.util.date;

import java.time.LocalDate;
import java.util.Objects;

/**
 * An inclusive range of local dates.
 */
public class LocalDateRange implements ILocalDateRange {

    private final LocalDate from;
    private final LocalDate to;

    public LocalDateRange(LocalDate from, LocalDate to) {
        if (from == null) {
            throw new NullPointerException("from");
        }
        if (to == null) {
            throw new NullPointerException("to");
        }
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("from=" + from + " is after to=" + to);
        }
        this.from = from;
        this.to = to;
    }

    @Override
    public LocalDate getFrom() {
        return from;
    }

    @Override
    public LocalDate getTo() {
        return to;
    }

    @Override
    public boolean contains(LocalDate date) {
        if (date == null) {
            throw new NullPointerException("date");
        }
        return !date.isBefore(from) && !date.isAfter(to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof LocalDateRange)) {
            return false;
        }
        LocalDateRange that = (LocalDateRange) object;
        return from.equals(that.from) && to.equals(that.to);
    }

    @Override
    public String toString() {
        return from + " to " + to;
    }
}
