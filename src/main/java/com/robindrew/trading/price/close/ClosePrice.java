package com.robindrew.trading.price.close;

public class ClosePrice implements IClosePrice {

	private final int price;

	public ClosePrice(int price) {
		if (price <= 0) {
			throw new IllegalArgumentException("price=" + price);
		}
		this.price = price;
	}

	@Override
	public int getClosePrice() {
		return price;
	}

}
