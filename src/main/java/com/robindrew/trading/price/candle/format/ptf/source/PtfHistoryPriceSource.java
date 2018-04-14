package com.robindrew.trading.price.candle.format.ptf.source;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.ITickPriceCandle;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleIntervalStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleStreamSourceBuilder;
import com.robindrew.trading.price.history.IInstrumentPriceHistory;

public class PtfHistoryPriceSource implements IInstrumentPriceHistory {

	private final IInstrument instrument;
	private final IPtfSourceSet sourceSet;

	public PtfHistoryPriceSource(IInstrument instrument, IPtfSourceSet sourceSet) {
		this.instrument = Check.notNull("instrument", instrument);
		this.sourceSet = Check.notNull("sourceSet", sourceSet);
	}

	@Override
	public IInstrument getInstrument() {
		return instrument;
	}

	@Override
	public List<IPriceCandle> getPriceCandles(LocalDateTime from, LocalDateTime to) {
		Set<? extends IPtfSource> sources = sourceSet.getSources(from, to);

		// Use the builder for convenience
		PriceCandleStreamSourceBuilder builder = new PriceCandleStreamSourceBuilder();
		builder.setPtfSources(sources);
		builder.between(from, to);
		return builder.getList();
	}

	@Override
	public IPriceCandleStreamSource getStreamSource(LocalDateTime from, LocalDateTime to) {
		Set<? extends IPtfSource> sources = sourceSet.getSources(from, to);

		// Use the builder for convenience
		PriceCandleStreamSourceBuilder builder = new PriceCandleStreamSourceBuilder();
		builder.setPtfSources(sources);
		builder.between(from, to);
		return builder.get();
	}

	@Override
	public IPriceCandleStreamSource getStreamSource() {
		Set<? extends IPtfSource> sources = sourceSet.getSources();

		// Use the builder for convenience
		PriceCandleStreamSourceBuilder builder = new PriceCandleStreamSourceBuilder();
		builder.setPtfSources(sources);
		return builder.get();
	}

	@Override
	public List<ITickPriceCandle> getLatestPrices(IPriceInterval interval, int count) {

		List<ITickPriceCandle> list = new ArrayList<>();

		try (IPriceCandleStreamSource source = new PriceCandleIntervalStreamSource(new PtfSourcesStreamSource(sourceSet.getSources(), true), interval)) {
			while (list.size() < count) {
				ITickPriceCandle candle = (ITickPriceCandle) source.getNextCandle();
				if (candle == null) {
					break;
				}
				list.add(candle);
			}
		}
		return list;
	}

}
