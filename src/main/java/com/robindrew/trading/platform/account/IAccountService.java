package com.robindrew.trading.platform.account;

import com.robindrew.trading.trade.cash.ICash;

public interface IAccountService {

	String getAccountId();

	ICash getBalance();

}
