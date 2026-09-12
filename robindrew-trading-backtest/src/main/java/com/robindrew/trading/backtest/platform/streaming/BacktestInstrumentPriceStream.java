package com.robindrew.trading.backtest.platform.streaming;

import com.robindrew.trading.backtest.IBacktestInstrument;
import com.robindrew.trading.platform.streaming.InstrumentPriceStream;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandles;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.history.IInstrumentPriceHistory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BacktestInstrumentPriceStream extends InstrumentPriceStream<IBacktestInstrument>
        implements IBacktestInstrumentPriceStream {

    private final IInstrumentPriceHistory history;

    public BacktestInstrumentPriceStream(IBacktestInstrument instrument, IInstrumentPriceHistory history) {
        super(instrument);
        this.history = history;
    }

    @Override
    public void run() {
        IPriceCandleStreamSource source = history.getStreamSource();

        // Perform basic sanity check - candles are published in chronological order
        source = PriceCandles.checkSorted(source);

        log.info("[Started Streaming Prices] {}", getInstrument());
        while (true) {
            IPriceCandle candle = source.getNextCandle();
            if (candle == null) {
                break;
            }
            putNextCandle(candle);
        }
        log.info("[Finished Streaming Prices] {}", getInstrument());
    }

    @Override
    public String getName() {
        return "BacktestInstrumentPriceStream[" + getInstrument() + "]";
    }

    @Override
    public void close() {}
}
