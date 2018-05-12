package com.robindrew.trading.platform;

import com.robindrew.common.util.Check;
import com.robindrew.trading.provider.ITradingProvider;

public abstract class AbstractTradingService implements ITradingService {

	private final ITradingProvider provider;

	protected AbstractTradingService(ITradingProvider provider) {
		this.provider = Check.notNull("provider", provider);
	}

	@Override
	public ITradingProvider getProvider() {
		return provider;
	}

}
