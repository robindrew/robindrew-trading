package com.robindrew.trading.price.candle.io.stream.sink.subscriber;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public class PriceCandleSubscriberStreamSink implements IInstrumentPriceStreamListener {

	private final IInstrument instrument;
	private final Set<IPriceCandleStreamSink> subscriberSet = new CopyOnWriteArraySet<>();

	public PriceCandleSubscriberStreamSink(IInstrument instrument) {
		this.instrument = Check.notNull("instrument", instrument);
	}

	@Override
	public IInstrument getInstrument() {
		return instrument;
	}

	@Override
	public String getName() {
		return "PriceCandleSubscriberStreamSink[" + instrument + "]";
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
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

}
