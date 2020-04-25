package com.robindrew.trading.platform.streaming;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.AbstractTradingService;
import com.robindrew.trading.provider.ITradingProvider;

public abstract class AbstractStreamingService<I extends IInstrument> extends AbstractTradingService implements IStreamingService<I> {

	private static final Logger log = LoggerFactory.getLogger(AbstractStreamingService.class);

	private final ConcurrentMap<I, IInstrumentPriceStream<I>> streamMap = new ConcurrentHashMap<>();

	protected AbstractStreamingService(ITradingProvider provider) {
		super(provider);
	}

	@Override
	public Set<I> getSubscribedInstruments() {
		return ImmutableSet.copyOf(streamMap.keySet());
	}

	@Override
	public boolean isSubscribedInstrument(I instrument) {
		Check.notNull("instrument", instrument);
		return streamMap.containsKey(instrument);
	}

	@Override
	public boolean canStreamPrices(I instrument) {
		Check.notNull("instrument", instrument);
		return streamMap.containsKey(instrument);
	}

	@Override
	public Set<IInstrumentPriceStream<I>> getPriceStreams() {
		return ImmutableSet.copyOf(streamMap.values());
	}

	@Override
	public IInstrumentPriceStream<I> getPriceStream(I instrument) {
		Check.notNull("instrument", instrument);

		IInstrumentPriceStream<I> stream = streamMap.get(instrument);
		if (stream == null) {
			throw new IllegalStateException("No stream registered for instrument: " + instrument);
		}
		return stream;
	}

	protected boolean registerStream(IInstrumentPriceStream<I> stream) {
		I instrument = stream.getInstrument();
		if (streamMap.putIfAbsent(instrument, stream) != null) {
			log.warn("Stream already registered for instrument: {}", instrument);
			return false;
		} else {
			log.info("[Register Stream] {}", instrument);
		}
		return true;
	}

	protected void unregisterStream(I instrument) {
		streamMap.remove(instrument);
	}

	@Override
	public void close() {
		for (IInstrumentPriceStream<I> stream : getPriceStreams()) {
			stream.close();
		}
	}
}
