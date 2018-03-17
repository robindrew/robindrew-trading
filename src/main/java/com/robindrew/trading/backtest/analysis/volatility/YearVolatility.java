package com.robindrew.trading.backtest.analysis.volatility;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.robindrew.trading.price.candle.IPriceCandle;

class YearVolatility {

	private final Year year;
	private final Map<Month, MonthVolatility> months = new EnumMap<>(Month.class);

	public YearVolatility(Year year) {
		this.year = year;
	}

	public Year getYear() {
		return year;
	}

	public int getCount() {
		int count = 0;
		for (MonthVolatility month : months.values()) {
			count += month.getCount();
		}
		return count;
	}

	public List<IPriceCandle> getCandles() {
		List<IPriceCandle> candles = new ArrayList<>();
		for (MonthVolatility month : months.values()) {
			candles.addAll(month.getCandles());
		}
		return candles;
	}

	public void add(LocalDateTime date, IPriceCandle candle) {
		Month month = date.getMonth();
		MonthVolatility volatility = months.get(month);
		if (volatility == null) {
			volatility = new MonthVolatility(month);
			months.put(month, volatility);
		}
		volatility.add(date, candle);
	}

	public List<MonthVolatility> getMonths() {
		return ImmutableList.copyOf(months.values());
	}

}
