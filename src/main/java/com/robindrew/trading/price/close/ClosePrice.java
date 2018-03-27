package com.robindrew.trading.price.close;

import com.robindrew.trading.price.decimal.Decimal;

public class ClosePrice extends Decimal implements IClosePrice {

	public ClosePrice(int price, int decimalPlaces) {
		super(price, decimalPlaces);
	}

	@Override
	public int getClosePrice() {
		return getValue();
	}

}
