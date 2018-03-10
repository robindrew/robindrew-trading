package com.robindrew.trading.price.candle.checker;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandles;

public class PriceCandleSanityChecker implements IPriceCandleChecker {

	private static final Logger log = LoggerFactory.getLogger(PriceCandleSanityChecker.class);

	private static final DecimalFormat format = new DecimalFormat("0.00");

	private final double maxPercentDiff;
	private boolean logFailure = true;
	private boolean throwFailure = true;

	public PriceCandleSanityChecker(double maxPercentDiff) {
		this.maxPercentDiff = maxPercentDiff;
	}

	public boolean isLogFailure() {
		return logFailure;
	}

	public boolean isThrowFailure() {
		return throwFailure;
	}

	public void setLogFailure(boolean enable) {
		this.logFailure = enable;
	}

	public void setThrowFailure(boolean enable) {
		this.throwFailure = enable;
	}

	public void checkDifference(int price1, int price2, IPriceCandle candle1, IPriceCandle candle2) {
		if (price1 == price2) {
			return;
		}

		double percentDiff = PriceCandles.getPercentDifference(price1, price2);
		if (percentDiff >= maxPercentDiff) {
			String percent = format.format(percentDiff);

			String message = "Maximum percent threshold (" + percent + "% > " + maxPercentDiff + "%) exceeded, candle1=" + candle1 + ", candle2=" + candle2;
			if (logFailure) {
				log.warn(message);
			}
			if (throwFailure) {
				throw new IllegalArgumentException(message);
			}
		}
	}

	@Override
	public boolean check(IPriceCandle candle1, IPriceCandle candle2) {
		checkDifference(candle1.getOpenPrice(), candle2.getOpenPrice(), candle1, candle2);
		checkDifference(candle1.getClosePrice(), candle2.getClosePrice(), candle1, candle2);
		checkDifference(candle1.getHighPrice(), candle2.getHighPrice(), candle1, candle2);
		checkDifference(candle1.getLowPrice(), candle2.getLowPrice(), candle1, candle2);
		return true;
	}

}
