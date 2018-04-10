package com.robindrew.trading.price.tick.io.stream.sink;

import com.robindrew.common.util.Check;
import com.robindrew.trading.platform.streaming.latest.IStreamingPrice;
import com.robindrew.trading.platform.streaming.latest.PriceSnapshot;
import com.robindrew.trading.platform.streaming.latest.StreamingPrice;
import com.robindrew.trading.price.tick.IPriceTick;

public class LatestPriceTickSink implements IPriceTickStreamSink {

	private final String name;
	private final StreamingPrice price = new StreamingPrice();

	public LatestPriceTickSink(String name) {
		this.name = Check.notEmpty("name", name);
	}

	public IStreamingPrice getPrice() {
		return price;
	}

	public IPriceTick getLatestTick() {
		PriceSnapshot snapshot = price.getSnapshot();
		if (snapshot != null) {
			return (IPriceTick) snapshot.getLatest();
		}
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void close() {
		// Nothing to do
	}

	@Override
	public void putNextTick(IPriceTick tick) {
		price.update(tick);
	}

}
