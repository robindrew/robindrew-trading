package com.robindrew.trading.platform;

import com.robindrew.trading.TradingException;

public class TradingPlatformException extends TradingException {

	private static final long serialVersionUID = -2483351416434414621L;

	public TradingPlatformException(String message) {
		super(message);
	}

	public TradingPlatformException(String message, Throwable cause) {
		super(message, cause);
	}

	public TradingPlatformException(Throwable cause) {
		super(cause);
	}

}
