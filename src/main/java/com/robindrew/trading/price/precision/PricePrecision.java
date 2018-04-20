package com.robindrew.trading.price.precision;

import java.math.BigDecimal;

import com.robindrew.trading.price.decimal.Decimals;

public class PricePrecision implements IPricePrecision {

	private final int decimalPlaces;

	public PricePrecision(int decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}

	@Override
	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	@Override
	public int toBigInt(BigDecimal price) {
		return Decimals.toBigInt(price, decimalPlaces);
	}

	@Override
	public BigDecimal toBigDecimal(int price) {
		return Decimals.toBigDecimal(price, decimalPlaces);
	}

}
