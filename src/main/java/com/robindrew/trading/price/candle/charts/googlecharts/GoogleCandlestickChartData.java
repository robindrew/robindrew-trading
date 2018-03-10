package com.robindrew.trading.price.candle.charts.googlecharts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.robindrew.common.date.Dates;
import com.robindrew.trading.price.candle.IPriceCandle;

public class GoogleCandlestickChartData {

	private final List<IPriceCandle> candles;
	private DateTimeFormatter format;

	public GoogleCandlestickChartData(List<IPriceCandle> candles, DateTimeFormatter format) {
		if (candles.isEmpty()) {
			throw new IllegalArgumentException("candles is empty");
		}
		if (format == null) {
			throw new NullPointerException("format");
		}
		this.candles = candles;
		this.format = format;
	}

	public GoogleCandlestickChartData(List<IPriceCandle> candles, String dateFormat) {
		this(candles, DateTimeFormatter.ofPattern(dateFormat));
	}

	public String toChartData() {
		StringBuilder script = new StringBuilder();

		boolean comma = false;
		for (IPriceCandle candle : candles) {
			if (comma) {
				script.append(',').append('\n');
			}
			comma = true;
			// ['Mon 12th', 2.2, 28, 38, 45],

			script.append('[');
			script.append('\'').append(getLabel(candle)).append("',");
			if (candle.hasClosedUp()) {
				script.append(candle.getLowPrice()).append(',');
				script.append(candle.getOpenPrice()).append(',');
				script.append(candle.getClosePrice()).append(',');
				script.append(candle.getHighPrice());
			} else {
				script.append(candle.getHighPrice()).append(',');
				script.append(candle.getOpenPrice()).append(',');
				script.append(candle.getClosePrice()).append(',');
				script.append(candle.getLowPrice());
			}
			script.append(']');
		}

		return script.toString();
	}

	private String getLabel(IPriceCandle candle) {
		LocalDateTime date = Dates.toLocalDateTime(candle.getOpenTime());
		return format.format(date);
	}
}
