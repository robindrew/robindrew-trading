package com.robindrew.trading.strategy;

import com.robindrew.common.util.Check;
import com.robindrew.trading.platform.ITradingPlatform;
import com.robindrew.trading.provider.ITradingInstrument;

public abstract class AbstractTradingStrategy<I extends ITradingInstrument> implements ITradingStrategy<I> {

	private final String name;
	private final ITradingPlatform<I> platform;
	private final I instrument;

	protected AbstractTradingStrategy(String name, ITradingPlatform<I> platform, I instrument) {
		this.name = Check.notEmpty("name", name);
		this.platform = Check.notNull("platform", platform);
		this.instrument = Check.notNull("instrument", instrument);
	}

	@Override
	public final I getInstrument() {
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
