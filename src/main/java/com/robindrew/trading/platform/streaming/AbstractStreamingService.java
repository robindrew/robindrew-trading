package com.robindrew.trading.platform.streaming;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.ImmutableSet;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.AbstractTradingService;
import com.robindrew.trading.provider.ITradingProvider;

public abstract class AbstractStreamingService<I extends IInstrument> extends AbstractTradingService implements IStreamingService<I> {

	private final ConcurrentMap<I, IInstrumentPriceStream<I>> streamMap = new ConcurrentHashMap<>();

	protected AbstractStreamingService(ITradingProvider provider) {
		super(provider);
	}

	@Override
	public boolean isSubscribed(I instrument) {
		return streamMap.containsKey(instrument);
	}

	@Override
	public boolean supports(I instrument) {
		Check.notNull("instrument", instrument);
		return streamMap.containsKey(instrument);
	}

	@Override
	public Set<IInstrumentPriceStream<I>> getPriceStreams() {
		return ImmutableSet.copyOf(streamMap.values());
	}

	@Override
	public IInstrumentPriceStream<I> getPriceStream(I instrument) {
		IInstrumentPriceStream<I> stream = streamMap.get(instrument);
		if (stream == null) {
			throw new IllegalStateException("No stream registered for instrument: " + instrument);
		}
		return stream;
	}

	protected void registerStream(IInstrumentPriceStream<I> stream) {
		I instrument = stream.getInstrument();
		if (streamMap.putIfAbsent(instrument, stream) != null) {
			throw new IllegalStateException("stream already registered for instrument: " + instrument);
		}
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
