package com.robindrew.trading.price.candle.io.stream.sink;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceCandleLoggingStreamSink implements IPriceCandleStreamSink {

    private final IInstrument instrument;

    public PriceCandleLoggingStreamSink(IInstrument instrument) {
        if (instrument == null) {
            throw new NullPointerException("instrument");
        }
        this.instrument = instrument;
    }

    @Override
    public String getName() {
        return "PriceCandleLoggingStreamSink";
    }

    @Override
    public void putNextCandle(IPriceCandle candle) {
        log.info("{} -> {}", instrument, candle);
    }
}
