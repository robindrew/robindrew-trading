package com.robindrew.trading.price.tick.merger;

import java.util.Collection;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandle;

public class PriceTickMerger {

	public IPriceCandle merge(IPriceCandle candle1, IPriceCandle candle2) {
		if (candle1.getDecimalPlaces() != candle2.getDecimalPlaces()) {
			throw new IllegalArgumentException("Unable to merge candles with different precision");
		}

		long openTime;
		int openPrice;
		if (candle1.getOpenTime() <= candle2.getOpenTime()) {
			openTime = candle1.getOpenTime();
			openPrice = candle1.getOpenPrice();
		} else {
			openTime = candle2.getOpenTime();
			openPrice = candle2.getOpenPrice();
		}

		long closeTime;
		int closePrice;
		if (candle1.getCloseTime() >= candle2.getCloseTime()) {
			closeTime = candle1.getCloseTime();
			closePrice = candle1.getClosePrice();
		} else {
			closeTime = candle2.getCloseTime();
			closePrice = candle2.getClosePrice();
		}

		int highPrice = Math.max(candle1.getHighPrice(), candle2.getHighPrice());
		int lowPrice = Math.min(candle1.getLowPrice(), candle2.getLowPrice());

		PriceCandle merged = new PriceCandle(openPrice, highPrice, lowPrice, closePrice, openTime, closeTime, candle1.getDecimalPlaces());
		return merged;
	}

	public IPriceCandle merge(Collection<? extends IPriceCandle> candles) {
		if (candles.isEmpty()) {
			throw new IllegalArgumentException("candles is empty");
		}

		int decimalPlaces = 0;

		long openTime = 0;
		long closeTime = 0;

		int openPrice = 0;
		int closePrice = 0;
		int highPrice = 0;
		int lowPrice = 0;

		boolean first = true;
		for (IPriceCandle candle : candles) {

			// First candle
			if (first) {
				first = false;

				decimalPlaces = candle.getDecimalPlaces();

				openTime = candle.getOpenTime();
				closeTime = candle.getCloseTime();

				openPrice = candle.getOpenPrice();
				closePrice = candle.getClosePrice();
				highPrice = candle.getHighPrice();
				lowPrice = candle.getLowPrice();
			}

			// Remaining candles ...
			else {
				if (decimalPlaces != candle.getDecimalPlaces()) {
					throw new IllegalArgumentException("Unable to merge candles with different precision");
				}
				if (closeTime > candle.getOpenTime()) {
					throw new IllegalArgumentException("Unable to merge candles which are not sorted by time");
				}

				// Candles are always in order
				closeTime = candle.getCloseTime();

				closePrice = candle.getClosePrice();
				highPrice = Math.max(highPrice, candle.getHighPrice());
				lowPrice = Math.min(lowPrice, candle.getLowPrice());
			}
		}

		return new PriceCandle(openPrice, highPrice, lowPrice, closePrice, openTime, closeTime, decimalPlaces);
	}

}
