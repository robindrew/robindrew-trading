package com.robindrew.trading.price.candle.io.stream.source;

import static com.robindrew.trading.price.candle.PriceCandles.merge;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.interval.IPriceInterval;

/**
 * Note that this does NOT support monthly intervals or above, as months have different lengths, and years are subject
 * to leap year loss.
 */
public class PriceCandleIntervalStreamSource implements IPriceCandleStreamSource {

	private final IPriceCandleStreamSource source;
	private final IPriceInterval interval;

	private IPriceCandle current = null;
	private boolean finished = false;

	public PriceCandleIntervalStreamSource(IPriceCandleStreamSource source, IPriceInterval interval) {
		this.source = Check.notNull("source", source);
		this.interval = Check.notNull("interval", interval);
	}

	public IPriceInterval getInterval() {
		return interval;
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
	public IPriceCandle getNextCandle() {
		if (finished) {
			return null;
		}

		if (current == null) {
			current = source.getNextCandle();
		}
		if (current == null) {
			finished = true;
			return null;
		}

		// Merge candles in the same interval
		IPriceCandle merged = current;
		long timePeriod = interval.getTimePeriod(current);
		while (true) {
			current = source.getNextCandle();
			if (current == null) {
				finished = true;
				break;
			}

			if (timePeriod != interval.getTimePeriod(current)) {
				break;
			}

			merged = merge(merged, current);
		}

		return merged;
	}

}
