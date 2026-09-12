package com.robindrew.taskmanager.controller;

import com.robindrew.common.date.Dates;
import com.robindrew.taskmanager.cache.PcfCandleCache;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandles;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderManager;
import com.robindrew.trading.price.candle.format.pcf.source.file.IPcfFileManager;
import com.robindrew.trading.price.candle.interval.TimeUnitInterval;
import com.robindrew.trading.provider.TradingProvider;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CandleChartController {

    // A gap of an hour or more of missed ticks (whether within a day or spanning many, e.g. a
    // weekend) is compressed out of the chart entirely and marked with a GAP point in its place.
    private static final long GAP_THRESHOLD_MILLIS = TimeUnit.HOURS.toMillis(1);

    private final IPcfFileManager pcfFileManager;
    private final PcfCandleCache candleCache;

    public CandleChartController(IPcfFileManager pcfFileManager, PcfCandleCache candleCache) {
        this.pcfFileManager = pcfFileManager;
        this.candleCache = candleCache;
    }

    @GetMapping("/candlechart/{provider}/{instrument}/candles")
    public List<ChartPointView> getCandles(
            @PathVariable String provider,
            @PathVariable String instrument,
            @RequestParam LocalDateTime from,
            @RequestParam int resolutionMinutes,
            @RequestParam(defaultValue = "100") int count) {

        if (resolutionMinutes <= 0) {
            throw new IllegalArgumentException("resolutionMinutes must be positive: " + resolutionMinutes);
        }
        if (count <= 0) {
            throw new IllegalArgumentException("count must be positive: " + count);
        }

        TradingProvider tradingProvider = TradingProvider.valueOf(provider);
        IPcfSourceProviderManager providerManager = pcfFileManager.getProvider(tradingProvider);
        IInstrument resolved = providerManager.getInstrument(instrument);

        // All of this instrument's PCF candles, cached in memory after the first request.
        List<IPriceCandle> candles = candleCache.getCandles(tradingProvider, resolved);

        TimeUnitInterval interval = new TimeUnitInterval(resolutionMinutes, TimeUnit.MINUTES);
        return buildChartPoints(candles, from, interval, count);
    }

    // Aggregates raw 1-minute candles from "from" onward into "resolution" candles, skipping any gap
    // of GAP_THRESHOLD_MILLIS or more between consecutive raw candles that ALSO crosses into a new
    // resolution period (rather than rendering an empty stretch of chart for it) and recording a GAP
    // point in its place. A gap that stays within the same period (e.g. a weekend inside one weekly,
    // or even daily, candle) does not fragment or mark it - only merging matters there, exactly as if
    // there were no gap - since the candle covers that whole period regardless of what's missing
    // inside it. "count" bounds the number of CANDLE points only - GAP points are extra, so the chart
    // always ends up with exactly "count" candles (data permitting) regardless of how many gaps fall
    // within the range. Without that distinction, a GAP eating into the same budget as candles would
    // make the returned candle count fluctuate between requests, which visibly rescales/shifts the
    // whole chart client-side.
    private List<ChartPointView> buildChartPoints(
            List<IPriceCandle> candles, LocalDateTime from, TimeUnitInterval interval, int count) {
        int startIndex = findStartIndex(candles, Dates.toMillis(from));

        List<ChartPointView> points = new ArrayList<>();
        int candleCount = 0;
        Long lastCandleCloseTime = null;
        Long currentPeriod = null;
        IPriceCandle currentAggregate = null;

        for (int i = startIndex; i < candles.size() && candleCount < count; i++) {
            IPriceCandle candle = candles.get(i);
            long period = interval.getTimePeriod(candle);

            if (currentPeriod != null && period == currentPeriod) {
                currentAggregate = PriceCandles.merge(currentAggregate, candle);
            } else {
                boolean hasGap = lastCandleCloseTime != null
                        && candle.getOpenTime() - lastCandleCloseTime >= GAP_THRESHOLD_MILLIS;

                if (currentAggregate != null) {
                    points.add(ChartPointView.candle(currentAggregate));
                    candleCount++;
                    if (candleCount >= count) {
                        break;
                    }
                }
                if (hasGap) {
                    points.add(ChartPointView.gap());
                }
                currentAggregate = candle;
                currentPeriod = period;
            }

            lastCandleCloseTime = candle.getCloseTime();
        }

        if (candleCount < count && currentAggregate != null) {
            points.add(ChartPointView.candle(currentAggregate));
        }

        return points;
    }

    // Candles are sorted ascending by open time - binary search for the first one at/after "from".
    private int findStartIndex(List<IPriceCandle> candles, long fromMillis) {
        int low = 0;
        int high = candles.size();
        while (low < high) {
            int mid = (low + high) >>> 1;
            if (candles.get(mid).getOpenTime() < fromMillis) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    public record ChartPointView(
            String type, LocalDateTime time, BigDecimal low, BigDecimal open, BigDecimal close, BigDecimal high) {

        static ChartPointView candle(IPriceCandle candle) {
            return new ChartPointView(
                    "CANDLE",
                    candle.getOpenDate(),
                    candle.getMidLow(),
                    candle.getMidOpen(),
                    candle.getMidClose(),
                    candle.getMidHigh());
        }

        static ChartPointView gap() {
            return new ChartPointView("GAP", null, null, null, null, null);
        }
    }
}
