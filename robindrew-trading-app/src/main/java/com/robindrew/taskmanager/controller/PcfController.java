package com.robindrew.taskmanager.controller;

import com.robindrew.common.text.Strings;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderManager;
import com.robindrew.trading.price.candle.format.pcf.source.file.IPcfFile;
import com.robindrew.trading.price.candle.format.pcf.source.file.IPcfFileManager;
import com.robindrew.trading.provider.TradingProvider;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PcfController {

    private final IPcfFileManager pcfFileManager;

    public PcfController(IPcfFileManager pcfFileManager) {
        this.pcfFileManager = pcfFileManager;
    }

    @GetMapping("/pcf/{provider}/{instrument}/files")
    public List<PcfFileView> getFiles(@PathVariable String provider, @PathVariable String instrument) {
        IPcfSourceProviderManager providerManager = pcfFileManager.getProvider(TradingProvider.valueOf(provider));
        IInstrument resolved = providerManager.getInstrument(instrument);

        List<PcfFileView> views = new ArrayList<>();
        for (IPcfSource source : providerManager.getSourceSet(resolved).getSources()) {
            IPcfFile file = (IPcfFile) source;
            views.add(new PcfFileView(
                    file.getFile().getName(),
                    file.getMonth().toString(),
                    Strings.formatBytes(file.getFile().length())));
        }
        views.sort(Comparator.comparing(PcfFileView::month));
        return views;
    }

    public record PcfFileView(String filename, String month, String size) {}
}
