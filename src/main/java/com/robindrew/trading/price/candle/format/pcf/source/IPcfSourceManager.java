package com.robindrew.trading.price.candle.format.pcf.source;

import java.util.Set;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.provider.ITradeDataProvider;
import com.robindrew.trading.provider.ITradeDataProviderSet;

public interface IPcfSourceManager {

	Set<IInstrument> getInstruments();

	ITradeDataProviderSet getProviderSet();

	IPcfSourceSet getSourceSet(IInstrument instrument);

	IPcfSourceSet getSourceSet(IInstrument instrument, ITradeDataProvider provider);
	
}
