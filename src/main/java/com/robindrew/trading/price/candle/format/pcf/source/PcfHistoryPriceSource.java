package com.robindrew.trading.price.candle.format.pcf.source;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleIntervalStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleStreamSourceBuilder;
import com.robindrew.trading.price.history.IInstrumentPriceHistory;

public class PcfHistoryPriceSource implements IInstrumentPriceHistory {

	private final IInstrument instrument;
	private final IPcfSourceSet sourceSet;

	public PcfHistoryPriceSource(IInstrument instrument, IPcfSourceSet sourceSet) {
		this.instrument = Check.notNull("instrument", instrument);
		this.sourceSet = Check.notNull("sourceSet", sourceSet);
	}

	@Override
	public IInstrument getInstrument() {
		return instrument;
	}

	@Override
	public List<IPriceCandle> getPriceCandles(LocalDateTime from, LocalDateTime to) {
		Set<? extends IPcfSource> sources = sourceSet.getSources(from, to);

		// Use the builder for convenience
		PriceCandleStreamSourceBuilder builder = new PriceCandleStreamSourceBuilder();
		builder.setPcfSources(sources);
		builder.between(from, to);
		return builder.getList();
	}

	@Override
	public IPriceCandleStreamSource getStreamSource(LocalDateTime from, LocalDateTime to) {
		Set<? extends IPcfSource> sources = sourceSet.getSources(from, to);

		// Use the builder for convenience
		PriceCandleStreamSourceBuilder builder = new PriceCandleStreamSourceBuilder();
		builder.setPcfSources(sources);
		builder.between(from, to);
		return builder.get();
	}

	@Override
	public IPriceCandleStreamSource getStreamSource() {
		Set<? extends IPcfSource> sources = sourceSet.getSources();

		// Use the builder for convenience
		PriceCandleStreamSourceBuilder builder = new PriceCandleStreamSourceBuilder();
		builder.setPcfSources(sources);
		return builder.get();
	}

	@Override
	public List<IPriceCandle> getLatestPrices(IPriceInterval interval, int count) {

		List<IPriceCandle> list = new ArrayList<IPriceCandle>();

		try (IPriceCandleStreamSource source = new PriceCandleIntervalStreamSource(new PcfSourcesStreamSource(sourceSet.getSources(), true), interval)) {
			while (list.size() < count) {
				IPriceCandle candle = source.getNextCandle();
				if (candle == null) {
					break;
				}
				list.add(candle);
			}
		}
		return list;
	}

}
