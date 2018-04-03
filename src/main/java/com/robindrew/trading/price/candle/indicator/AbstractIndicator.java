package com.robindrew.trading.price.candle.indicator;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandles;
import com.robindrew.trading.price.candle.interval.IPriceInterval;

public abstract class AbstractIndicator implements IIndicator {

	private final String name;
	private final IPriceInterval interval;
	private final Deque<IPriceCandle> intervalList = new LinkedList<>();
	private final List<IPriceCandle> bufferList = new ArrayList<>();
	private final int capacity;
	private final AtomicBoolean available = new AtomicBoolean(false);

	private IPriceCandle previousCandle;
	private long previousPeriod;

	protected AbstractIndicator(String name, IPriceInterval interval, int capacity) {
		this.name = Check.notEmpty("name", name);
		this.interval = interval;
		this.capacity = capacity;
	}

	@Override
	public String getName() {
		return name;
	}

	public IPriceInterval getInterval() {
		return interval;
	}

	public int getCapacity() {
		return capacity;
	}

	public boolean isAvailable() {
		return available.get();
	}

	public void setAvailable(boolean available) {
		this.available.set(available);
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {

		// First candle?
		if (previousCandle == null) {
			handleFirstCandle(candle);
		}

		// Next candle!
		else {
			handleNextCandle(candle);
		}

	}

	private void handleNextCandle(IPriceCandle nextCandle) {
		long nextPeriod = interval.getTimePeriod(nextCandle);

		// Sanity check
		if (nextPeriod < previousPeriod) {
			throw new IllegalStateException("This should never happen! nextCandle=" + nextCandle + ", previousCandle=" + previousCandle);
		}

		// Same as current period?
		if (nextPeriod == previousPeriod) {
			bufferList.add(nextCandle);
		}

		// Period has ended, empty the buffer
		else {
			IPriceCandle merged = PriceCandles.merge(bufferList);
			intervalList.addLast(merged);

			// Reset the buffer
			bufferList.clear();
			bufferList.add(nextCandle);

			// Ensure the capacity of the intervals
			while (intervalList.size() > capacity) {
				intervalList.removeFirst();
			}

			// The interval list has changed, update it accordingly
			calculate(ImmutableList.copyOf(intervalList));
		}

		previousPeriod = nextPeriod;
		previousCandle = nextCandle;
	}

	private void handleFirstCandle(IPriceCandle firstCandle) {
		long firstPeriod = interval.getTimePeriod(firstCandle);

		previousCandle = firstCandle;
		previousPeriod = firstPeriod;
		bufferList.add(firstCandle);
	}

	@Override
	public void close() {
		bufferList.clear();
		intervalList.clear();
	}

	protected abstract void calculate(List<IPriceCandle> list);

}
