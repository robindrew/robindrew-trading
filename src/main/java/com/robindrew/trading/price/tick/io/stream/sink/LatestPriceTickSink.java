package com.robindrew.trading.price.tick.io.stream.sink;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.streaming.IStreamingTickPrice;
import com.robindrew.trading.price.tick.streaming.PriceTickSnapshot;
import com.robindrew.trading.price.tick.streaming.StreamingPriceTick;

public class LatestPriceTickSink implements IPriceTickStreamSink {

	private final String name;
	private final StreamingPriceTick price = new StreamingPriceTick();

	public LatestPriceTickSink(String name) {
		this.name = Check.notEmpty("name", name);
	}

	public IStreamingTickPrice getPrice() {
		return price;
	}

	public IPriceTick getLatestTick() {
		PriceTickSnapshot snapshot = price.getSnapshot();
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
