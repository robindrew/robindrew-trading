package com.robindrew.trading.price.candle.format.ptf.source;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.PriceCandles;
import com.robindrew.trading.price.candle.format.pcf.source.file.IPcfFileProviderManager;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileProviderManager;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileStreamSink;
import com.robindrew.trading.price.candle.format.ptf.source.file.IPtfFileProviderManager;
import com.robindrew.trading.price.candle.format.ptf.source.file.PtfFileProviderManager;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.interval.PriceIntervals;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleIntervalStreamSource;
import com.robindrew.trading.provider.ITradingProvider;
import com.robindrew.trading.provider.TradingProvider;
import com.robindrew.trading.util.lang.Args;
import java.io.File;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PtfToPcfConverter {

    public static void main(String[] array) {
        Args args = new Args(array);

        String fromDir = args.get("-f"); // "C:\\development\\data\\converted\\ptf"
        String toDir = args.get("-t"); // "C:\\development\\data\\converted\\pcf";

        ITradingProvider provider = TradingProvider.FXCM;

        IPtfFileProviderManager ptf = new PtfFileProviderManager(new File(fromDir), provider);
        IPcfFileProviderManager pcf = new PcfFileProviderManager(new File(toDir), provider);

        PtfToPcfConverter converter = new PtfToPcfConverter(ptf, pcf);
        for (IInstrument instrument : ptf.getInstruments()) {
            converter.convert(instrument);
        }
    }

    private final IPtfFileProviderManager ptf;
    private final IPcfFileProviderManager pcf;

    public PtfToPcfConverter(IPtfFileProviderManager ptf, IPcfFileProviderManager pcf) {
        this.ptf = Check.notNull("ptf", ptf);
        this.pcf = Check.notNull("pcf", pcf);
    }

    public boolean convert(IInstrument instrument) {
        return convert(instrument, PriceIntervals.MINUTELY);
    }

    public boolean convert(IInstrument instrument, IPriceInterval interval) {

        // Get the instrument
        log.info("Converting Instrument: {}", instrument);

        IPtfSourceSet set = ptf.getSourceSet(instrument);
        Set<? extends IPtfSource> sources = set.getSources();
        if (sources.isEmpty()) {
            return false;
        }

        // Get the sources for the instrument
        try (IPriceCandleStreamSource source = createSource(sources, interval)) {

            // Output directory
            File directory = pcf.getDirectory(instrument);
            if (directory.exists()) {
                log.info("Output directory already exists, skipping: {}", directory);
                return false;
            }
            directory.mkdirs();

            try (PcfFileStreamSink sink = new PcfFileStreamSink(directory)) {
                PriceCandles.pipe(source, sink);
            }
        }
        return true;
    }

    private IPriceCandleStreamSource createSource(Set<? extends IPtfSource> sources, IPriceInterval interval) {
        return new PriceCandleIntervalStreamSource(new PtfSourcesStreamSource(sources), interval);
    }
}
