package com.robindrew.trading.backtest.platform;

import com.robindrew.common.util.Check;
import com.robindrew.trading.backtest.IBacktestInstrument;
import com.robindrew.trading.backtest.platform.account.BacktestAccountService;
import com.robindrew.trading.backtest.platform.history.BacktestHistoryService;
import com.robindrew.trading.backtest.platform.position.BacktestPositionService;
import com.robindrew.trading.backtest.platform.streaming.BacktestStreamingService;
import com.robindrew.trading.backtest.platform.streaming.IBacktestStreamingService;
import com.robindrew.trading.platform.TradingPlatform;

public class BacktestTradingPlatform extends TradingPlatform<IBacktestInstrument> implements IBacktestTradingPlatform {

    private final BacktestAccountService account;
    private final BacktestHistoryService history;
    private final BacktestStreamingService streaming;
    private final BacktestPositionService position;

    public BacktestTradingPlatform(
            BacktestAccountService account,
            BacktestHistoryService history,
            BacktestStreamingService streaming,
            BacktestPositionService position) {
        this.account = Check.notNull("account", account);
        this.history = Check.notNull("history", history);
        this.streaming = Check.notNull("streaming", streaming);
        this.position = Check.notNull("position", position);
    }

    @Override
    public BacktestHistoryService getHistoryService() {
        return history;
    }

    @Override
    public IBacktestStreamingService getStreamingService() {
        return streaming;
    }

    @Override
    public BacktestPositionService getPositionService() {
        return position;
    }

    @Override
    public BacktestAccountService getAccountService() {
        return account;
    }
}
