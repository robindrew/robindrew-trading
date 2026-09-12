package com.robindrew.trading.price.candle.format.pcf.source;

import static com.robindrew.trading.util.text.TradingStrings.bytes;

import com.google.common.collect.Lists;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleListBackedStreamSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PcfSourcesStreamSource implements IPriceCandleStreamSource {

    private final LinkedList<IPcfSource> sources;
    private final boolean reverse;

    private IPriceCandleStreamSource currentSource = null;

    public PcfSourcesStreamSource(Collection<? extends IPcfSource> sources) {
        this(sources, false);
    }

    public PcfSourcesStreamSource(Collection<? extends IPcfSource> sources, boolean reverse) {
        this.sources = new LinkedList<>(reverse ? Lists.reverse(new ArrayList<>(sources)) : sources);
        this.reverse = reverse;
    }

    @Override
    public String getName() {
        return "PcfSourceStreamSource";
    }

    @Override
    public void close() {
        sources.clear();
        if (currentSource != null) {
            currentSource.close();
            currentSource = null;
        }
    }

    @Override
    public IPriceCandle getNextCandle() {

        // Is there a source?
        if (currentSource != null) {
            IPriceCandle candle = currentSource.getNextCandle();
            if (candle != null) {
                return candle;
            }
            currentSource = null;
        }

        // No more sources?
        while (true) {
            if (sources.isEmpty()) {
                return null;
            }

            // Next file
            IPcfSource source = sources.removeFirst();
            if (log.isDebugEnabled()) {
                log.debug("Source: {} ({})", source, bytes(source.size()));
            }

            List<? extends IPriceCandle> candles = source.read();
            if (reverse) {
                candles = Lists.reverse(candles);
            }
            currentSource = new PriceCandleListBackedStreamSource(candles);

            // Each file should contain at least one candle!
            IPriceCandle candle = currentSource.getNextCandle();
            if (candle == null) {
                throw new IllegalStateException("File does not contain any candles: " + source);
            }
            return candle;
        }
    }
}
