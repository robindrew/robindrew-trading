package com.robindrew.trading.price.close.calc;

import com.robindrew.trading.price.close.IClosePrice;

public interface IClosePriceStreamCalc {

	int getMinPrices();

	int getMaxPrices();

	void next(IClosePrice price, int index);

}
