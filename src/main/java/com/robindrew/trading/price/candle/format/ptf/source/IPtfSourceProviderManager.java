package com.robindrew.trading.price.candle.format.ptf.source;

import java.util.Set;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.IPriceFormat;
import com.robindrew.trading.provider.ITradingProvider;

public interface IPtfSourceProviderManager {

	IPriceFormat getFormat();

	ITradingProvider getProvider();

	Set<IInstrument> getInstruments();

	IPtfSourceSet getSourceSet(IInstrument instrument);

	IInstrument getInstrument(String name);

}
