package com.robindrew.trading.strategy;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.ITradingPlatform;

public abstract class AbstractTradingStrategy<I extends IInstrument> implements ITradingStrategy<I> {

	private final String name;
	private final ITradingPlatform<I> platform;
	private final IInstrument instrument;

	protected AbstractTradingStrategy(String name, ITradingPlatform<I> platform, IInstrument instrument) {
		this.name = Check.notEmpty("name", name);
		this.platform = Check.notNull("platform", platform);
		this.instrument = Check.notNull("instrument", instrument);
	}

	@Override
	public final IInstrument getInstrument() {
		return instrument;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public final ITradingPlatform<I> getPlatform() {
		return platform;
	}

}
