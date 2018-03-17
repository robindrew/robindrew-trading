package com.robindrew.trading.backtest.analysis.volatility;

import static com.robindrew.common.text.Strings.number;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.price.candle.IPriceCandle;

public class VolatilityReport {

	private static final Logger log = LoggerFactory.getLogger(VolatilityReport.class);

	private final Map<Year, YearVolatility> years = new TreeMap<>();

	public void add(LocalDateTime date, IPriceCandle candle) {
		Year year = Year.of(date.getYear());
		YearVolatility volatility = years.get(year);
		if (volatility == null) {
			volatility = new YearVolatility(year);
			years.put(year, volatility);
		}
		volatility.add(date, candle);
	}

	public void logReport() {
		for (YearVolatility year : years.values()) {
			for (MonthVolatility month : year.getMonths()) {
				log.info("{} {}", month.getMonth(), year.getYear());
				log.info("candles = {}", number(month.getCount()));
				log.info("buy = {}", number(month.getBuy()));
				log.info("sell = {}", number(month.getSell()));
			}

		}
	}

}
