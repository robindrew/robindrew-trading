package com.robindrew.trading.platform.account;

import com.robindrew.trading.platform.AbstractTradingService;
import com.robindrew.trading.provider.ITradingProvider;

public abstract class AbstractAccountService extends AbstractTradingService implements IAccountService {

	protected AbstractAccountService(ITradingProvider provider) {
		super(provider);
	}

}
