package com.robindrew.trading.price.candle.format.ptf.source;

import static com.robindrew.common.text.Strings.bytes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.ITickPriceCandle;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleListBackedStreamSource;

public class PtfSourcesStreamSource implements IPriceCandleStreamSource {

	private static final Logger log = LoggerFactory.getLogger(PtfSourcesStreamSource.class);

	private final LinkedList<IPtfSource> sources;
	private final boolean reverse;

	private IPriceCandleStreamSource currentSource = null;

	public PtfSourcesStreamSource(Collection<? extends IPtfSource> sources) {
		this(sources, false);
	}

	public PtfSourcesStreamSource(Collection<? extends IPtfSource> sources, boolean reverse) {
		this.sources = new LinkedList<>(reverse ? Lists.reverse(new ArrayList<>(sources)) : sources);
		this.reverse = reverse;
	}

	@Override
	public String getName() {
		return "PtfSourceStreamSource";
	}

	@Override
	public void close() {
		sources.clear();
		if (currentSource != null) {
			currentSource.close();
			currentSource = null;
		}
	}

	@Override
	public IPriceCandle getNextCandle() {

		// Is there a source?
		if (currentSource != null) {
			IPriceCandle candle = currentSource.getNextCandle();
			if (candle != null) {
				return candle;
			}
			currentSource.close();
			currentSource = null;
		}

		// No more sources?
		while (true) {
			if (sources.isEmpty()) {
				return null;
			}

			// Next file
			IPtfSource source = sources.removeFirst();
			if (log.isDebugEnabled()) {
				log.debug("Source: {} ({})", source, bytes(source.size()));
			}

			List<ITickPriceCandle> candles = source.read();
			if (reverse) {
				candles = Lists.reverse(candles);
			}
			currentSource = new PriceCandleListBackedStreamSource(candles);

			// Each file should contain at least one candle!
			IPriceCandle candle = currentSource.getNextCandle();
			if (candle == null) {
				throw new IllegalStateException("File does not contain any candles: " + source);
			}
			return candle;
		}
	}

}
