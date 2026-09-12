package com.robindrew.taskmanager.controller;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderManager;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceSet;
import com.robindrew.trading.price.candle.format.pcf.source.file.IPcfFileManager;
import com.robindrew.trading.provider.TradingProvider;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChartController {

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

    @GetMapping("/chart/{provider}/{instrument}/years")
    public List<Integer> getAvailableYears(@PathVariable String provider, @PathVariable String instrument) {
        IPcfSourceSet sourceSet = getSourceSet(provider, instrument);

        SortedSet<LocalDate> months = sourceSet.getMonths();
        if (months.isEmpty()) {
            throw new IllegalArgumentException("No PCF data available for instrument: " + instrument);
        }

        List<Integer> years = new ArrayList<>();
        int lastYear = Integer.MIN_VALUE;
        for (LocalDate month : months) {
            if (month.getYear() != lastYear) {
                years.add(month.getYear());
                lastYear = month.getYear();
            }
        }
        return years;
    }

    private IPcfSourceSet getSourceSet(String provider, String instrument) {
        IPcfSourceProviderManager providerManager = pcfFileManager.getProvider(TradingProvider.valueOf(provider));
        IInstrument resolved = providerManager.getInstrument(instrument);
        return providerManager.getSourceSet(resolved);
    }

    public record FromView(LocalDateTime from) {}
}
