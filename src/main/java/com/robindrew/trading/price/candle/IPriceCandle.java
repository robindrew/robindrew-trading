package com.robindrew.trading.price.candle;

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

	int getDecimalPlaces();

	long getOpenTime();

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