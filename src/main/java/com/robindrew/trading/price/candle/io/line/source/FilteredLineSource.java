package com.robindrew.trading.price.candle.io.line.source;

import com.robindrew.trading.price.candle.line.filter.ILineFilter;

public class FilteredLineSource implements ILineSource {

	private final ILineSource source;
	private final ILineFilter filter;

	public FilteredLineSource(ILineSource source, ILineFilter filter) {
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (filter == null) {
			throw new NullPointerException("filter");
		}
		this.source = source;
		this.filter = filter;
	}

	public ILineSource getSource() {
		return source;
	}

	public ILineFilter getFilter() {
		return filter;
	}

	@Override
	public String getName() {
		return source.getName();
	}

	@Override
	public void close() {
		source.close();
	}

	@Override
	public String getNextLine() {
		while (true) {
			String line = source.getNextLine();

			// End of source?
			if (line == null) {
				return null;
			}

			line = filter.filter(line);
			if (line == null) {
				return getNextLine();
			}

			return line;
		}
	}
}
