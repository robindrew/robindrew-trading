package com.robindrew.trading.platform.streaming;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.streaming.latest.StreamingPrice;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public abstract class InstrumentPriceStream implements IInstrumentPriceStream {

	private final IInstrument instrument;
	private final StreamingPrice price = new StreamingPrice();
	private final Set<IPriceCandleStreamSink> subscriberSet = new CopyOnWriteArraySet<>();

	protected InstrumentPriceStream(IInstrument instrument) {
		this.instrument = Check.notNull("instrument", instrument);
	}

	@Override
	public IInstrument getInstrument() {
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
	public StreamingPrice getPrice() {
		return price;
	}

}
