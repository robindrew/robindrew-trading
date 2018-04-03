package com.robindrew.trading.price.candle.indicator.custom;

import static com.robindrew.trading.trade.TradeDirection.BUY;
import static com.robindrew.trading.trade.TradeDirection.SELL;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.indicator.AbstractIndicator;
import com.robindrew.trading.price.candle.interval.IPriceInterval;
import com.robindrew.trading.trade.TradeDirection;

public class DirectionIndicator extends AbstractIndicator {

	private final AtomicLong averageBuy = new AtomicLong(0);
	private final AtomicLong averageSell = new AtomicLong(0);

	public DirectionIndicator(String name, IPriceInterval interval, int capacity) {
		super(name, interval, capacity);
	}

	@Override
	protected void calculate(List<IPriceCandle> candles) {

		long totalBuy = 0;
		long totalSell = 0;
		for (IPriceCandle candle : candles) {
			if (candle.hasClosedUp()) {
				totalBuy += candle.getOpenCloseRange();
			} else {
				totalSell += candle.getOpenCloseRange();
			}
		}

		averageBuy.set(totalBuy / candles.size());
		averageSell.set(totalSell / candles.size());
		setAvailable(true);
	}

	public boolean isBuy() {
		return averageBuy.get() > averageSell.get();
	}

	public TradeDirection getDirection() {
		return isBuy() ? BUY : SELL;
	}

}
