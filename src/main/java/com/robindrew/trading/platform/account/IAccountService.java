package com.robindrew.trading.platform.account;

import com.robindrew.trading.platform.ITradingService;
import com.robindrew.trading.trade.balance.IMoney;

public interface IAccountService extends ITradingService {

	String getAccountId();

	IMoney getBalance();

}
