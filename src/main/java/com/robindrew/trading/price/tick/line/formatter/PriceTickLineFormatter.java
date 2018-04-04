package com.robindrew.trading.price.tick.line.formatter;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import com.robindrew.trading.price.tick.IPriceTick;

public class PriceTickLineFormatter implements IPriceTickLineFormatter {

	private static final String DEFAULT_EOL = "\n";

	private String endOfLine = DEFAULT_EOL;

	@Override
	public String formatTick(IPriceTick tick, boolean includeEndOfLine) {

		StringBuilder line = new StringBuilder();
		line.append(toLocalDateTime(tick.getOpenTime())).append(',');
		line.append(toLocalDateTime(tick.getCloseTime())).append(',');
		line.append(tick.getBidPrice()).append(',');
		line.append(tick.getAskPrice());

		// End of line?
		if (includeEndOfLine) {
			line.append(getEndOfLine());
		}

		return line.toString();
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
