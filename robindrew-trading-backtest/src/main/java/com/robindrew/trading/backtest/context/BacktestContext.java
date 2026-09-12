package com.robindrew.trading.backtest.context;

import com.robindrew.trading.backtest.platform.IBacktestTradingPlatform;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceSet;

public record BacktestContext(IBacktestTradingPlatform platform, IPcfSourceSet sourceSet) {}
