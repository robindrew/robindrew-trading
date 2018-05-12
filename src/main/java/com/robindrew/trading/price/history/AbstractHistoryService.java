package com.robindrew.trading.price.history;

import com.robindrew.trading.platform.AbstractTradingService;
import com.robindrew.trading.provider.ITradingProvider;

public abstract class AbstractHistoryService extends AbstractTradingService implements IHistoryService {

	protected AbstractHistoryService(ITradingProvider provider) {
		super(provider);
	}

}
