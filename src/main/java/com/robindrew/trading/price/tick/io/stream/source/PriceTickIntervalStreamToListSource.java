package com.robindrew.trading.price.tick.io.stream.source;

import static java.util.Collections.emptyList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.io.list.source.IPriceTickListSource;

public class PriceTickIntervalStreamToListSource implements IPriceTickListSource {

	private final IPriceTickStreamSource source;
	private final IPriceInterval interval;

	private boolean finished = false;
	private IPriceTick next = null;

	public PriceTickIntervalStreamToListSource(IPriceTickStreamSource source, IPriceInterval interval) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (interval == null) {
			throw new NullPointerException("interval");
		}
		this.source = source;
		this.interval = interval;
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
		if (finished) {
			return emptyList();
		}

		if (next == null) {
			next = source.getNextTick();
			if (next == null) {
				finished = true;
				return emptyList();
			}
		}
		LocalDateTime date = interval.getDateTime(next);

		List<IPriceTick> ticks = new ArrayList<>();
		ticks.add(next);

		while (true) {
			next = source.getNextTick();
			if (next == null) {
				finished = true;
				break;
			}
			if (!date.equals(interval.getDateTime(next))) {
				break;
			}
			ticks.add(next);
		}

		return ticks;
	}

}
