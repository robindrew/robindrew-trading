package com.robindrew.trading.price.indicator;

import java.util.Deque;
import java.util.LinkedList;

import com.google.common.base.Optional;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandles;
import com.robindrew.trading.price.decimal.Decimal;

/**
 * Simple Moving Average (SMA).
 */
public class SimpleMovingAverage implements IPriceCandleStreamIndicator<Decimal> {

	private final int periods;
	private final Deque<IPriceCandle> candles = new LinkedList<>();

	public SimpleMovingAverage(int periods) {
		if (periods < 1) {
			throw new IllegalArgumentException("periods=" + periods);
		}
		this.periods = periods;
	}

	@Override
	public String getName() {
		return "SimpleMovingAverage";
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		candles.addLast(candle);
		if (candles.size() > periods) {
			candles.removeFirst();
		}
	}

	@Override
	public void close() {
		candles.clear();
	}

	@Override
	public Optional<Decimal> getIndicator() {
		if (candles.size() < periods) {
			return Optional.absent();
		}
		Decimal average = PriceCandles.getAverage(candles);
		return Optional.of(average);
	}
}
