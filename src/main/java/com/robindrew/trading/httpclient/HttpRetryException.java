package com.robindrew.trading.httpclient;

public class HttpRetryException extends HttpClientException {

	private static final long serialVersionUID = -6664533260758734613L;

	public HttpRetryException(String message) {
		super(message);
	}

}
