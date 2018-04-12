package com.robindrew.trading.price.candle;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import java.time.LocalDateTime;

public abstract class AbstractPriceCandle implements IPriceCandle {

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PriceCandle[");
		builder.append(toLocalDateTime(getOpenTime())).append('|');
		builder.append(toLocalDateTime(getCloseTime())).append('|');
		builder.append(getMidOpenPrice()).append('|');
		builder.append(getMidHighPrice()).append('|');
		builder.append(getMidLowPrice()).append('|');
		builder.append(getMidClosePrice()).append(']');
		return builder.toString();
	}

	@Override
	public long getCloseMove() {
		return getMidClosePrice() - getMidOpenPrice();
	}

	@Override
	public boolean isTick() {
		return getOpenTime() == getCloseTime();
	}

	@Override
	public IPriceCandle mergeWith(IPriceCandle candle) {
		return PriceCandles.merge(this, candle);
	}

	@Override
	public boolean after(IPriceCandle candle) {
		return getOpenTime() > candle.getCloseTime();
	}

	@Override
	public boolean before(IPriceCandle candle) {
		return getMidClosePrice() < candle.getOpenTime();
	}

	@Override
	public boolean containsPrice(int price) {
		return getMidLowPrice() <= price && price <= getMidHighPrice();
	}

	@Override
	public boolean hasClosedUp() {
		return getMidClosePrice() >= getMidOpenPrice();
	}

	@Override
	public long getHighLowRange() {
		return getMidHighPrice() - getMidLowPrice();
	}

	@Override
	public long getOpenCloseRange() {
		return Math.abs(getCloseMove());
	}

	@Override
	public LocalDateTime getOpenDate() {
		return toLocalDateTime(getOpenTime());
	}

	@Override
	public LocalDateTime getCloseDate() {
		return toLocalDateTime(getCloseTime());
	}

	@Override
	public int hashCode() {
		return (int) ((getOpenTime() * 1999) + (getCloseTime() * 37));
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (object instanceof IPriceCandle) {
			IPriceCandle candle = (IPriceCandle) object;
			if (getOpenTime() != candle.getOpenTime()) {
				return false;
			}
			if (getCloseTime() != candle.getCloseTime()) {
				return false;
			}
			if (getMidOpenPrice() != candle.getMidOpenPrice()) {
				return false;
			}
			if (getMidHighPrice() != candle.getMidHighPrice()) {
				return false;
			}
			if (getMidLowPrice() != candle.getMidLowPrice()) {
				return false;
			}
			if (getMidClosePrice() != candle.getMidClosePrice()) {
				return false;
			}
			return true;
		}
		return false;
	}

}
