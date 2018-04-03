package com.robindrew.trading.price.tick.checker;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.PriceTicks;

public class PriceTickSanityChecker implements IPriceTickChecker {

	private static final Logger log = LoggerFactory.getLogger(PriceTickSanityChecker.class);

	private static final DecimalFormat format = new DecimalFormat("0.00");

	private final double maxPercentDiff;
	private boolean logFailure = true;
	private boolean throwFailure = true;

	public PriceTickSanityChecker(double maxPercentDiff) {
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

	public void checkDifference(int price1, int price2, IPriceTick tick1, IPriceTick tick2) {
		if (price1 == price2) {
			return;
		}

		double percentDiff = PriceTicks.getPercentDifference(price1, price2);
		if (percentDiff >= maxPercentDiff) {
			String percent = format.format(percentDiff);

			String message = "Maximum percent threshold (" + percent + "% > " + maxPercentDiff + "%) exceeded, tick1=" + tick1 + ", tick2=" + tick2;
			if (logFailure) {
				log.warn(message);
			}
			if (throwFailure) {
				throw new IllegalArgumentException(message);
			}
		}
	}

	@Override
	public boolean check(IPriceTick tick1, IPriceTick tick2) {
		checkDifference(tick1.getOpenPrice(), tick2.getOpenPrice(), tick1, tick2);
		checkDifference(tick1.getClosePrice(), tick2.getClosePrice(), tick1, tick2);
		checkDifference(tick1.getHighPrice(), tick2.getHighPrice(), tick1, tick2);
		checkDifference(tick1.getLowPrice(), tick2.getLowPrice(), tick1, tick2);
		return true;
	}

}
