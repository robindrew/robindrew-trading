package com.robindrew.trading.price.candle.line.filter;

public class LineFilter implements ILineFilter {

	private boolean skipEmptyLines = true;
	private boolean trimLines = true;

	@Override
	public String filter(String line) {

		// Trim whitespace from lines?
		if (trimLines) {
			line = line.trim();
		}

		// Skip empty lines?
		if (skipEmptyLines && line.isEmpty()) {
			return null;
		}

		return line;
	}

	public boolean isSkipEmptyLines() {
		return skipEmptyLines;
	}

	public boolean isTrimLines() {
		return trimLines;
	}

	public void setSkipEmptyLines(boolean skipEmptyLines) {
		this.skipEmptyLines = skipEmptyLines;
	}

	public void setTrimLines(boolean trimLines) {
		this.trimLines = trimLines;
	}

}
