package com.robindrew.trading.backtest.platform.account;

import com.robindrew.common.util.Check;
import com.robindrew.trading.platform.account.AbstractAccountService;
import com.robindrew.trading.provider.ITradingProvider;
import com.robindrew.trading.trade.currency.Currency;

public class BacktestAccountService extends AbstractAccountService {

    private final String accountId;
    private volatile Currency balance;

    public BacktestAccountService(ITradingProvider provider, String accountId, Currency balance) {
        super(provider);
        this.accountId = Check.notEmpty("accountId", accountId);
        this.balance = Check.notNull("balance", balance);
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public Currency getBalance() {
        return balance;
    }

    public void setBalance(Currency balance) {
        this.balance = Check.notNull("balance", balance);
    }
}
