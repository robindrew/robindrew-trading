package com.robindrew.trading.price.window;

import com.robindrew.trading.util.date.LocalDateRange;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A monthly time window. Days are always normalized to the first day of the month (1).
 */
public class MonthlyTimeWindow extends LocalDateRange implements ITimeWindow {

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

    public MonthlyTimeWindow(LocalDate from, LocalDate to) {
        super(normalize(from), normalize(to));
    }

    public MonthlyTimeWindow(LocalDateTime from, LocalDateTime to) {
        this(from.toLocalDate(), to.toLocalDate());
    }

    public MonthlyTimeWindow() {
        this(monthPast(), monthNow());
    }

    @Override
    public boolean contains(LocalDate date) {
        date = normalize(date);
        return super.contains(date);
    }
}
