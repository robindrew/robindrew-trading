package com.robindrew.trading.price.candle;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import java.time.LocalDateTime;

import com.robindrew.trading.price.decimal.Decimal;
import com.robindrew.trading.price.decimal.IDecimal;

public abstract class AbstractPriceCandle implements IPriceCandle {

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PriceCandle[");
		builder.append(toLocalDateTime(getOpenTime())).append('|');
		builder.append(toLocalDateTime(getCloseTime())).append('|');
		builder.append(getOpenPrice()).append('|');
		builder.append(getHighPrice()).append('|');
		builder.append(getLowPrice()).append('|');
		builder.append(getClosePrice()).append(']');
		return builder.toString();
	}

	@Override
	public IDecimal getOpen() {
		return new Decimal(getOpenPrice(), getDecimalPlaces());
	}

	@Override
	public IDecimal getHigh() {
		return new Decimal(getHighPrice(), getDecimalPlaces());
	}

	@Override
	public IDecimal getLow() {
		return new Decimal(getLowPrice(), getDecimalPlaces());
	}

	@Override
	public IDecimal getClose() {
		return new Decimal(getClosePrice(), getDecimalPlaces());
	}

	@Override
	public long getCloseAmount() {
		return getClosePrice() - getOpenPrice();
	}

	@Override
	public boolean isInstant() {
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
		return getClosePrice() < candle.getOpenTime();
	}

	@Override
	public boolean containsPrice(int price) {
		return getLowPrice() <= price && price <= getHighPrice();
	}

	@Override
	public boolean hasClosedUp() {
		return getClosePrice() >= getOpenPrice();
	}

	@Override
	public long getHighLowRange() {
		return getHighPrice() - getLowPrice();
	}

	@Override
	public long getOpenCloseRange() {
		return Math.abs(getCloseAmount());
	}

	@Override
	public double getMedian() {
		if (isInstant()) {
			return getClosePrice();
		}
		return (getHighPrice() + getLowPrice()) / 2.0;
	}

	@Override
	public double getTypical() {
		if (isInstant()) {
			return getClosePrice();
		}
		return (getHighPrice() + getLowPrice() + getClosePrice()) / 3.0;
	}

	@Override
	public double getWeighted() {
		if (isInstant()) {
			return getClosePrice();
		}
		return (getHighPrice() + getLowPrice() + getClosePrice() + getClosePrice()) / 4.0;
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
			if (getOpenPrice() != candle.getOpenPrice()) {
				return false;
			}
			if (getHighPrice() != candle.getHighPrice()) {
				return false;
			}
			if (getLowPrice() != candle.getLowPrice()) {
				return false;
			}
			if (getClosePrice() != candle.getClosePrice()) {
				return false;
			}
			return true;
		}
		return false;
	}

}
