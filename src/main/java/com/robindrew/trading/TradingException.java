package com.robindrew.trading;

public class TradingException extends RuntimeException {

	private static final long serialVersionUID = -4485503621528227203L;

	public TradingException(String message) {
		super(message);
	}

	public TradingException(String message, Throwable cause) {
		super(message, cause);
	}

	public TradingException(Throwable cause) {
		super(cause);
	}

}
