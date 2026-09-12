package com.robindrew.trading.price.candle.format.pcf.source;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.IPriceFormat;
import com.robindrew.trading.provider.ITradingProvider;
import java.util.Set;

public interface IPcfSourceProviderManager {

    IPriceFormat getFormat();

    ITradingProvider getProvider();

    Set<IInstrument> getInstruments();

    IPcfSourceSet getSourceSet(IInstrument instrument);

    IInstrument getInstrument(String name);
}
