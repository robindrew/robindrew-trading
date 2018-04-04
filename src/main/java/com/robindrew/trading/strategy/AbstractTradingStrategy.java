package com.robindrew.trading.strategy;

import com.robindrew.common.util.Check;
import com.robindrew.trading.platform.ITradingPlatform;
import com.robindrew.trading.price.tick.IPriceTick;

public abstract class AbstractTradingStrategy implements ITradingStrategy {

	private final String name;
	private final ITradingPlatform platform;

	protected AbstractTradingStrategy(String name, ITradingPlatform platform) {
		this.name = Check.notEmpty("name", name);
		this.platform = Check.notNull("platform", platform);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ITradingPlatform getPlatform() {
		return platform;
	}

	@Override
	public void putNextTick(IPriceTick tick) {
		putNextCandle(tick);
	}

}
