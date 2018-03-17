package com.robindrew.trading.backtest.analysis;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;

public abstract class AbstractBacktestAnalysis implements IBacktestAnalysis {

	private final IInstrument instrument;

	protected AbstractBacktestAnalysis(IInstrument instrument) {
		this.instrument = Check.notNull("instrument", instrument);;
	}

	@Override
	public IInstrument getInstrument() {
		return instrument;
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public void close() {
	}

}
