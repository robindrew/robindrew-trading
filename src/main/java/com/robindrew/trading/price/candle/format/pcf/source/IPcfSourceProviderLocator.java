package com.robindrew.trading.price.candle.format.pcf.source;

import java.util.Set;

import com.robindrew.trading.price.candle.format.IPriceFormat;
import com.robindrew.trading.provider.ITradingProvider;

public interface IPcfSourceProviderLocator {

	IPriceFormat getFormat();

	Set<? extends IPcfSourceProviderManager> getProviders();

	IPcfSourceProviderManager getProvider(ITradingProvider provider);

}
