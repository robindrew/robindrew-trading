package com.robindrew.trading.platform.streaming;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.streaming.latest.StreamingPrice;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.io.stream.sink.IPriceTickStreamSink;

public abstract class InstrumentPriceStream implements IInstrumentPriceStream {

	private final IInstrument instrument;
	private final StreamingPrice price = new StreamingPrice();
	private final Set<IPriceTickStreamSink> subscriberSet = new CopyOnWriteArraySet<>();

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
	public void putNextTick(IPriceTick candle) {

		// Update the latest price
		price.update(candle);

		// Propagate the candle to each subscriber
		for (IPriceTickStreamSink subscriber : subscriberSet) {
			subscriber.putNextTick(candle);
		}
	}

	@Override
	public boolean register(IPriceTickStreamSink sink) {
		return subscriberSet.add(sink);
	}

	@Override
	public boolean unregister(IPriceTickStreamSink sink) {
		return subscriberSet.remove(sink);
	}

	@Override
	public void close() {
		for (IPriceTickStreamSink subscriber : subscriberSet) {
			subscriber.close();
		}
		subscriberSet.clear();
	}

	@Override
	public StreamingPrice getPrice() {
		return price;
	}

}
