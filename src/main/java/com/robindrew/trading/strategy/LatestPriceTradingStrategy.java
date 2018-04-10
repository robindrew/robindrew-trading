package com.robindrew.trading.strategy;

import com.robindrew.common.util.Threads;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.ITradingPlatform;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.io.stream.sink.LatestPriceTickSink;
import com.robindrew.trading.price.tick.streaming.IPriceTickSnapshot;
import com.robindrew.trading.price.tick.streaming.IStreamingTickPrice;

public abstract class LatestPriceTradingStrategy extends AbstractTradingStrategy implements ILatestPriceTradingStrategy {

	private final LatestPriceTickSink priceSink;

	protected LatestPriceTradingStrategy(String name, ITradingPlatform platform, IInstrument instrument) {
		super(name, platform, instrument);
		this.priceSink = new LatestPriceTickSink(name);
	}

	@Override
	public void putNextTick(IPriceTick tick) {
		priceSink.putNextTick(tick);
	}

	@Override
	public void close() {
		priceSink.close();
	}

	protected void pause() {
		// Pause for a time to yield control
		Threads.sleep(100);
	}

	@Override
	public void run() {
		IStreamingTickPrice price = priceSink.getPrice();

		long previousTimestamp = 0;
		while (true) {

			// Do we have any price?
			IPriceTickSnapshot snapshot = price.getSnapshot();
			if (snapshot == null) {
				pause();
				continue;
			}

			// Has the price changed?
			IPriceTick tick = snapshot.getLatest();
			if (previousTimestamp < tick.getTimestamp()) {
				previousTimestamp = tick.getTimestamp();
				
				// Changed!
				handleLatestTick(tick);
			} else {
				pause();
			}
		}
	}

}
