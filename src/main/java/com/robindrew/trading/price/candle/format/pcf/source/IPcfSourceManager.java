package com.robindrew.trading.price.candle.format.pcf.source;

import java.util.Set;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.provider.ITradeDataProvider;

public interface IPcfSourceManager {

	Set<IInstrument> getInstruments();

	Set<IInstrument> getInstruments(ITradeDataProvider provider);

	Set<ITradeDataProvider> getProviders();

	IPcfSourceSet getSourceSet(IInstrument instrument);

	IPcfSourceSet getSourceSet(IInstrument instrument, ITradeDataProvider provider);

	IInstrument getInstrument(ITradeDataProvider provider, String name);

}
