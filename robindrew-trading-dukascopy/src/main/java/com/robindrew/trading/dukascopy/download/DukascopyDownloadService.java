package com.robindrew.trading.dukascopy.download;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.dukascopy.download.client.DukascopyHourRequest;
import com.robindrew.trading.dukascopy.download.client.DukascopySymbol;
import com.robindrew.trading.dukascopy.download.client.DukascopySymbolDownloader;
import com.robindrew.trading.dukascopy.download.client.DukascopyTickDownloader;
import com.robindrew.trading.price.candle.format.ptf.PtfFormat;
import com.robindrew.trading.price.candle.format.ptf.source.file.PtfFile;
import com.robindrew.trading.price.candle.format.ptf.source.file.PtfFileProviderManager;
import com.robindrew.trading.price.candle.tick.ITickPriceCandle;
import com.robindrew.trading.provider.TradingProvider;
import java.io.File;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

/**
 * Downloads Dukascopy tick data and writes it as daily PTF files under {ptf root}/DUKASCOPY/{type}/{symbol}.
 */
@Slf4j
public class DukascopyDownloadService implements AutoCloseable {

    public static final TradingProvider PROVIDER = TradingProvider.DUKASCOPY;
    public static final int HOURS_PER_DAY = 24;

    private final DukascopySymbolDownloader symbolDownloader;
    private final DukascopyTickDownloader tickDownloader;
    private final File ptfRootDirectory;
    private final ExecutorService executor;
    private volatile List<DukascopySymbol> symbols = null;

    public DukascopyDownloadService(
            DukascopySymbolDownloader symbolDownloader,
            DukascopyTickDownloader tickDownloader,
            File ptfRootDirectory,
            int threads) {
        this.symbolDownloader = Check.notNull("symbolDownloader", symbolDownloader);
        this.tickDownloader = Check.notNull("tickDownloader", tickDownloader);
        this.ptfRootDirectory = Check.notNull("ptfRootDirectory", ptfRootDirectory);
        this.executor = Executors.newFixedThreadPool(threads);
    }

    @Override
    public void close() {
        executor.shutdownNow();
    }

    public List<DukascopySymbol> getSymbols(boolean refresh) {
        List<DukascopySymbol> list = symbols;
        if (list == null || refresh) {
            list = symbolDownloader.download(null);
            symbols = list;
            log.info("Loaded {} Dukascopy symbols", list.size());
        }
        return list;
    }

    public DukascopySymbol getSymbol(String symbol) {
        for (DukascopySymbol available : getSymbols(false)) {
            if (available.symbol().equalsIgnoreCase(symbol) || available.name().equalsIgnoreCase(symbol)) {
                return available;
            }
        }
        throw new IllegalArgumentException("Dukascopy symbol not found: '" + symbol + "'");
    }

    public File getDirectory(DukascopySymbol symbol) {
        return PtfFileProviderManager.getDirectory(PROVIDER, symbol.toInstrument(), ptfRootDirectory);
    }

    /**
     * Download the given number of complete days (UTC), ending yesterday.
     * @param overwrite if false, days that already have a PTF file are skipped.
     */
    public List<DayResult> download(String symbolName, int days, boolean overwrite) {
        Check.min("days", days, 1);
        DukascopySymbol symbol = getSymbol(symbolName);

        LocalDate yesterday = LocalDate.now(ZoneOffset.UTC).minusDays(1);
        List<DayResult> results = new ArrayList<>();
        for (int i = days - 1; i >= 0; i--) {
            results.add(download(symbol, yesterday.minusDays(i), overwrite));
        }
        return results;
    }

    /**
     * Download the ticks for a single hour (UTC), using the same date/time components as the datafeed URL.
     * @param month0 the zero based month (January = 0), as used in the datafeed URL.
     */
    public HourResult downloadHour(String symbolName, int year, int month0, int dayOfMonth, int hour) {
        if (month0 < 0 || month0 > 11) {
            throw new IllegalArgumentException("month must be between 00 and 11 (zero based): " + month0);
        }
        if (hour < 0 || hour >= HOURS_PER_DAY) {
            throw new IllegalArgumentException("hour must be between 00 and 23: " + hour);
        }
        LocalDate day;
        try {
            day = LocalDate.of(year, month0 + 1, dayOfMonth);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        DukascopySymbol symbol = getSymbol(symbolName);
        DukascopyHourRequest request = new DukascopyHourRequest(symbol, day, hour);
        String url = request.getUrl();
        List<ITickPriceCandle> ticks = tickDownloader.download(request);
        log.info("[{}] {} {}h fetched {} ticks", symbol.symbol(), day, hour, ticks.size());

        List<Tick> views = new ArrayList<>(ticks.size());
        for (ITickPriceCandle tick : ticks) {
            views.add(new Tick(
                    Instant.ofEpochMilli(tick.getTimestamp()),
                    BigDecimal.valueOf(tick.getBidPrice(), symbol.decimalPlaces()),
                    BigDecimal.valueOf(tick.getAskPrice(), symbol.decimalPlaces())));
        }
        return new HourResult(symbol, url, views.size(), views);
    }

    public DayResult download(DukascopySymbol symbol, LocalDate day, boolean overwrite) {
        IInstrument instrument = symbol.toInstrument();
        File file = new File(getDirectory(symbol), PtfFormat.getFilename(day));
        if (file.exists() && !overwrite) {
            log.info("[{}] {} already downloaded: {}", instrument, day, file);
            return new DayResult(day, DayStatus.SKIPPED, 0, file.getAbsolutePath());
        }

        List<ITickPriceCandle> ticks = downloadDay(symbol, day);
        if (ticks.isEmpty()) {
            log.info("[{}] {} no ticks", instrument, day);
            return new DayResult(day, DayStatus.NO_DATA, 0, null);
        }

        new PtfFile(file, day).write(ticks);
        log.info("[{}] {} wrote {} ticks to {}", instrument, day, ticks.size(), file);
        return new DayResult(day, DayStatus.WRITTEN, ticks.size(), file.getAbsolutePath());
    }

    private List<ITickPriceCandle> downloadDay(DukascopySymbol symbol, LocalDate day) {
        List<Future<List<ITickPriceCandle>>> hours = new ArrayList<>(HOURS_PER_DAY);
        for (int hour = 0; hour < HOURS_PER_DAY; hour++) {
            DukascopyHourRequest request = new DukascopyHourRequest(symbol, day, hour);
            hours.add(executor.submit(() -> tickDownloader.download(request)));
        }

        List<ITickPriceCandle> ticks = new ArrayList<>();
        try {
            for (Future<List<ITickPriceCandle>> hour : hours) {
                ticks.addAll(hour.get());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Download interrupted: " + symbol.symbol() + " " + day, e);
        } catch (ExecutionException e) {
            // Never write a partial day
            hours.forEach(hour -> hour.cancel(true));
            throw new IllegalStateException("Download failed: " + symbol.symbol() + " " + day, e.getCause());
        }

        ticks.sort(Comparator.comparingLong(ITickPriceCandle::getTimestamp));
        return ticks;
    }

    public enum DayStatus {
        WRITTEN,
        SKIPPED,
        NO_DATA
    }

    public record DayResult(LocalDate day, DayStatus status, int ticks, String file) {}

    public record HourResult(DukascopySymbol symbol, String url, int tickCount, List<Tick> ticks) {}

    public record Tick(Instant time, BigDecimal bid, BigDecimal ask) {}
}
