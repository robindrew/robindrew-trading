package com.robindrew.trading.price.candle.line.parser;

import static com.robindrew.common.date.Dates.toMillis;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

import com.google.common.base.Charsets;
import com.robindrew.common.text.tokenizer.CharDelimiters;
import com.robindrew.common.text.tokenizer.CharTokenizer;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandle;
import com.robindrew.trading.price.tick.PriceTick;

public class PriceCandleLineParser implements IPriceCandleLineParser {

	public static final CharDelimiters DELIMITERS = new CharDelimiters().whitespace().character(',');

	private final int decimalPlaces;

	public PriceCandleLineParser(int decimalPlaces) {
		if (decimalPlaces < 0) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}
		this.decimalPlaces = decimalPlaces;
	}

	@Override
	public Charset getCharset() {
		return Charsets.US_ASCII;
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
			int bidPrice = Integer.parseInt(tokenizer.next(false));
			int askPrice = Integer.parseInt(tokenizer.next(false));

			return new PriceTick(bidPrice, askPrice, toMillis(date), decimalPlaces);
		}

		// Standard candle ...

		// Dates
		LocalDateTime openDate = LocalDateTime.parse(tokenizer.next(false));
		LocalDateTime closeDate = LocalDateTime.parse(tokenizer.next(false));

		// Prices
		int open = Integer.parseInt(tokenizer.next(false));
		int high = Integer.parseInt(tokenizer.next(false));
		int low = Integer.parseInt(tokenizer.next(false));
		int close = Integer.parseInt(tokenizer.next(false));

		return new PriceCandle(open, high, low, close, toMillis(openDate), toMillis(closeDate), decimalPlaces);
	}

}
