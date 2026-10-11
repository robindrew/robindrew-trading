package com.robindrew.trading.dukascopy.download.client;

import com.robindrew.trading.price.candle.tick.ITickPriceCandle;
import com.robindrew.trading.price.candle.tick.TickPriceCandle;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.tukaani.xz.LZMAInputStream;

/**
 * Downloads a single hour (UTC) of tick data from the Dukascopy datafeed, published as one LZMA compressed bi5 file
 * per hour.
 */
@Slf4j
public class DukascopyTickDownloader
        extends DukascopyWebClientDownloader<DukascopyHourRequest, List<ITickPriceCandle>> {

    public static final String DATAFEED_URL = "https://datafeed.dukascopy.com/datafeed";

    private static final int TICK_RECORD_SIZE = 20;

    public DukascopyTickDownloader(WebClient.Builder builder) {
        super(builder);
    }

    public static String getHourUrl(String symbol, LocalDate day, int hour) {
        // Month is zero based in the datafeed URL (January = 00)
        return String.format(
                "%s/%s/%04d/%02d/%02d/%02dh_ticks.bi5",
                DATAFEED_URL, symbol, day.getYear(), day.getMonthValue() - 1, day.getDayOfMonth(), hour);
    }

    /**
     * @return the ticks, empty if there was no trading in the hour.
     */
    @Override
    public List<ITickPriceCandle> download(DukascopyHourRequest request) {
        String url = request.getUrl();
        byte[] compressed = fetch(url, null);
        if (compressed == null || compressed.length == 0) {
            return List.of();
        }
        long hourStart = request.day()
                .atTime(request.hour(), 0)
                .toInstant(ZoneOffset.UTC)
                .toEpochMilli();
        try {
            return decodeTicks(compressed, hourStart, request.symbol().decimalPlaces());
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to decode " + url, e);
        }
    }

    /**
     * Each record is 20 bytes, big endian: time offset from the hour in millis (int), ask (int), bid (int), ask
     * volume (float), bid volume (float).
     */
    static List<ITickPriceCandle> decodeTicks(byte[] compressed, long hourStart, int decimalPlaces) throws IOException {
        List<ITickPriceCandle> ticks = new ArrayList<>();
        try (DataInputStream input = new DataInputStream(new LZMAInputStream(new ByteArrayInputStream(compressed)))) {
            while (true) {
                int offset;
                try {
                    offset = input.readInt();
                } catch (EOFException e) {
                    break;
                }
                int ask = input.readInt();
                int bid = input.readInt();
                input.skipNBytes(TICK_RECORD_SIZE - 12); // Volumes are not stored in PTF

                if (bid <= 0 || ask <= 0) {
                    log.debug("Skipping invalid tick: bid={}, ask={}", bid, ask);
                    continue;
                }
                ticks.add(new TickPriceCandle(bid, ask, hourStart + offset, decimalPlaces));
            }
        }
        return ticks;
    }
}
