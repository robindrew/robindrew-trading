package com.robindrew.trading.backtest.platform;

import com.robindrew.trading.backtest.IBacktestInstrument;
import com.robindrew.trading.backtest.platform.account.BacktestAccountService;
import com.robindrew.trading.backtest.platform.history.BacktestHistoryService;
import com.robindrew.trading.backtest.platform.position.BacktestPositionService;
import com.robindrew.trading.backtest.platform.streaming.IBacktestStreamingService;
import com.robindrew.trading.platform.ITradingPlatform;

public interface IBacktestTradingPlatform extends ITradingPlatform<IBacktestInstrument> {

    BacktestHistoryService getHistoryService();

    IBacktestStreamingService getStreamingService();

    BacktestPositionService getPositionService();

    BacktestAccountService getAccountService();
}
