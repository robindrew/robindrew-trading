package com.robindrew.taskmanager.controller;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderManager;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceSet;
import com.robindrew.trading.price.candle.format.pcf.source.file.IPcfFileManager;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.interval.PriceIntervals;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleStreamSourceBuilder;
import com.robindrew.trading.provider.TradingProvider;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChartController {

    private static final Set<ChronoUnit> SUPPORTED_UNITS =
            Set.of(ChronoUnit.MINUTES, ChronoUnit.HOURS, ChronoUnit.DAYS);

    private final IPcfFileManager pcfFileManager;

    public ChartController(IPcfFileManager pcfFileManager) {
        this.pcfFileManager = pcfFileManager;
    }

    @GetMapping("/chart/{provider}/{instrument}/default-from")
    public FromView getDefaultFrom(@PathVariable String provider, @PathVariable String instrument) {
        IPcfSourceSet sourceSet = getSourceSet(provider, instrument);

        SortedSet<LocalDate> months = sourceSet.getMonths();
        if (months.isEmpty()) {
            throw new IllegalArgumentException("No PCF data available for instrument: " + instrument);
        }

        return new FromView(months.last().atStartOfDay());
    }

    @GetMapping("/chart/{provider}/{instrument}/candles")
    public List<CandleView> getCandles(
            @PathVariable String provider,
            @PathVariable String instrument,
            @RequestParam LocalDateTime from,
            @RequestParam(defaultValue = "1") long amount,
            @RequestParam(defaultValue = "MINUTES") ChronoUnit unit,
            @RequestParam int count) {

        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive: " + amount);
        }
        if (!SUPPORTED_UNITS.contains(unit)) {
            throw new IllegalArgumentException("unit must be one of " + SUPPORTED_UNITS + ": " + unit);
        }
        if (count <= 0) {
            throw new IllegalArgumentException("count must be positive: " + count);
        }

        // The candle count is a count of RESULT candles (post-aggregation), so the source window needs
        // to span amount * count of the requested unit - e.g. 12 candles of 1 HOUR spans 12 hours.
        LocalDateTime to = from.plus(Math.multiplyExact(amount, count), unit);

        IPcfSourceSet sourceSet = getSourceSet(provider, instrument);
        Set<? extends IPcfSource> sources = sourceSet.getSources(from, to);

        IPriceInterval interval = PriceIntervals.interval(amount, unit);
        PriceCandleStreamSourceBuilder builder = new PriceCandleStreamSourceBuilder();
        builder.setPcfSources(sources);
        builder.setInterval(interval);
        builder.between(from, to);
        builder.limit(count);

        List<CandleView> views = new ArrayList<>();
        for (IPriceCandle candle : builder.getList()) {
            views.add(new CandleView(
                    candle.getOpenDate(),
                    candle.getMidLow(),
                    candle.getMidOpen(),
                    candle.getMidClose(),
                    candle.getMidHigh()));
        }
        return views;
    }

    private IPcfSourceSet getSourceSet(String provider, String instrument) {
        IPcfSourceProviderManager providerManager = pcfFileManager.getProvider(TradingProvider.valueOf(provider));
        IInstrument resolved = providerManager.getInstrument(instrument);
        return providerManager.getSourceSet(resolved);
    }

    public record FromView(LocalDateTime from) {}

    public record CandleView(LocalDateTime time, BigDecimal low, BigDecimal open, BigDecimal close, BigDecimal high) {}
}
