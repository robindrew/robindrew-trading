package com.robindrew.trading.backtest.history;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instruments;
import com.robindrew.trading.backtest.platform.history.BacktestHistoryService;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceManager;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderManager;
import com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileManager;
import com.robindrew.trading.price.candle.interval.PriceIntervals;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.history.IInstrumentPriceHistory;
import com.robindrew.trading.provider.ITradingProvider;
import com.robindrew.trading.provider.TradingProvider;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BacktestHistoryTest {

    @Test
    public void checkHistory() {

        File rootDirectory = new File("src/test/resources/pcf");
        ITradingProvider provider = TradingProvider.FXCM;
        IInstrument instrument = Instruments.GBP_USD;

        // Source the price candles
        IPcfSourceManager locator = new PcfFileManager(rootDirectory);
        IPcfSourceProviderManager manager = locator.getProvider(provider);

        // Create history service
        BacktestHistoryService history = new BacktestHistoryService(manager);

        Assertions.assertTrue(history.getInstruments().contains(instrument));

        IInstrumentPriceHistory instrumentHistory = history.getPriceHistory(instrument);

        IPriceCandle lastCandle = null;
        IPriceCandleStreamSource stream = instrumentHistory.getStreamSource();
        while (true) {
            IPriceCandle nextCandle = stream.getNextCandle();
            if (nextCandle == null) {
                break;
            }
            lastCandle = nextCandle;
        }
        Assertions.assertEquals(
                LocalDate.of(2017, 12, 29), lastCandle.getCloseDate().toLocalDate());

        List<? extends IPriceCandle> latest = instrumentHistory.getLatestPrices(PriceIntervals.MINUTELY, 1);
        Assertions.assertTrue(latest.contains(lastCandle));
    }
}
