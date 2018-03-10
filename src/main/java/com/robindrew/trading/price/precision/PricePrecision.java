package com.robindrew.trading.price.precision;

import java.math.BigDecimal;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.format.pcf.FloatingPoint;

public class PricePrecision implements IPricePrecision {

	private final int decimalPlaces;
	private final int minPrice;
	private final int maxPrice;

	public PricePrecision(int decimalPlaces, int minPrice, int maxPrice) {
		this.decimalPlaces = decimalPlaces;
		this.minPrice = Check.min("minPrice", minPrice, 1);
		this.maxPrice = Check.min("maxPrice", maxPrice, minPrice + 1);
	}

	@Override
	public int getDecimalPlaces() {
		return decimalPlaces;
	}

	@Override
	public int getMinPrice() {
		return minPrice;
	}

	@Override
	public int getMaxPrice() {
		return maxPrice;
	}

	@Override
	public int toBigInt(BigDecimal price) {
		return FloatingPoint.toBigInt(price, decimalPlaces);
	}

	@Override
	public BigDecimal toBigDecimal(int price) {
		return FloatingPoint.toBigDecimal(price, decimalPlaces);
	}

}
