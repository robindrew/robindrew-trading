package com.robindrew.trading.backtest.analysis.volatility;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.robindrew.trading.price.candle.IPriceCandle;

class MonthVolatility {

	private final Month month;
	private final Map<DayOfWeek, DayVolatility> days = new EnumMap<>(DayOfWeek.class);

	public MonthVolatility(Month month) {
		this.month = month;
	}

	public Month getMonth() {
		return month;
	}

	public int getCount() {
		int count = 0;
		for (DayVolatility day : days.values()) {
			count += day.getCount();
		}
		return count;
	}

	public void add(LocalDateTime date, IPriceCandle candle) {
		DayOfWeek day = date.getDayOfWeek();
		DayVolatility volatility = days.get(day);
		if (volatility == null) {
			volatility = new DayVolatility(day);
			days.put(day, volatility);
		}
		volatility.add(date, candle);
	}

	public List<IPriceCandle> getCandles() {
		List<IPriceCandle> candles = new ArrayList<>();
		for (DayVolatility day : days.values()) {
			candles.addAll(day.getCandles());
		}
		return candles;
	}

	public long getBuy() {
		long amount = 0;
		for (DayVolatility day : days.values()) {
			amount += day.getBuy();
		}
		return amount;
	}

	public long getSell() {
		long amount = 0;
		for (DayVolatility day : days.values()) {
			amount += day.getSell();
		}
		return amount;
	}
}
