package com.robindrew.trading.provider;

import com.robindrew.trading.IInstrument;

public interface ITradeDataProviderInstrument extends IInstrument {

	ITradingProvider getProvider();

}
