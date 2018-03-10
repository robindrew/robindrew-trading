package com.robindrew.trading.provider;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class TradeDataProviderSet implements ITradeDataProviderSet {

	public static TradeDataProviderSet of(ITradeDataProvider provider) {
		return new TradeDataProviderSet(newArrayList(provider));
	}

	public static TradeDataProviderSet of(ITradeDataProvider... providers) {
		return new TradeDataProviderSet(newArrayList(providers));
	}

	public static TradeDataProviderSet defaultProviders() {
		return of(TradeDataProvider.values());
	}

	private final ITradeDataProvider primary;
	private final Set<ITradeDataProvider> providers;

	public TradeDataProviderSet(Collection<? extends ITradeDataProvider> providers) {
		this.providers = ImmutableSet.copyOf(providers);
		this.primary = providers.iterator().next();
	}

	@Override
	public Iterator<ITradeDataProvider> iterator() {
		return providers.iterator();
	}

	@Override
	public ITradeDataProvider getPrimary() {
		return primary;
	}

}
