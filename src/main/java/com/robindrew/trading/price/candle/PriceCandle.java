package com.robindrew.trading.price.candle;

import com.robindrew.common.concurrent.Immutable;
import com.robindrew.trading.price.Mid;

@Immutable
public class PriceCandle extends AbstractPriceCandle {

	private final int bidOpenPrice;
	private final int bidHighPrice;
	private final int bidLowPrice;
	private final int bidClosePrice;

	private final int askOpenPrice;
	private final int askHighPrice;
	private final int askLowPrice;
	private final int askClosePrice;

	private final long openTime;
	private final long closeTime;

	private final byte decimalPlaces;

	public PriceCandle(int bidOpenPrice, int bidHighPrice, int bidLowPrice, int bidClosePrice, int askOpenPrice, int askHighPrice, int askLowPrice, int askClosePrice, long openTime, long closeTime, int decimalPlaces) {

		if (bidOpenPrice <= 0) {
			throw new IllegalArgumentException("bidOpenPrice=" + bidOpenPrice);
		}
		if (bidHighPrice <= 0) {
			throw new IllegalArgumentException("bidHighPrice=" + bidHighPrice);
		}
		if (bidLowPrice <= 0) {
			throw new IllegalArgumentException("bidLowPrice=" + bidLowPrice);
		}
		if (bidClosePrice <= 0) {
			throw new IllegalArgumentException("bidClosePrice=" + bidClosePrice);
		}

		if (askOpenPrice <= 0) {
			throw new IllegalArgumentException("askOpenPrice=" + askOpenPrice);
		}
		if (askHighPrice <= 0) {
			throw new IllegalArgumentException("askHighPrice=" + askHighPrice);
		}
		if (askLowPrice <= 0) {
			throw new IllegalArgumentException("askLowPrice=" + askLowPrice);
		}
		if (askClosePrice <= 0) {
			throw new IllegalArgumentException("askClosePrice=" + askClosePrice);
		}

		if (bidHighPrice < bidOpenPrice) {
			throw new IllegalArgumentException("bidHighPrice=" + bidHighPrice + ", bidOpenPrice=" + bidOpenPrice);
		}
		if (bidHighPrice < bidClosePrice) {
			throw new IllegalArgumentException("bidHighPrice=" + bidHighPrice + ", bidClosePrice=" + bidClosePrice);
		}
		if (bidLowPrice > bidOpenPrice) {
			throw new IllegalArgumentException("bidLowPrice=" + bidLowPrice + ", bidOpenPrice=" + bidOpenPrice);
		}
		if (bidLowPrice > bidClosePrice) {
			throw new IllegalArgumentException("bidLowPrice=" + bidLowPrice + ", bidClosePrice=" + bidClosePrice);
		}

		if (askHighPrice < askOpenPrice) {
			throw new IllegalArgumentException("askHighPrice=" + askHighPrice + ", askOpenPrice=" + askOpenPrice);
		}
		if (askHighPrice < askClosePrice) {
			throw new IllegalArgumentException("askHighPrice=" + askHighPrice + ", askClosePrice=" + askClosePrice);
		}
		if (askLowPrice > askOpenPrice) {
			throw new IllegalArgumentException("askLowPrice=" + askLowPrice + ", askOpenPrice=" + askOpenPrice);
		}
		if (askLowPrice > askClosePrice) {
			throw new IllegalArgumentException("askLowPrice=" + askLowPrice + ", askClosePrice=" + askClosePrice);
		}

		if (openTime > closeTime) {
			throw new IllegalArgumentException("openTime=" + openTime + ", closeTime=" + closeTime);
		}
		if (decimalPlaces < 0 || decimalPlaces > 6) {
			throw new IllegalArgumentException("decimalPlaces=" + decimalPlaces);
		}

		this.bidOpenPrice = bidOpenPrice;
		this.bidHighPrice = bidHighPrice;
		this.bidLowPrice = bidLowPrice;
		this.bidClosePrice = bidClosePrice;

		this.askOpenPrice = askOpenPrice;
		this.askHighPrice = askHighPrice;
		this.askLowPrice = askLowPrice;
		this.askClosePrice = askClosePrice;

		this.openTime = openTime;
		this.closeTime = closeTime;

		this.decimalPlaces = (byte) decimalPlaces;
	}

	@Override
	public int getMidOpenPrice() {
		return Mid.getMid(bidOpenPrice, askOpenPrice);
	}

	@Override
	public int getMidClosePrice() {
		return Mid.getMid(bidClosePrice, askClosePrice);
	}

	@Override
	public int getMidHighPrice() {
		return Mid.getMid(bidHighPrice, askHighPrice);
	}

	@Override
	public int getMidLowPrice() {
		return Mid.getMid(bidLowPrice, askLowPrice);
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
		return bidOpenPrice;
	}

	@Override
	public int getAskOpenPrice() {
		return askOpenPrice;
	}

	@Override
	public int getBidHighPrice() {
		return bidHighPrice;
	}

	@Override
	public int getAskHighPrice() {
		return askHighPrice;
	}

	@Override
	public int getBidLowPrice() {
		return bidLowPrice;
	}

	@Override
	public int getAskLowPrice() {
		return askLowPrice;
	}

	@Override
	public int getBidClosePrice() {
		return bidClosePrice;
	}

	@Override
	public int getAskClosePrice() {
		return askClosePrice;
	}

}
