package com.robindrew.trading.price.candle.line.formatter;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.ITickPriceCandle;
import com.robindrew.trading.price.candle.MidPriceCandle;

public class PriceCandleLineFormatter implements IPriceCandleLineFormatter {

	private static final String DEFAULT_EOL = "\n";

	private String endOfLine = DEFAULT_EOL;

	@Override
	public String formatCandle(IPriceCandle candle, boolean includeEndOfLine) {

		StringBuilder line = new StringBuilder();

		// Tick
		if (candle instanceof ITickPriceCandle) {
			formatCandle(line, (ITickPriceCandle) candle);
		} else if (candle instanceof MidPriceCandle) {
			formatCandle(line, (MidPriceCandle) candle);
		} else {
			formatCandle(line, candle);
		}

		// End of line?
		if (includeEndOfLine) {
			line.append(getEndOfLine());
		}

		return line.toString();
	}

	private void formatCandle(StringBuilder line, ITickPriceCandle candle) {
		// Tick
		line.append(toLocalDateTime(candle.getTimestamp())).append(',');
		line.append(candle.getBidPrice()).append(',');
		line.append(candle.getAskPrice());
	}

	private void formatCandle(StringBuilder line, MidPriceCandle candle) {

		// Dates
		line.append(toLocalDateTime(candle.getOpenTime())).append(',');
		line.append(toLocalDateTime(candle.getCloseTime())).append(',');

		// Mid
		line.append(candle.getMidOpenPrice()).append(',');
		line.append(candle.getMidHighPrice()).append(',');
		line.append(candle.getMidLowPrice()).append(',');
		line.append(candle.getMidClosePrice()).append(',');

		// Volume
		line.append(candle.getTickVolume());
	}

	private void formatCandle(StringBuilder line, IPriceCandle candle) {

		// Dates
		line.append(toLocalDateTime(candle.getOpenTime())).append(',');
		line.append(toLocalDateTime(candle.getCloseTime())).append(',');

		// Bid
		line.append(candle.getBidOpenPrice()).append(',');
		line.append(candle.getBidHighPrice()).append(',');
		line.append(candle.getBidLowPrice()).append(',');
		line.append(candle.getBidClosePrice()).append(',');

		// Ask
		line.append(candle.getAskOpenPrice()).append(',');
		line.append(candle.getAskHighPrice()).append(',');
		line.append(candle.getAskLowPrice()).append(',');
		line.append(candle.getAskClosePrice()).append(',');

		// Volume
		line.append(candle.getTickVolume());
	}

	public String getEndOfLine() {
		return endOfLine;
	}

	public void setEndOfLine(String endOfLine) {
		if (endOfLine.isEmpty()) {
			throw new IllegalArgumentException("endOfLine is empty");
		}
		this.endOfLine = endOfLine;
	}

}
