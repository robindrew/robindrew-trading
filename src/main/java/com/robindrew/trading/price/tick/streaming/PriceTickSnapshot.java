package com.robindrew.trading.price.tick.streaming;

import static com.robindrew.trading.trade.TradeDirection.BUY;
import static com.robindrew.trading.trade.TradeDirection.SELL;

import java.util.Optional;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.trade.TradeDirection;

public class PriceTickSnapshot implements IPriceTickSnapshot {

	private final long timestamp;
	private final IPriceTick previous;
	private final IPriceTick latest;

	public PriceTickSnapshot(IPriceTick next) {
		this(next, next.getCloseTime());
	}

	public PriceTickSnapshot(IPriceTick latest, long timestamp) {
		this.latest = Check.notNull("latest", latest);
		this.timestamp = timestamp;
		this.previous = null;
	}

	public PriceTickSnapshot(IPriceTick latest, long timestamp, IPriceTick previous) {
		this.latest = Check.notNull("latest", latest);
		this.timestamp = timestamp;
		this.previous = Check.notNull("previous", previous);
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Optional<IPriceTick> getPrevious() {
		return Optional.ofNullable(previous);
	}

	public IPriceTick getLatest() {
		return latest;
	}

	public TradeDirection getDirection() {
		if (previous == null) {
			return BUY;
		}
		return previous.getClosePrice() <= latest.getClosePrice() ? BUY : SELL;
	}

	public PriceTickSnapshot update(IPriceTick latest) {
		return update(latest, latest.getCloseTime());
	}

	public PriceTickSnapshot update(IPriceTick latest, long timestamp) {
		return new PriceTickSnapshot(latest, timestamp, getLatest());
	}

}
