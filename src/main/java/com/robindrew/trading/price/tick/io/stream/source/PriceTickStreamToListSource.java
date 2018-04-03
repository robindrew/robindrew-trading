package com.robindrew.trading.price.tick.io.stream.source;

import static com.robindrew.common.text.Strings.number;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.PriceTicks;
import com.robindrew.trading.price.tick.io.list.source.IPriceTickListSource;

public class PriceTickStreamToListSource implements IPriceTickListSource {

	private static final Logger log = LoggerFactory.getLogger(PriceTickStreamToListSource.class);

	private final IPriceTickStreamSource source;

	public PriceTickStreamToListSource(IPriceTickStreamSource source) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		this.source = source;
	}

	@Override
	public String getName() {
		return source.getName();
	}

	@Override
	public void close() {
		source.close();
	}

	@Override
	public List<IPriceTick> getNextTicks() {
		log.debug("Reading all ticks from {}", getName());
		Stopwatch timer = Stopwatch.createStarted();

		List<IPriceTick> list = Lists.newArrayList(PriceTicks.iterable(source));

		timer.stop();
		log.debug("Read {} ticks from {} in {}", number(list), getName(), timer);
		return list;
	}

}
