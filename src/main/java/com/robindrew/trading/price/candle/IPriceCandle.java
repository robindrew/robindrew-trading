package com.robindrew.trading.price.candle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IPriceCandle {

	int getBidOpenPrice();

	int getBidHighPrice();

	int getBidLowPrice();

	int getBidClosePrice();

	int getAskOpenPrice();

	int getAskHighPrice();

	int getAskLowPrice();

	int getAskClosePrice();

	int getMidOpenPrice();

	int getMidHighPrice();

	int getMidLowPrice();

	int getMidClosePrice();

	BigDecimal getMidOpen();

	BigDecimal getMidHigh();

	BigDecimal getMidLow();

	BigDecimal getMidClose();

	int getDecimalPlaces();

	long getOpenTime();

	long getTickVolume();

	LocalDateTime getOpenDate();

	long getCloseTime();

	LocalDateTime getCloseDate();

	long getCloseMove();

	long getHighLowRange();

	long getOpenCloseRange();

	IPriceCandle mergeWith(IPriceCandle candle);

	boolean isTick();

	boolean hasClosedUp();

	boolean after(IPriceCandle candle);

	boolean before(IPriceCandle candle);

	boolean containsPrice(int price);

}