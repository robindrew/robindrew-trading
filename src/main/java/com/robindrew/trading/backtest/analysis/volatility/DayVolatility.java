package com.robindrew.trading.backtest.analysis.volatility;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.robindrew.trading.price.candle.IPriceCandle;

class DayVolatility {

	private final DayOfWeek day;
	private final Map<Integer, HourVolatility> hours = new TreeMap<>();

	public DayVolatility(DayOfWeek day) {
		this.day = day;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public int getCount() {
		int count = 0;
		for (HourVolatility hour : hours.values()) {
			count += hour.getCount();
		}
		return count;
	}

	public List<IPriceCandle> getCandles() {
		List<IPriceCandle> candles = new ArrayList<>();
		for (HourVolatility hour : hours.values()) {
			candles.add(hour.getCandle());
		}
		return candles;
	}

	public long getBuy() {
		long amount = 0;
		for (HourVolatility hour : hours.values()) {
			amount += hour.getBuy();
		}
		return amount;
	}

	public long getSell() {
		long amount = 0;
		for (HourVolatility hour : hours.values()) {
			amount += hour.getSell();
		}
		return amount;
	}

	public void add(LocalDateTime date, IPriceCandle candle) {
		Integer hour = date.getHour();
		HourVolatility volatility = hours.get(hour);
		if (volatility == null) {
			volatility = new HourVolatility(hour);
			hours.put(hour, volatility);
		}
		volatility.add(date, candle);
	}
}
