package com.robindrew.trading.strategy;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.ITradingPlatform;

public abstract class SingleInstrumentStrategy extends AbstractTradingStrategy {

	private IInstrument instrument;

	protected SingleInstrumentStrategy(String name, ITradingPlatform platform, IInstrument instrument) {
		super(name, platform);
		this.instrument = Check.notNull("instrument", instrument);
	}

	public IInstrument getInstrument() {
		return instrument;
	}

}
