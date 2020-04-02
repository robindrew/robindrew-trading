package com.robindrew.trading.price.candle.tick;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import java.util.concurrent.atomic.AtomicBoolean;

import com.robindrew.trading.price.Mid;
import com.robindrew.trading.price.candle.AbstractPriceCandle;
import com.robindrew.trading.price.candle.IPriceCandle;

public class CachedTickPriceCandle extends AbstractPriceCandle implements ITickPriceCandle {

	private final AtomicBoolean active = new AtomicBoolean(false);
	private int bidPrice;
	private int askPrice;
	private long timestamp;
	private byte decimalPlaces;

	public void set(int bidPrice, int askPrice, long timestamp, int decimalPlaces) {
		if (bidPrice <= 0) {
			throw new IllegalArgumentException("bidPrice=" + bidPrice);
		}
		if (askPrice <= 0) {
			throw new IllegalArgumentException("askPrice=" + askPrice);
		}
		if (decimalPlaces < 0 || decimalPlaces > 8) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}

		// Check 'active' flag
		if (!active.compareAndSet(false, true)) {
			throw new IllegalStateException("Attempt to set active candle");
		}

		this.bidPrice = bidPrice;
		this.askPrice = askPrice;
		this.timestamp = timestamp;
		this.decimalPlaces = (byte) decimalPlaces;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TickPriceCandle[");
		builder.append(toLocalDateTime(getTimestamp())).append('|');
		builder.append(getBidPrice()).append('|');
		builder.append(getAskPrice()).append(']');
		return builder.toString();
	}

	@Override
	public boolean isTick() {
		return true;
	}

	@Override
	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public int getBidPrice() {
		return bidPrice;
	}

	@Override
	public int getAskPrice() {
		return askPrice;
	}

	@Override
	public int getMidPrice() {
		return Mid.getMid(bidPrice, askPrice);
	}

	@Override
	public int getBidOpenPrice() {
		return getBidPrice();
	}

	@Override
	public int getAskOpenPrice() {
		return getAskPrice();
	}

	@Override
	public int getBidHighPrice() {
		return getBidPrice();
	}

	@Override
	public int getAskHighPrice() {
		return getAskPrice();
	}

	@Override
	public int getBidLowPrice() {
		return getBidPrice();
	}

	@Override
	public int getAskLowPrice() {
		return getAskPrice();
	}

	@Override
	public int getBidClosePrice() {
		return getBidPrice();
	}

	@Override
	public int getAskClosePrice() {
		return getAskPrice();
	}

	@Override
	public int getMidOpenPrice() {
		return getMidPrice();
	}

	@Override
	public int getMidHighPrice() {
		return getMidPrice();
	}

	@Override
	public int getMidLowPrice() {
		return getMidPrice();
	}

	@Override
	public int getMidClosePrice() {
		return getMidPrice();
	}

	@Override
	public long getOpenTime() {
		return getTimestamp();
	}

	@Override
	public long getCloseTime() {
		return getTimestamp();
	}

	@Override
	public long getTickVolume() {
		// This is a single tick!
		return 1;
	}

	@Override
	public IPriceCandle withDecimalPlaces(int decimalPlaces) {
		throw new UnsupportedOperationException();
	}

	public void release() {
		if (!active.compareAndSet(true, false)) {
			throw new IllegalStateException("Attempt to release inactive candle");
		}
	}
}
