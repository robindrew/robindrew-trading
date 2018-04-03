package com.robindrew.trading.price.candle;

import java.time.LocalDateTime;

import com.robindrew.trading.price.close.IClosePrice;
import com.robindrew.trading.price.decimal.IDecimal;

public interface IPriceCandle extends IClosePrice {

	int getOpenPrice();

	int getHighPrice();

	int getLowPrice();

	@Override
	int getClosePrice();

	IDecimal getOpen();

	IDecimal getHigh();

	IDecimal getLow();

	IDecimal getClose();

	int getDecimalPlaces();

	long getOpenTime();

	LocalDateTime getOpenDate();

	long getCloseTime();

	LocalDateTime getCloseDate();

	long getCloseAmount();

	long getHighLowRange();

	long getOpenCloseRange();

	IPriceCandle mergeWith(IPriceCandle candle);

	boolean isInstant();

	boolean hasClosedUp();

	double getMedian();

	double getTypical();

	double getWeighted();

	boolean after(IPriceCandle candle);

	boolean before(IPriceCandle candle);

	boolean containsPrice(int price);

}