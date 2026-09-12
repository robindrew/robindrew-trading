package com.robindrew.trading.price.candle.filter;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.util.text.TradingStrings;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceCandleConsecutiveFilter implements IPriceCandleFilter {

    private final int logThreshold;

    private IPriceCandle previous = null;
    private int skipped = 0;

    public PriceCandleConsecutiveFilter(int logThreshold) {
        if (logThreshold < 1) {
            throw new IllegalArgumentException("logThreshold=" + logThreshold);
        }
        this.logThreshold = logThreshold;
    }

    @Override
    public boolean accept(IPriceCandle next) {
        if (previous != null) {

            // Test if two candles in a row are consecutive
            if (next.getOpenTime() < previous.getCloseTime()) {
                skipped++;
                if (skipped % logThreshold == 0) {
                    log.info("Filtered out " + TradingStrings.number(skipped) + " non-consecutive candles");
                }
                return false;
            }
        }
        previous = next;
        return true;
    }
}
