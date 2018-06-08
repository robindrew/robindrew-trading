package com.robindrew.trading.provider;

import com.robindrew.trading.IInstrument;

public interface ITradingProviderInstrument extends IInstrument {

	ITradingProvider getProvider();

}
