package com.robindrew.trading.platform.account;

import com.robindrew.trading.platform.ITradingService;
import com.robindrew.trading.trade.currency.Currency;

public interface IAccountService extends ITradingService {

	String getAccountId();

	Currency getBalance();

}
