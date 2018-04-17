package com.robindrew.trading.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.text.Strings;
import com.robindrew.common.util.Check;

public class TransactionLogger extends AbstractTransactionLog {

	private final Logger logger;

	public TransactionLogger() {
		this(LoggerFactory.getLogger(TransactionLogger.class));
	}

	public TransactionLogger(Logger logger) {
		this.logger = Check.notNull("logger", logger);
	}

	@Override
	protected void log(Entry entry) {
		StringBuilder line = new StringBuilder();
		line.append(entry.getHeader());
		line.append('\n');
		line.append(Strings.json(entry.getContent(), true));
		logger.debug(line.toString());
	}

}
