package com.robindrew.trading.dukascopy.controller;

import com.robindrew.trading.InstrumentType;
import com.robindrew.trading.dukascopy.download.DukascopyDownloadService;
import com.robindrew.trading.dukascopy.download.DukascopyDownloadService.HourResult;
import com.robindrew.trading.dukascopy.download.client.DukascopySymbol;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Dukascopy", description = "Dukascopy symbols and historic tick data downloads")
public class DukascopyController {

    private final DukascopyDownloadService downloadService;

    public DukascopyController(DukascopyDownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping("/api/v1/dukascopy/symbols")
    @Operation(
            summary = "List symbols",
            description = "Returns the Dukascopy symbols, optionally filtered by instrument type")
    public List<DukascopySymbol> getSymbols(
            @RequestParam(name = "type", required = false) InstrumentType type,
            @RequestParam(name = "refresh", defaultValue = "false") boolean refresh) {
        List<DukascopySymbol> symbols = downloadService.getSymbols(refresh);
        if (type == null) {
            return symbols;
        }
        return symbols.stream().filter(symbol -> symbol.type() == type).toList();
    }

    // Mirrors the datafeed path: /datafeed/{INSTRUMENT}/{YEAR}/{MONTH_0_INDEXED}/{DAY}/{HOUR}h_ticks.bi5
    @GetMapping("/api/v1/dukascopy/datafeed/{instrument}/{year}/{month}/{day}/{hour}h_ticks.bi5")
    @Operation(
            summary = "Download hour ticks",
            description =
                    "Fetches a single hour (UTC) of tick data from the Dukascopy datafeed, e.g. EURUSD/2024/00/15/14h_ticks.bi5. The month is zero based (00 = January)")
    public HourResult downloadHour(
            @PathVariable(name = "instrument") String instrument,
            @PathVariable(name = "year") int year,
            @PathVariable(name = "month") int month,
            @PathVariable(name = "day") int day,
            @PathVariable(name = "hour") int hour) {
        return downloadService.downloadHour(instrument, year, month, day, hour);
    }
}
