package com.robindrew.trading.price.candle.line.parser;

import static com.robindrew.common.date.Dates.toMillis;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

import com.google.common.base.Charsets;
import com.robindrew.common.text.tokenizer.CharDelimiters;
import com.robindrew.common.text.tokenizer.CharTokenizer;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.MidPriceCandle;
import com.robindrew.trading.price.candle.TickPriceCandle;
import com.robindrew.trading.price.decimal.Decimals;

public class PriceCandleLineDecimalParser implements IPriceCandleLineParser {

	public static final CharDelimiters DELIMITERS = new CharDelimiters().whitespace().character(',');

	private final int decimalPlaces;
	private final boolean checkPlaces;

	public PriceCandleLineDecimalParser(int decimalPlaces, boolean checkPlaces) {
		if (decimalPlaces < 0) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}
		this.decimalPlaces = decimalPlaces;
		this.checkPlaces = checkPlaces;
	}

	@Override
	public boolean skipLine(String line) {
		return false;
	}

	@Override
	public IPriceCandle parseCandle(String line) {

		CharTokenizer tokenizer = new CharTokenizer(line, DELIMITERS);

		// Instant candle
		if (line.indexOf(',') == line.lastIndexOf(',')) {

			LocalDateTime date = LocalDateTime.parse(tokenizer.next(false));
			int bidPrice = Decimals.toInt(tokenizer.next(false), decimalPlaces, checkPlaces);
			int askPrice = Decimals.toInt(tokenizer.next(false), decimalPlaces, checkPlaces);

			return new TickPriceCandle(bidPrice, askPrice, toMillis(date), decimalPlaces);
		}

		// Standard candle ...

		// Dates
		LocalDateTime openDate = LocalDateTime.parse(tokenizer.next(false));
		LocalDateTime closeDate = LocalDateTime.parse(tokenizer.next(false));

		// Prices
		int open = Decimals.toInt(tokenizer.next(false), decimalPlaces, checkPlaces);
		int high = Decimals.toInt(tokenizer.next(false), decimalPlaces, checkPlaces);
		int low = Decimals.toInt(tokenizer.next(false), decimalPlaces, checkPlaces);
		int close = Decimals.toInt(tokenizer.next(false), decimalPlaces, checkPlaces);

		long tickVolume = Long.parseLong(tokenizer.next(false));

		return new MidPriceCandle(open, high, low, close, toMillis(openDate), toMillis(closeDate), decimalPlaces, tickVolume);
	}

	@Override
	public Charset getCharset() {
		return Charsets.US_ASCII;
	}
}
