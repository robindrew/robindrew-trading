package com.robindrew.trading.trade.window;

import java.time.LocalTime;
import java.util.Optional;

public class TradingHours implements ITradingHours {

	public static final LocalTime MIDNIGHT = LocalTime.of(0, 0);

	public static ITradingHours openFrom(LocalTime openTime) {
		return new TradingHours(openTime, null);
	}

	public static ITradingHours openUntil(LocalTime closeTime) {
		return new TradingHours(null, closeTime);
	}

	private final LocalTime openTime;
	private final LocalTime closeTime;

	public TradingHours(LocalTime openTime, LocalTime closeTime) {
		if (openTime == null && closeTime == null) {
			throw new IllegalArgumentException("openTime and closeTime are both null");
		}
		this.openTime = openTime;
		this.closeTime = closeTime;
	}

	public Optional<LocalTime> getOpenTime() {
		return Optional.ofNullable(openTime);
	}

	public Optional<LocalTime> getCloseTime() {
		return Optional.ofNullable(closeTime);
	}

	@Override
	public boolean contains(LocalTime time) {
		if (openTime != null && time.isBefore(openTime)) {
			return false;
		}
		if (closeTime != null && !time.isBefore(closeTime)) {
			return false;
		}
		return true;
	}

}
