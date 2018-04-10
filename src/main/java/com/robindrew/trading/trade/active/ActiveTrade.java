package com.robindrew.trading.trade.active;

import com.robindrew.common.util.Check;
import com.robindrew.common.util.Threads;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.streaming.latest.IPriceSnapshot;
import com.robindrew.trading.platform.streaming.latest.IStreamingPrice;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.io.stream.sink.LatestPriceTickSink;

public abstract class ActiveTrade implements IActiveTrade {

	private final String name;
	private final IInstrument instrument;
	private final LatestPriceTickSink priceSink;

	protected ActiveTrade(String name, IInstrument instrument) {
		this.name = Check.notEmpty("name", name);
		this.instrument = Check.notNull("instrument", instrument);
		this.priceSink = new LatestPriceTickSink(name);
	}

	@Override
	public void putNextTick(IPriceTick tick) {
		priceSink.putNextTick(tick);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void close() {
		priceSink.close();
	}

	@Override
	public IInstrument getInstrument() {
		return instrument;
	}

	@Override
	public IPriceTick getLatestPrice() {
		return priceSink.getLatestTick();
	}

	@Override
	public void run() {
		IStreamingPrice price = priceSink.getPrice();

		while (true) {

			// Do we have any price?
			IPriceSnapshot snapshot = price.getSnapshot();
			if (snapshot == null) {
				Threads.sleep(100);
				continue;
			}

			IPriceTick tick = (IPriceTick) snapshot.getLatest();
			handleLatestTick(tick, snapshot);
		}
	}

	protected abstract void handleLatestTick(IPriceTick tick, IPriceSnapshot snapshot);

}
