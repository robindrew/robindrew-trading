package com.robindrew.trading.dukascopy.download.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.robindrew.trading.InstrumentType;
import com.robindrew.trading.price.candle.format.ptf.PtfFormat;
import com.robindrew.trading.price.candle.format.ptf.source.file.PtfFile;
import com.robindrew.trading.price.candle.tick.ITickPriceCandle;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class DukascopyTickDownloaderTest {

    @Test
    void hourUrlHasZeroBasedMonth() {
        DukascopySymbol symbol = new DukascopySymbol("EURUSD", "EUR/USD", "", InstrumentType.CURRENCIES, 5, 0);
        assertEquals(
                "https://datafeed.dukascopy.com/datafeed/EURUSD/2024/00/15/10h_ticks.bi5",
                new DukascopyHourRequest(symbol, LocalDate.of(2024, 1, 15), 10).getUrl());
    }

    @Test
    void decodeTicksAndWritePtf(@TempDir Path directory) throws Exception {
        LocalDate day = LocalDate.of(2024, 1, 15);
        long hourStart = day.atTime(10, 0).toInstant(ZoneOffset.UTC).toEpochMilli();

        byte[] compressed;
        try (InputStream input = getClass().getResourceAsStream("/bi5/USDJPY-2024-01-15-10h.bi5")) {
            compressed = input.readAllBytes();
        }
        List<ITickPriceCandle> ticks = DukascopyTickDownloader.decodeTicks(compressed, hourStart, 3);

        assertFalse(ticks.isEmpty());
        long previous = hourStart;
        for (ITickPriceCandle tick : ticks) {
            assertTrue(tick.getTimestamp() >= previous, "ticks out of order");
            assertTrue(tick.getTimestamp() < hourStart + 3_600_000L, "tick outside hour");
            assertTrue(tick.getAskPrice() >= tick.getBidPrice(), "ask below bid");
            // USDJPY traded around 145-147 on this day
            double bid = tick.getBidPrice() / 1000.0;
            assertTrue(bid > 140 && bid < 150, "unexpected bid: " + bid);
            previous = tick.getTimestamp();
        }

        PtfFile file = new PtfFile(new File(directory.toFile(), PtfFormat.getFilename(day)), day);
        file.write(ticks);
        List<ITickPriceCandle> read = new PtfFile(file.getFile()).read();
        assertEquals(ticks.size(), read.size());
        assertEquals(ticks.get(0).getBidPrice(), read.get(0).getBidPrice());
        assertEquals(
                ticks.get(ticks.size() - 1).getTimestamp(),
                read.get(read.size() - 1).getTimestamp());
    }
}
