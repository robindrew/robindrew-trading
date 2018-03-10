package com.robindrew.trading.provider;

public interface ITradeDataProviderSet extends Iterable<ITradeDataProvider> {

	ITradeDataProvider getPrimary();

}
