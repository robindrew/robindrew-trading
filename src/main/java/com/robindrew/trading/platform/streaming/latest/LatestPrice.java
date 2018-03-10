package com.robindrew.trading.platform.streaming.latest;

import static com.robindrew.trading.trade.TradeDirection.BUY;
import static com.robindrew.trading.trade.TradeDirection.SELL;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.trade.TradeDirection;

public class LatestPrice implements ILatestPrice {

	private final AtomicReference<IPriceCandle> price = new AtomicReference<IPriceCandle>();
	private final AtomicReference<TradeDirection> direction = new AtomicReference<TradeDirection>(BUY);
	private final AtomicLong time = new AtomicLong(0);
	private final AtomicLong count = new AtomicLong(0);

	@Override
	public IPriceCandle getPrice() {
		return price.get();
	}

	@Override
	public TradeDirection getDirection() {
		return direction.get();
	}

	@Override
	public long getUpdateTime() {
		return time.get();
	}

	@Override
	public long getUpdateCount() {
		return count.get();
	}

	public void update(long timestamp, IPriceCandle next) {
		time.set(timestamp);
		IPriceCandle previous = price.getAndSet(next);
		if (previous != null && previous.getClosePrice() > next.getClosePrice()) {
			direction.set(SELL);
		} else {
			direction.set(BUY);
		}
		count.incrementAndGet();
	}

}
