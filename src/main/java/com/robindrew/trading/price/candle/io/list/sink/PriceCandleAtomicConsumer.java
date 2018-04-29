package com.robindrew.trading.price.candle.io.list.sink;

import static com.robindrew.common.concurrent.Atomics.setConditional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public class PriceCandleAtomicConsumer implements IPriceCandleListSink, IPriceCandleStreamSink {

	private final String name;
	private final AtomicReference<IPriceCandle> candle = new AtomicReference<>();

	public PriceCandleAtomicConsumer(String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void close() {
		candle.set(null);
	}

	@Override
	public void putNextCandles(List<? extends IPriceCandle> candles) {
		for (IPriceCandle candle : candles) {
			putNextCandle(candle);
		}
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		updateCandle(candle);
	}

	public boolean updateCandle(IPriceCandle newCandle) {
		if (candle.compareAndSet(null, newCandle)) {
			return true;
		}

		// Lock-free conditional setter
		return setConditional(candle, newCandle, (IPriceCandle oldValue, IPriceCandle newValue) -> (newValue.getOpenTime() > oldValue.getOpenTime()));
	}

	public AtomicReference<IPriceCandle> getReference() {
		return candle;
	}

}
