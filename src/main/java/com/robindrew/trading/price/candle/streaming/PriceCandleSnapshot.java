package com.robindrew.trading.price.candle.streaming;

import static com.robindrew.trading.trade.TradeDirection.BUY;
import static com.robindrew.trading.trade.TradeDirection.SELL;

import java.util.Optional;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.trade.TradeDirection;

public class PriceCandleSnapshot implements IPriceCandleSnapshot {

	private final long timestamp;
	private final IPriceCandle previous;
	private final IPriceCandle latest;

	public PriceCandleSnapshot(IPriceCandle next) {
		this(next, next.getCloseTime());
	}

	public PriceCandleSnapshot(IPriceCandle latest, long timestamp) {
		this.latest = Check.notNull("latest", latest);
		this.timestamp = timestamp;
		this.previous = null;
	}

	public PriceCandleSnapshot(IPriceCandle latest, long timestamp, IPriceCandle previous) {
		this.latest = Check.notNull("latest", latest);
		this.timestamp = timestamp;
		this.previous = Check.notNull("previous", previous);
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Optional<IPriceCandle> getPrevious() {
		return Optional.ofNullable(previous);
	}

	public IPriceCandle getLatest() {
		return latest;
	}

	public TradeDirection getDirection() {
		if (previous == null) {
			return BUY;
		}
		return previous.getClosePrice() <= latest.getClosePrice() ? BUY : SELL;
	}

	public PriceCandleSnapshot update(IPriceCandle latest) {
		return update(latest, latest.getCloseTime());
	}

	public PriceCandleSnapshot update(IPriceCandle latest, long timestamp) {
		return new PriceCandleSnapshot(latest, timestamp, getLatest());
	}

}
