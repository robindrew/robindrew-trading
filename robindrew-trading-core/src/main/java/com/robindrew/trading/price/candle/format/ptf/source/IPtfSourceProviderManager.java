package com.robindrew.trading.price.candle.format.ptf.source;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.IPriceFormat;
import com.robindrew.trading.provider.ITradingProvider;
import java.util.Set;

public interface IPtfSourceProviderManager {

    IPriceFormat getFormat();

    ITradingProvider getProvider();

    Set<IInstrument> getInstruments();

    IPtfSourceSet getSourceSet(IInstrument instrument);

    IInstrument getInstrument(String name);
}
