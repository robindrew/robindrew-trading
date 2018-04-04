package com.robindrew.trading.provider.histdata.line;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.robindrew.common.text.tokenizer.CharTokenizer;
import com.robindrew.trading.price.PriceException;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandle;
import com.robindrew.trading.price.decimal.Decimals;
import com.robindrew.trading.provider.histdata.HistDataInstrument;

public class HistDataM1LineParser extends HistDataLineParser {

	private static DateTimeFormatter TIME_FORMAT_8 = ofPattern("HHmmss");
	private static DateTimeFormatter DATE_FORMAT_8 = ofPattern("yyyyMMdd");
	private static DateTimeFormatter DATE_FORMAT_10 = ofPattern("yyyy.MM.dd");

	private final HistDataInstrument instrument;

	public HistDataM1LineParser(HistDataInstrument instrument) {
		if (instrument == null) {
			throw new NullPointerException("instrument");
		}
		this.instrument = instrument;
	}

	@Override
	public IPriceCandle parseCandle(String line) {
		try {
			CharTokenizer tokenizer = new CharTokenizer(line, DELIMITERS);
			int decimalPlaces = instrument.getPricePrecision().getDecimalPlaces();

			// Dates
			LocalDate date = parseDate(tokenizer.next(false));
			LocalTime openTime = parseTime(tokenizer.next(false));
			LocalDateTime openDate = LocalDateTime.of(date, openTime);
			LocalDateTime closeDate = openDate.plus(1, MINUTES);

			// Prices
			int open = Decimals.toBigInt(tokenizer.next(false), decimalPlaces);
			int high = Decimals.toBigInt(tokenizer.next(false), decimalPlaces);
			int low = Decimals.toBigInt(tokenizer.next(false), decimalPlaces);
			int close = Decimals.toBigInt(tokenizer.next(false), decimalPlaces);

			return new PriceCandle(open, high, low, close, toMillis(openDate), toMillis(closeDate), decimalPlaces);
		} catch (Exception e) {
			throw new PriceException("Failed to parse candle from line: '" + line + "'", e);
		}
	}

	private LocalTime parseTime(String text) {
		if (text.length() == 6) {
			return LocalTime.parse(text, TIME_FORMAT_8);
		}
		return LocalTime.parse(text);
	}

	private LocalDate parseDate(String text) {
		if (text.length() == 8) {
			return LocalDate.parse(text, DATE_FORMAT_8);
		}
		if (text.length() == 10) {
			return LocalDate.parse(text, DATE_FORMAT_10);
		}
		return LocalDate.parse(text);
	}
}
