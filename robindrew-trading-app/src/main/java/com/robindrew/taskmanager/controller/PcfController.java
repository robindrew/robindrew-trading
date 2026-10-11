package com.robindrew.taskmanager.controller;

import com.robindrew.common.text.Strings;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderManager;
import com.robindrew.trading.price.candle.format.pcf.source.file.IPcfFile;
import com.robindrew.trading.price.candle.format.pcf.source.file.IPcfFileManager;
import com.robindrew.trading.provider.TradingProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "PCF", description = "Price candle format (PCF) files")
public class PcfController {

    private static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM");

    private final IPcfFileManager pcfFileManager;

    public PcfController(IPcfFileManager pcfFileManager) {
        this.pcfFileManager = pcfFileManager;
    }

    @GetMapping("/api/v1/pcf/{provider}/{instrument}/files")
    @Operation(summary = "List PCF files", description = "Returns the monthly PCF files available for the instrument")
    public List<PcfFileView> getFiles(
            @PathVariable(name = "provider") String provider, @PathVariable(name = "instrument") String instrument) {
        IPcfSourceProviderManager providerManager = pcfFileManager.getProvider(TradingProvider.valueOf(provider));
        IInstrument resolved = providerManager.getInstrument(instrument);

        List<PcfFileView> views = new ArrayList<>();
        for (IPcfSource source : providerManager.getSourceSet(resolved).getSources()) {
            IPcfFile file = (IPcfFile) source;
            views.add(new PcfFileView(
                    file.getFile().getName(),
                    MONTH_FORMAT.format(file.getMonth()),
                    Strings.formatBytes(file.getFile().length())));
        }
        views.sort(Comparator.comparing(PcfFileView::month));
        return views;
    }

    public record PcfFileView(String filename, String month, String size) {}
}
