package com.robindrew.trading.position;

import static com.robindrew.trading.trade.TradeDirection.BUY;
import static java.math.RoundingMode.CEILING;

import java.math.BigDecimal;

import com.robindrew.trading.position.market.IMarketPrice;

public class Positions {

	public static BigDecimal getTradeProfit(IPosition position, IMarketPrice marketPrice) {
		BigDecimal open = position.getOpenPrice();

		if (position.getDirection().equals(BUY)) {
			BigDecimal close = marketPrice.getBid();
			return close.subtract(open).multiply(position.getTradeSize()).setScale(2, CEILING);
		} else {
			BigDecimal close = marketPrice.getAsk();
			return open.subtract(close).multiply(position.getTradeSize()).setScale(2, CEILING);
		}
	}

	public static BigDecimal getProfit(IPosition position, IMarketPrice marketPrice) {
		BigDecimal absolute = getTradeProfit(position, marketPrice);
		if (absolute.doubleValue() >= 0.0) {
			return absolute;
		}
		return BigDecimal.ZERO;
	}

	public static BigDecimal getLoss(IPosition position, IMarketPrice marketPrice) {
		BigDecimal absolute = getTradeProfit(position, marketPrice);
		if (absolute.doubleValue() < 0.0) {
			return absolute.abs();
		}
		return BigDecimal.ZERO;
	}

	public static boolean isProfit(IPosition position, IMarketPrice marketPrice) {
		return getTradeProfit(position, marketPrice).doubleValue() >= 0.0;
	}

}
