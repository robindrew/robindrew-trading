package com.robindrew.taskmanager.controller;

import com.robindrew.common.text.Strings;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileProviderManager;
import com.robindrew.trading.price.candle.format.pcf.source.file.ReloadablePcfFileManager;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSource;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSourceProviderManager;
import com.robindrew.trading.price.candle.format.ptf.source.PtfToPcfConverter;
import com.robindrew.trading.price.candle.format.ptf.source.file.IPtfFile;
import com.robindrew.trading.price.candle.format.ptf.source.file.IPtfFileManager;
import com.robindrew.trading.price.candle.format.ptf.source.file.IPtfFileProviderManager;
import com.robindrew.trading.provider.TradingProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "PTF", description = "Price tick format (PTF) files and their conversion to PCF")
public class PtfController {

    private final IPtfFileManager ptfFileManager;
    private final ReloadablePcfFileManager pcfFileManager;

    public PtfController(IPtfFileManager ptfFileManager, ReloadablePcfFileManager pcfFileManager) {
        this.ptfFileManager = ptfFileManager;
        this.pcfFileManager = pcfFileManager;
    }

    @GetMapping("/ptf/{provider}/{instrument}/files")
    @Operation(summary = "List PTF files", description = "Returns the daily PTF files available for the instrument")
    public List<PtfFileView> getFiles(
            @PathVariable(name = "provider") String provider, @PathVariable(name = "instrument") String instrument) {
        IPtfSourceProviderManager providerManager = ptfFileManager.getProvider(TradingProvider.valueOf(provider));
        IInstrument resolved = providerManager.getInstrument(instrument);

        List<PtfFileView> views = new ArrayList<>();
        for (IPtfSource source : providerManager.getSourceSet(resolved).getSources()) {
            IPtfFile file = (IPtfFile) source;
            views.add(new PtfFileView(
                    file.getFile().getName(),
                    file.getDay().toString(),
                    Strings.formatBytes(file.getFile().length())));
        }
        views.sort(Comparator.comparing(PtfFileView::day));
        return views;
    }

    @PostMapping("/ptf/{provider}/{instrument}/convert")
    @Operation(
            summary = "Convert instrument to PCF",
            description = "Converts the PTF files for the instrument into PCF files")
    public ConvertResponse convertToPcf(
            @PathVariable(name = "provider") String provider, @PathVariable(name = "instrument") String instrument) {
        TradingProvider tradingProvider = TradingProvider.valueOf(provider);

        IPtfFileProviderManager ptfProvider = (IPtfFileProviderManager) ptfFileManager.getProvider(tradingProvider);
        IInstrument resolved = ptfProvider.getInstrument(instrument);

        PcfFileProviderManager pcfProvider =
                new PcfFileProviderManager(pcfFileManager.getRootDirectory(), tradingProvider);

        boolean converted = new PtfToPcfConverter(ptfProvider, pcfProvider).convert(resolved);
        if (converted) {
            pcfFileManager.reload();
        }
        return new ConvertResponse(converted);
    }

    @PostMapping("/ptf/convert-all")
    @Operation(
            summary = "Convert all instruments to PCF",
            description = "Converts every instrument that does not yet have PCF data from PTF to PCF")
    public ConvertAllResponse convertAll() {
        List<Callable<Boolean>> tasks = new ArrayList<>();

        for (IPtfSourceProviderManager provider : ptfFileManager.getProviders()) {
            IPtfFileProviderManager ptfProvider = (IPtfFileProviderManager) provider;
            PcfFileProviderManager pcfProvider =
                    new PcfFileProviderManager(pcfFileManager.getRootDirectory(), provider.getProvider());

            for (IInstrument instrument : provider.getInstruments()) {
                if (pcfFileManager.hasInstrument(provider.getProvider(), instrument)) {
                    continue;
                }
                tasks.add(() -> new PtfToPcfConverter(ptfProvider, pcfProvider).convert(instrument));
            }
        }

        int converted = 0;
        if (!tasks.isEmpty()) {
            ExecutorService executor =
                    Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            try {
                for (Future<Boolean> future : executor.invokeAll(tasks)) {
                    if (future.get()) {
                        converted++;
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Conversion interrupted", e);
            } catch (ExecutionException e) {
                throw new IllegalStateException("Conversion failed", e.getCause());
            } finally {
                executor.shutdown();
            }

            if (converted > 0) {
                pcfFileManager.reload();
            }
        }

        return new ConvertAllResponse(converted);
    }

    public record PtfFileView(String filename, String day, String size) {}

    public record ConvertResponse(boolean converted) {}

    public record ConvertAllResponse(int converted) {}
}
