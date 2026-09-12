package com.robindrew.trading.price.candle.indicator;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import java.util.Collections;
import java.util.List;

public class TestIndicator extends AbstractIndicator {

    private List<IPriceCandle> latest = Collections.emptyList();

    public TestIndicator(IPriceInterval interval, int capacity) {
        super("TestIndicator", interval, capacity);
    }

    public List<IPriceCandle> getLatest() {
        return latest;
    }

    @Override
    protected void calculate(List<IPriceCandle> list) {
        latest = list;
    }

    @Override
    public boolean isAvailable() {
        return !latest.isEmpty();
    }
}
