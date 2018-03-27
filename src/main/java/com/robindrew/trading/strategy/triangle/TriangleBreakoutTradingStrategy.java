package com.robindrew.trading.strategy.triangle;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.ITradingPlatform;
import com.robindrew.trading.platform.streaming.latest.PriceSnapshot;
import com.robindrew.trading.platform.streaming.latest.StreamingPrice;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.strategy.SingleInstrumentStrategy;

public class TriangleBreakoutTradingStrategy extends SingleInstrumentStrategy implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(TriangleBreakoutTradingStrategy.class);

	private final TriangleBreakoutParameters parameters;
	private final StreamingPrice price = new StreamingPrice();
	private final AtomicBoolean closed = new AtomicBoolean(false);
	private long sleepTimeMillis = 10;

	public TriangleBreakoutTradingStrategy(ITradingPlatform platform, IInstrument instrument, TriangleBreakoutParameters parameters) {
		super("TriangleBreakout", platform, instrument);
		this.parameters = parameters;
	}

	public TriangleBreakoutParameters getParameters() {
		return parameters;
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		price.update(candle);
	}

	@Override
	public void run() {
		long previousTime = 0;

		try {
			while (!isClosed()) {
				PriceSnapshot update = price.getSnapshot();

				long time = update.getTimestamp();
				if (previousTime < time) {
					previousTime = time;
					handleLatest(update.getLatest());
				} else {
					Thread.sleep(sleepTimeMillis);
				}
			}
		} catch (Throwable t) {
			closed.set(true);
			log.error("Strategy crashed", t);
		}
	}

	private void handleLatest(IPriceCandle price) {
		// TODO: finish writing this?
	}

	public boolean isClosed() {
		return closed.get();
	}

	@Override
	public void close() {
		closed.set(true);
	}

}
