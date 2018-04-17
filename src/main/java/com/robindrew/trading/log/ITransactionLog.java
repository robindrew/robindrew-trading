package com.robindrew.trading.log;

public interface ITransactionLog {

	void log(Object content);

	void log(String header, Object content);
}
