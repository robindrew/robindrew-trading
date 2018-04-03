package com.robindrew.trading.price.tick.format.ptf.source;

import java.util.Set;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.provider.ITradeDataProvider;

public interface IPtfSourceManager {

	Set<IInstrument> getInstruments();

	Set<IInstrument> getInstruments(ITradeDataProvider provider);

	Set<ITradeDataProvider> getProviders();

	IPtfSourceSet getSourceSet(IInstrument instrument);

	IPtfSourceSet getSourceSet(IInstrument instrument, ITradeDataProvider provider);

}
