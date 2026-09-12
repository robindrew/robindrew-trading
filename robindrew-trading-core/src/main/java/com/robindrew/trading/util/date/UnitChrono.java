package com.robindrew.trading.util.date;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * A quantity of a {@link ChronoUnit}, for example "5 MINUTES".
 */
public class UnitChrono {

    private final long interval;
    private final ChronoUnit unit;

    public UnitChrono(long interval, ChronoUnit unit) {
        if (interval < 1) {
            throw new IllegalArgumentException("interval=" + interval);
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        this.interval = interval;
        this.unit = unit;
    }

    public long getInterval() {
        return interval;
    }

    public ChronoUnit getUnit() {
        return unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(interval, unit);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof UnitChrono)) {
            return false;
        }
        UnitChrono that = (UnitChrono) object;
        return interval == that.interval && unit == that.unit;
    }

    @Override
    public String toString() {
        return interval + " " + unit;
    }
}
