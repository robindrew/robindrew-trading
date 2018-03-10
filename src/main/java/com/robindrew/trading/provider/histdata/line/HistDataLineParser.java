package com.robindrew.trading.provider.histdata.line;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.robindrew.common.date.Dates;
import com.robindrew.common.text.tokenizer.CharDelimiters;
import com.robindrew.trading.price.candle.line.parser.IPriceCandleLineParser;

public abstract class HistDataLineParser implements IPriceCandleLineParser {

	public static final CharDelimiters DELIMITERS = new CharDelimiters().whitespace().characters(',', ';');

	public static final ZoneOffset EST = ZoneOffset.of("-05:00");

	public static long toMillis(LocalDateTime date) {
		return Dates.toMillis(date, EST);
	}

	@Override
	public boolean skipLine(String line) {
		return false;
	}

}
