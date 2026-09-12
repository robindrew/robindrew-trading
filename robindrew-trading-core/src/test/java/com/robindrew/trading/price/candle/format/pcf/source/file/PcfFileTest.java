package com.robindrew.trading.price.candle.format.pcf.source.file;

import static com.robindrew.trading.price.candle.interval.TimeUnitInterval.ONE_MINUTE;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.format.pcf.PcfFormat;
import com.robindrew.trading.price.candle.generator.PriceCandleGenerator;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class PcfFileTest {

    @TempDir
    File tempDir;

    @Test
    public void writeAndReadFile() throws IOException {

        // Generate some candles
        LocalDateTime fromDate = LocalDateTime.of(2018, 01, 01, 0, 0);
        IPriceInterval interval = ONE_MINUTE;
        PriceCandleGenerator generator = new PriceCandleGenerator(interval);
        List<IPriceCandle> before = generator.generateCandles(1440, fromDate);

        File tempFile = new File(tempDir, PcfFormat.getFilename(fromDate.toLocalDate()));

        PcfFile pcf = new PcfFile(tempFile);
        pcf.write(before);

        List<IPriceCandle> after = pcf.read();
        Assertions.assertEquals(before, after);
    }
}
