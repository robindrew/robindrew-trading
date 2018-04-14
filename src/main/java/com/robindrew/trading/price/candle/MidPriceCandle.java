package com.robindrew.trading.price.candle;

import static com.robindrew.common.date.Dates.toLocalDateTime;

import com.robindrew.common.concurrent.Immutable;

@Immutable
public class MidPriceCandle extends AbstractPriceCandle {

	private final int openPrice;
	private final int highPrice;
	private final int lowPrice;
	private final int closePrice;
	private final long openTime;
	private final long closeTime;
	private final byte decimalPlaces;

	public MidPriceCandle(int openPrice, int highPrice, int lowPrice, int closePrice, long openTime, long closeTime, int decimalPlaces) {
		if (openPrice <= 0) {
			throw new IllegalArgumentException("openPrice=" + openPrice);
		}
		if (highPrice <= 0) {
			throw new IllegalArgumentException("highPrice=" + highPrice);
		}
		if (lowPrice <= 0) {
			throw new IllegalArgumentException("lowPrice=" + lowPrice);
		}
		if (closePrice <= 0) {
			throw new IllegalArgumentException("closePrice=" + closePrice);
		}

		// Sanity checks
		if (highPrice < openPrice) {
			throw new IllegalArgumentException("highPrice=" + highPrice + ", openPrice=" + openPrice);
		}
		if (highPrice < closePrice) {
			throw new IllegalArgumentException("highPrice=" + highPrice + ", closePrice=" + closePrice);
		}
		if (lowPrice > openPrice) {
			throw new IllegalArgumentException("lowPrice=" + lowPrice + ", openPrice=" + openPrice);
		}
		if (lowPrice > closePrice) {
			throw new IllegalArgumentException("lowPrice=" + lowPrice + ", closePrice=" + closePrice);
		}
		if (openTime > closeTime) {
			throw new IllegalArgumentException("openTime=" + openTime + ", closeTime=" + closeTime);
		}
		if (decimalPlaces < 0 || decimalPlaces > 6) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}

		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.decimalPlaces = (byte) decimalPlaces;
	}

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
	public int getMidOpenPrice() {
		return openPrice;
	}

	@Override
	public int getMidClosePrice() {
		return closePrice;
	}

	@Override
	public int getMidHighPrice() {
		return highPrice;
	}

	@Override
	public int getMidLowPrice() {
		return lowPrice;
	}

	@Override
	public long getOpenTime() {
		return openTime;
	}

	@Override
	public long getCloseTime() {
		return closeTime;
	}

	@Override
	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	@Override
	public int getBidOpenPrice() {
		return openPrice;
	}

	@Override
	public int getAskOpenPrice() {
		return openPrice;
	}

	@Override
	public int getBidHighPrice() {
		return highPrice;
	}

	@Override
	public int getAskHighPrice() {
		return highPrice;
	}

	@Override
	public int getBidLowPrice() {
		return lowPrice;
	}

	@Override
	public int getAskLowPrice() {
		return lowPrice;
	}

	@Override
	public int getBidClosePrice() {
		return closePrice;
	}

	@Override
	public int getAskClosePrice() {
		return closePrice;
	}

	@Override
	public long getTickVolume() {
		return 0;
	}
}
