package com.robindrew.trading.price;

import com.robindrew.trading.TradingException;

public class PriceException extends TradingException {

	private static final long serialVersionUID = -5701026394190481547L;

	public PriceException(String message) {
		super(message);
	}

	public PriceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PriceException(Throwable cause) {
		super(cause);
	}

}
