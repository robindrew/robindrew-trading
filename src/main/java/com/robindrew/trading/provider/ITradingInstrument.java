package com.robindrew.trading.provider;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.precision.IPricePrecision;

public interface ITradingInstrument extends IInstrument {

	ITradingProvider getProvider();

	IPricePrecision getPrecision();

}
