package com.robindrew.trading.price.candle.io.list.sink;

import com.robindrew.trading.price.candle.IPriceCandle;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class MultiListSink implements IPriceCandleListSink {

    private final String name;
    private final Set<IPriceCandleListSink> sinks = new CopyOnWriteArraySet<>();

    public MultiListSink(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void close() {
        for (IPriceCandleListSink sink : sinks) {
            sink.close();
        }
        sinks.clear();
    }

    @Override
    public void putNextCandles(List<? extends IPriceCandle> candles) {
        for (IPriceCandleListSink sink : sinks) {
            sink.putNextCandles(candles);
        }
    }
}
