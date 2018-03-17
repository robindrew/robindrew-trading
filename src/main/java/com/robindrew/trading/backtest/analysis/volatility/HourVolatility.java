package com.robindrew.trading.backtest.analysis.volatility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandles;

class HourVolatility {

	private final int hour;
	private IPriceCandle candle = null;
	private List<IPriceCandle> candles = new ArrayList<>();
	private int count = 0;
	private long buy = 0;
	private long sell = 0;

	public HourVolatility(int hour) {
		if (hour < 0 || hour > 23) {
			throw new IllegalArgumentException();
		}
		this.hour = hour;
	}

	public int getHour() {
		return hour;
	}

	public int getCount() {
		return count;
	}

	public long getBuy() {
		return buy;
	}

	public long getSell() {
		return sell;
	}

	public void add(LocalDateTime date, IPriceCandle candle) {
		if (candle.hasClosedUp()) {
			buy += candle.getOpenCloseRange();
		} else {
			sell += candle.getOpenCloseRange();
		}

		candles.add(candle);
		count++;
		if (this.candle == null) {
			this.candle = candle;
		} else {
			this.candle = PriceCandles.merge(this.candle, candle);
		}
	}

	public IPriceCandle getCandle() {
		return candle;
	}
}