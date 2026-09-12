package com.robindrew.trading.price.candle.format.pcf.source;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.IPriceFormat;
import com.robindrew.trading.provider.ITradingProvider;
import java.util.Set;

public interface IPcfSourceManager {

    IPriceFormat getFormat();

    Set<? extends IPcfSourceProviderManager> getProviders();

    IPcfSourceProviderManager getProvider(ITradingProvider provider);

    default boolean hasInstrument(ITradingProvider provider, IInstrument instrument) {
        for (IPcfSourceProviderManager providerManager : getProviders()) {
            if (providerManager.getProvider().equals(provider)) {
                return providerManager.getInstruments().contains(instrument);
            }
        }
        return false;
    }
}
