package com.robindrew.trading.platform.account;

import com.robindrew.trading.platform.ITradingService;
import com.robindrew.trading.trade.cash.ICash;

public interface IAccountService extends ITradingService {

	String getAccountId();

	ICash getBalance();

}
