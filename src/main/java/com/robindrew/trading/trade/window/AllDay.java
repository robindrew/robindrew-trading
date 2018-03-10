package com.robindrew.trading.trade.window;

import java.time.LocalTime;

public class AllDay implements ITradingHours {

	public static final AllDay OPEN = new AllDay(true);
	public static final AllDay CLOSED = new AllDay(false);

	private final boolean open;

	public AllDay(boolean open) {
		this.open = open;
	}

	@Override
	public boolean contains(LocalTime time) {
		return open;
	}

}
