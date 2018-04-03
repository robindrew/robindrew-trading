package com.robindrew.trading.price.tick.format.ptf.source;

import static com.robindrew.common.text.Strings.bytes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.io.stream.source.IPriceTickStreamSource;
import com.robindrew.trading.price.tick.io.stream.source.PriceTickListBackedStreamSource;

public class PtfSourcesStreamSource implements IPriceTickStreamSource {

	private static final Logger log = LoggerFactory.getLogger(PtfSourcesStreamSource.class);

	private final LinkedList<IPtfSource> sources;
	private final boolean reverse;

	private IPriceTickStreamSource currentSource = null;

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
	public IPriceTick getNextTick() {

		// Is there a source?
		if (currentSource != null) {
			IPriceTick tick = currentSource.getNextTick();
			if (tick != null) {
				return tick;
			}
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

			List<IPriceTick> ticks = source.read();
			if (reverse) {
				ticks = Lists.reverse(ticks);
			}
			currentSource = new PriceTickListBackedStreamSource(ticks);

			// Each file should contain at least one tick!
			IPriceTick tick = currentSource.getNextTick();
			if (tick == null) {
				throw new IllegalStateException("File does not contain any ticks: " + source);
			}
			return tick;
		}
	}

}
