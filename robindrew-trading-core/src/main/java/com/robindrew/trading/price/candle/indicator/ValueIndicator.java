package com.robindrew.trading.price.candle.indicator;

import com.robindrew.trading.price.candle.interval.IPriceInterval;
import java.util.concurrent.atomic.AtomicLong;

public abstract class ValueIndicator extends AbstractIndicator {

    private final AtomicLong value = new AtomicLong(0);

    protected ValueIndicator(String name, IPriceInterval interval, int capacity) {
        super(name, interval, capacity);
    }

    public long getValue() {
        if (!isAvailable()) {
            throw new IllegalStateException("value not set");
        }
        return value.get();
    }

    protected void setValue(long value) {
        this.value.set(value);
        setAvailable(true);
    }
}
