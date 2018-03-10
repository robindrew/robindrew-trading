package com.robindrew.trading.price.candle.io.stream.source;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public class AverageCandlesStreamSource implements IPriceCandleStreamSink {

	private final long timeWindow;
	private final LinkedList<IPriceCandle> candles = new LinkedList<>();
	private final String name;

	private long finishTime = 0;
	private long startTime = 0;

	public AverageCandlesStreamSource(String name, long amount, TimeUnit unit) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		this.name = name;
		this.timeWindow = unit.toMillis(amount);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void close() {
		candles.clear();
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		finishTime = candle.getCloseTime();
		startTime = finishTime - timeWindow;
		candles.add(candle);

		if (candles.size() > 1) {
			Iterator<IPriceCandle> iterator = candles.iterator();
			while (iterator.hasNext()) {
				IPriceCandle head = iterator.next();
				if (head.getOpenTime() >= startTime) {
					break;
				}
				iterator.remove();
			}
		}
	}

}
