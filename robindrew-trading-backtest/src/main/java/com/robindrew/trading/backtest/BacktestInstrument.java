package com.robindrew.trading.backtest;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.provider.ITradingInstrument;
import com.robindrew.trading.provider.ITradingProvider;
import com.robindrew.trading.provider.TradingInstrument;
import com.robindrew.trading.provider.TradingProvider;

public class BacktestInstrument extends TradingInstrument implements IBacktestInstrument {

    public static BacktestInstrument of(ITradingInstrument instrument) {
        return new BacktestInstrument(instrument, instrument.getPrecision());
    }

    public static BacktestInstrument of(IInstrument instrument, IPricePrecision precision) {
        return new BacktestInstrument(instrument, precision);
    }

    private BacktestInstrument(IInstrument underlying, IPricePrecision precision) {
        super(underlying.getName(), underlying, precision);
    }

    public ITradingProvider getProvider() {
        return TradingProvider.BACKTEST;
    }
}
