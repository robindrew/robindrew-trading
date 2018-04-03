package com.robindrew.trading.price.tick;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import com.robindrew.trading.price.Mid;
import com.robindrew.trading.price.candle.AbstractPriceCandle;
import com.robindrew.trading.price.decimal.Decimal;
import com.robindrew.trading.price.decimal.IDecimal;

public abstract class AbstractPriceTick extends AbstractPriceCandle implements IPriceTick {

	@Override
	public int getMidPrice() {
		return Mid.getMid(getBidPrice(), getAskPrice());
	}

	@Override
	public int getOpenPrice() {
		return getMidPrice();
	}

	@Override
	public int getClosePrice() {
		return getMidPrice();
	}

	@Override
	public int getHighPrice() {
		return getMidPrice();
	}

	@Override
	public int getLowPrice() {
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
	public boolean hasClosedUp() {
		return false;
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PriceTick[");
		builder.append(getBidPrice()).append('|');
		builder.append(getAskPrice()).append('|');
		builder.append(toLocalDateTime(getCloseTime())).append(']');
		return builder.toString();
	}

	@Override
	public IDecimal getBid() {
		return new Decimal(getBidPrice(), getDecimalPlaces());
	}

	@Override
	public IDecimal getAsk() {
		return new Decimal(getAskPrice(), getDecimalPlaces());
	}

	@Override
	public IDecimal getMid() {
		return new Decimal(getMidPrice(), getDecimalPlaces());
	}

}
