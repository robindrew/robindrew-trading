package com.robindrew.trading.backtest;

import com.robindrew.common.util.Check;
import com.robindrew.trading.platform.streaming.InstrumentPriceStream;

public class BacktestInstrumentPriceStream extends InstrumentPriceStream {

	private final BacktestInstrumentPriceStreamListener listener;

	public BacktestInstrumentPriceStream(BacktestInstrumentPriceStreamListener listener) {
		super(listener.getInstrument(), listener.getLatestPrice());
		this.listener = Check.notNull("listener", listener);
	}

	@Override
	public BacktestInstrumentPriceStreamListener getListener() {
		return listener;
	}

	@Override
	public void close() {
	}

}
