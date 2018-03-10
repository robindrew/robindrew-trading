package com.robindrew.trading.price.candle.line.filter;

import java.util.HashSet;
import java.util.Set;

public class DeclineSetFilter extends LineFilter {

	private final Set<String> declineSet = new HashSet<>();

	public DeclineSetFilter add(String line) {
		declineSet.add(line);
		return this;
	}

	@Override
	public String filter(String line) {
		line = super.filter(line);
		if (line == null || declineSet.contains(line)) {
			return null;
		}
		return line;
	}

}
