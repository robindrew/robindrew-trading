package com.robindrew.trading.util.date;

import java.time.LocalDate;

/**
 * An inclusive range of local dates.
 */
public interface ILocalDateRange {

    /**
     * Returns the first date in this range (inclusive).
     */
    LocalDate getFrom();

    /**
     * Returns the last date in this range (inclusive).
     */
    LocalDate getTo();

    /**
     * Returns true if the given date falls within this range.
     */
    boolean contains(LocalDate date);
}
