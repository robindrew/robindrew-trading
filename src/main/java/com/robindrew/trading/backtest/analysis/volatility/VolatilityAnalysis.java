package com.robindrew.trading.backtest.analysis.volatility;

import java.time.LocalDateTime;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.backtest.analysis.AbstractBacktestAnalysis;
import com.robindrew.trading.price.candle.IPriceCandle;

public class VolatilityAnalysis extends AbstractBacktestAnalysis {

	private final VolatilityReport report = new VolatilityReport();

	public VolatilityAnalysis(IInstrument instrument) {
		super(instrument);
	}

	@Override
	public void close() {
		report.logReport();
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {

		LocalDateTime date = candle.getOpenDate();
		report.add(date, candle);
	}

}
