package com.robindrew.trading.provider.histdata.line;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.robindrew.common.text.tokenizer.CharTokenizer;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.format.pcf.FloatingPoint;
import com.robindrew.trading.price.tick.PriceTick;
import com.robindrew.trading.provider.histdata.HistDataInstrument;

public class HistDataTickLineParser extends HistDataLineParser {

	private static DateTimeFormatter DATE_FORMAT = ofPattern("yyyyMMdd");
	private static DateTimeFormatter TIME_FORMAT = ofPattern("HHmmssSSS");

	private final HistDataInstrument instrument;

	public HistDataTickLineParser(HistDataInstrument instrument) {
		if (instrument == null) {
			throw new NullPointerException("instrument");
		}
		this.instrument = instrument;
	}

	@Override
	public IPriceCandle parseCandle(String line) {
		CharTokenizer tokenizer = new CharTokenizer(line, DELIMITERS);
		int decimalPlaces = instrument.getPricePrecision().getDecimalPlaces();

		// Dates
		LocalDate date = LocalDate.parse(tokenizer.next(false), DATE_FORMAT);
		LocalTime time = LocalTime.parse(tokenizer.next(false), TIME_FORMAT);
		long timestamp = toMillis(LocalDateTime.of(date, time));

		// Prices
		BigDecimal bid = new BigDecimal(tokenizer.next(false));
		BigDecimal ask = new BigDecimal(tokenizer.next(false));

		int bidPrice = FloatingPoint.toBigInt(bid, decimalPlaces);
		int askPrice = FloatingPoint.toBigInt(ask, decimalPlaces);
		
		return new PriceTick(bidPrice, askPrice, timestamp, decimalPlaces);
	}

}
