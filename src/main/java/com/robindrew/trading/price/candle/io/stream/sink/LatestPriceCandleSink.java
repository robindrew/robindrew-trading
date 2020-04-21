package com.robindrew.trading.price.candle.io.stream.sink;

import static com.robindrew.common.util.Check.notEmpty;

import java.util.Optional;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.streaming.IStreamingCandlePrice;
import com.robindrew.trading.price.candle.streaming.PriceCandleSnapshot;
import com.robindrew.trading.price.candle.streaming.StreamingPriceCandle;

public class LatestPriceCandleSink implements IPriceCandleStreamSink {

	private final String name;
	private final StreamingPriceCandle price = new StreamingPriceCandle();

	public LatestPriceCandleSink(String name) {
		this.name = notEmpty("name", name);
	}

	public IStreamingCandlePrice getPrice() {
		return price;
	}

	public boolean hasLatestCandle() {
		return price.getUpdateCount() > 0;
	}

	public Optional<IPriceCandle> getLatestCandle() {
		PriceCandleSnapshot snapshot = price.getSnapshot();
		if (snapshot == null) {
			return Optional.empty();
		}
		return Optional.of((IPriceCandle) snapshot.getLatest());
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
	public void putNextCandle(IPriceCandle candle) {
		price.update(candle);
	}

}
