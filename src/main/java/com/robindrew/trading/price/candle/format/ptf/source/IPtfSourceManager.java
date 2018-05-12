package com.robindrew.trading.price.candle.format.ptf.source;

import java.util.Set;

import com.robindrew.trading.price.candle.format.IPriceFormat;
import com.robindrew.trading.provider.ITradingProvider;

public interface IPtfSourceManager {

	IPriceFormat getFormat();

	Set<? extends IPtfSourceProviderManager> getProviders();

	IPtfSourceProviderManager getProvider(ITradingProvider provider);

}
