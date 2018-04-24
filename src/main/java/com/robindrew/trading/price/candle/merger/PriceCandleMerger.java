package com.robindrew.trading.price.candle.merger;

import java.util.Collection;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.MidPriceCandle;
import com.robindrew.trading.price.candle.PriceCandle;

public class PriceCandleMerger {

	public IPriceCandle merge(IPriceCandle candle1, IPriceCandle candle2) {
		if (candle1.getDecimalPlaces() != candle2.getDecimalPlaces()) {
			throw new IllegalArgumentException("Unable to merge candles with different precision");
		}

		int decimalPlaces = candle1.getDecimalPlaces();

		// Open
		final long openTime;
		final int bidOpenPrice;
		final int askOpenPrice;
		if (candle1.getOpenTime() <= candle2.getOpenTime()) {
			openTime = candle1.getOpenTime();
			bidOpenPrice = candle1.getBidOpenPrice();
			askOpenPrice = candle1.getAskOpenPrice();
		} else {
			openTime = candle2.getOpenTime();
			bidOpenPrice = candle2.getBidOpenPrice();
			askOpenPrice = candle2.getAskOpenPrice();
		}

		// Close
		final long closeTime;
		final int bidClosePrice;
		final int askClosePrice;
		if (candle1.getCloseTime() >= candle2.getCloseTime()) {
			closeTime = candle1.getCloseTime();
			bidClosePrice = candle1.getBidClosePrice();
			askClosePrice = candle1.getAskClosePrice();
		} else {
			closeTime = candle2.getCloseTime();
			bidClosePrice = candle2.getBidClosePrice();
			askClosePrice = candle2.getAskClosePrice();
		}

		// High
		int bidHighPrice = Math.max(candle1.getBidHighPrice(), candle2.getBidHighPrice());
		int askHighPrice = Math.max(candle1.getAskHighPrice(), candle2.getAskHighPrice());

		// Low
		int bidLowPrice = Math.min(candle1.getBidLowPrice(), candle2.getBidLowPrice());
		int askLowPrice = Math.min(candle1.getAskLowPrice(), candle2.getAskLowPrice());

		long tickVolume = candle1.getTickVolume() + candle2.getTickVolume();

		// Check if this is a mid price candle
		if (bidOpenPrice == askOpenPrice && bidClosePrice == askClosePrice && bidHighPrice == askHighPrice && bidLowPrice == askLowPrice) {
			return new MidPriceCandle(bidOpenPrice, bidHighPrice, bidLowPrice, bidClosePrice, openTime, closeTime, decimalPlaces, tickVolume);
		}

		// Full bid/ask price candle
		return new PriceCandle(bidOpenPrice, bidHighPrice, bidLowPrice, bidClosePrice, askOpenPrice, askHighPrice, askLowPrice, askClosePrice, openTime, closeTime, decimalPlaces, tickVolume);
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

		long tickVolume = 0;

		boolean first = true;
		for (IPriceCandle candle : candles) {

			// First candle
			if (first) {
				first = false;

				decimalPlaces = candle.getDecimalPlaces();

				openTime = candle.getOpenTime();
				closeTime = candle.getCloseTime();

				openPrice = candle.getMidOpenPrice();
				closePrice = candle.getMidClosePrice();
				highPrice = candle.getMidHighPrice();
				lowPrice = candle.getMidLowPrice();

				tickVolume = candle.getTickVolume();
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

				closePrice = candle.getMidClosePrice();
				highPrice = Math.max(highPrice, candle.getMidHighPrice());
				lowPrice = Math.min(lowPrice, candle.getMidLowPrice());

				tickVolume += candle.getTickVolume();
			}
		}

		return new MidPriceCandle(openPrice, highPrice, lowPrice, closePrice, openTime, closeTime, decimalPlaces, tickVolume);
	}

}
