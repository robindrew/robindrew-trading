package com.robindrew.trading.platform.streaming;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.ImmutableSet;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;

public abstract class StreamingService implements IStreamingService {

	private final ConcurrentMap<IInstrument, IInstrumentPriceStream> streamMap = new ConcurrentHashMap<>();

	@Override
	public boolean supports(IInstrument instrument) {
		Check.notNull("instrument", instrument);
		return streamMap.containsKey(instrument);
	}

	@Override
	public Set<IInstrumentPriceStream> getPriceStreams() {
		return ImmutableSet.copyOf(streamMap.values());
	}

	@Override
	public IInstrumentPriceStream getPriceStream(IInstrument instrument) {
		instrument = instrument.getUnderlying(true);
		IInstrumentPriceStream stream = streamMap.get(instrument);
		if (stream == null) {
			throw new IllegalStateException("No stream registered for instrument: " + instrument);
		}
		return stream;
	}

	protected void registerStream(IInstrumentPriceStream stream) {
		IInstrument instrument = stream.getInstrument().getUnderlying(true);
		if (streamMap.putIfAbsent(instrument, stream) != null) {
			throw new IllegalStateException("stream already registered for instrument: " + instrument);
		}
	}

	protected void unregisterStream(IInstrumentPriceStream stream) {
		unregisterStream(stream.getInstrument());
	}

	protected void unregisterStream(IInstrument instrument) {
		streamMap.remove(instrument.getUnderlying(true));
	}

	@Override
	public void close() {
		for (IInstrumentPriceStream stream : getPriceStreams()) {
			stream.close();
		}
	}
}
