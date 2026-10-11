package com.robindrew.taskmanager.controller;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderManager;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceSet;
import com.robindrew.trading.price.candle.format.pcf.source.file.IPcfFileManager;
import com.robindrew.trading.provider.TradingProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Chart", description = "Metadata for initialising price charts")
public class ChartController {

    private final IPcfFileManager pcfFileManager;

    public ChartController(IPcfFileManager pcfFileManager) {
        this.pcfFileManager = pcfFileManager;
    }

    @GetMapping("/api/v1/chart/{provider}/{instrument}/default-from")
    @Operation(
            summary = "Get default chart start",
            description = "Returns the start of the most recent month with PCF data for the instrument")
    public FromView getDefaultFrom(
            @PathVariable(name = "provider") String provider, @PathVariable(name = "instrument") String instrument) {
        IPcfSourceSet sourceSet = getSourceSet(provider, instrument);

        SortedSet<LocalDate> months = sourceSet.getMonths();
        if (months.isEmpty()) {
            throw new IllegalArgumentException("No PCF data available for instrument: " + instrument);
        }

        return new FromView(months.last().atStartOfDay());
    }

    @GetMapping("/api/v1/chart/{provider}/{instrument}/years")
    @Operation(
            summary = "Get available years",
            description = "Returns the years for which PCF data is available for the instrument")
    public List<Integer> getAvailableYears(
            @PathVariable(name = "provider") String provider, @PathVariable(name = "instrument") String instrument) {
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
