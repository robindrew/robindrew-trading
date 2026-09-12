package com.robindrew.taskmanager.cache;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandles;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderManager;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceSet;
import com.robindrew.trading.price.candle.format.pcf.source.file.IPcfFileManager;
import com.robindrew.trading.provider.ITradingProvider;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Caches every PCF (1-minute) candle for an instrument's entire history in memory, on first access,
// so repeated chart requests (panning/zooming/scrolling) don't re-read the PCF files from disk each
// time. Entries live for the lifetime of the process - there's no eviction, and no invalidation if the
// underlying PCF files change (e.g. a new PTF -> PCF conversion) while the server is running.
public class PcfCandleCache {

    private final IPcfFileManager pcfFileManager;
    private final Map<String, List<IPriceCandle>> cache = new ConcurrentHashMap<>();

    public PcfCandleCache(IPcfFileManager pcfFileManager) {
        this.pcfFileManager = Check.notNull("pcfFileManager", pcfFileManager);
    }

    public List<IPriceCandle> getCandles(ITradingProvider provider, IInstrument instrument) {
        String key = provider.name() + "|" + instrument.getName();
        return cache.computeIfAbsent(key, ignored -> loadCandles(provider, instrument));
    }

    private List<IPriceCandle> loadCandles(ITradingProvider provider, IInstrument instrument) {
        IPcfSourceProviderManager providerManager = pcfFileManager.getProvider(provider);
        IPcfSourceSet sourceSet = providerManager.getSourceSet(instrument);
        return PriceCandles.drainToList(sourceSet.asStreamSource());
    }
}
