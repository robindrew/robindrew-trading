package com.robindrew.trading.httpclient;

import com.robindrew.trading.TradingException;

public class HttpClientException extends TradingException {

	private static final long serialVersionUID = 7064891651562654467L;

	public HttpClientException(String message) {
		super(message);
	}

	public HttpClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpClientException(Throwable cause) {
		super(cause);
	}

}
