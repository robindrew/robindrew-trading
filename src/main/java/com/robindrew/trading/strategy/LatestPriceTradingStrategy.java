package com.robindrew.trading.strategy;

import com.robindrew.common.util.Threads;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.ITradingPlatform;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.sink.LatestPriceCandleSink;
import com.robindrew.trading.price.candle.streaming.IPriceCandleSnapshot;
import com.robindrew.trading.price.candle.streaming.IStreamingCandlePrice;

public abstract class LatestPriceTradingStrategy<I extends IInstrument> extends AbstractTradingStrategy<I> implements ILatestPriceTradingStrategy<I> {

	private final LatestPriceCandleSink priceSink;

	protected LatestPriceTradingStrategy(String name, ITradingPlatform<I> platform, IInstrument instrument) {
		super(name, platform, instrument);
		this.priceSink = new LatestPriceCandleSink(name);
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		priceSink.putNextCandle(candle);
	}

	@Override
	public void close() {
		priceSink.close();
	}

	protected void pause() {
		// Pause for a time to yield control
		Threads.sleep(100);
	}

	public Thread start() {
		Thread thread = new Thread(this, getName());
		thread.start();
		return thread;
	}

	@Override
	public void run() {
		IStreamingCandlePrice price = priceSink.getPrice();

		long previousTimestamp = 0;
		while (true) {

			// Do we have any price?
			IPriceCandleSnapshot snapshot = price.getSnapshot();
			if (snapshot == null) {
				pause();
				continue;
			}

			// Has the price changed?
			IPriceCandle candle = snapshot.getLatest();
			if (previousTimestamp < candle.getCloseTime()) {
				previousTimestamp = candle.getCloseTime();

				// Changed!
				handleLatestCandle(candle);
			} else {
				pause();
			}
		}
	}

}
