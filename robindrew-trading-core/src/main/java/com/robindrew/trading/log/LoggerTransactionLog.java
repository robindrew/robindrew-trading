package com.robindrew.trading.log;

import com.robindrew.common.util.Check;
import com.robindrew.trading.util.text.TradingStrings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTransactionLog extends AbstractTransactionLog {

    private final Logger logger;
    private final boolean formatted;

    public LoggerTransactionLog(boolean formatted) {
        this(formatted, LoggerFactory.getLogger(LoggerTransactionLog.class));
    }

    public LoggerTransactionLog(boolean formatted, Logger logger) {
        this.logger = Check.notNull("logger", logger);
        this.formatted = formatted;
    }

    @Override
    protected void log(Entry entry) {
        StringBuilder line = new StringBuilder();
        line.append(entry.getHeader());
        if (formatted) {
            line.append('\n');
        } else {
            line.append(" -> ");
        }
        line.append(TradingStrings.json(entry.getContent(), formatted));
        logger.info(line.toString());
    }
}
