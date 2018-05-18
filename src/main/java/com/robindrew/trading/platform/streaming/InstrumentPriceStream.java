package com.robindrew.trading.platform.streaming;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;
import com.robindrew.trading.price.candle.streaming.StreamingPriceCandle;

public class InstrumentPriceStream<I extends IInstrument> implements IInstrumentPriceStream<I> {

	private final I instrument;
	private final StreamingPriceCandle price = new StreamingPriceCandle();
	private final Set<IPriceCandleStreamSink> subscriberSet = new CopyOnWriteArraySet<>();

	public InstrumentPriceStream(I instrument) {
		this.instrument = Check.notNull("instrument", instrument);
	}

	@Override
	public I getInstrument() {
		return instrument;
	}

	@Override
	public String getName() {
		return "InstrumentPriceStream[" + instrument + "]";
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {

		// Update the latest price
		price.update(candle);

		// Propagate the candle to each subscriber
		for (IPriceCandleStreamSink subscriber : subscriberSet) {
			subscriber.putNextCandle(candle);
		}
	}

	@Override
	public boolean register(IPriceCandleStreamSink sink) {
		return subscriberSet.add(sink);
	}

	@Override
	public boolean unregister(IPriceCandleStreamSink sink) {
		return subscriberSet.remove(sink);
	}

	@Override
	public void close() {
		for (IPriceCandleStreamSink subscriber : subscriberSet) {
			subscriber.close();
		}
		subscriberSet.clear();
	}

	@Override
	public StreamingPriceCandle getPrice() {
		return price;
	}

}
