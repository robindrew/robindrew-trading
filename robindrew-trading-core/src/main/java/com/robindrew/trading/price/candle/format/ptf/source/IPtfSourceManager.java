package com.robindrew.trading.price.candle.format.ptf.source;

import com.robindrew.trading.price.candle.format.IPriceFormat;
import com.robindrew.trading.provider.ITradingProvider;
import java.util.Set;

public interface IPtfSourceManager {

    IPriceFormat getFormat();

    Set<? extends IPtfSourceProviderManager> getProviders();

    IPtfSourceProviderManager getProvider(ITradingProvider provider);
}
