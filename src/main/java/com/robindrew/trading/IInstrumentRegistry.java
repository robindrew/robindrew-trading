package com.robindrew.trading;

public interface IInstrumentRegistry {

	<I extends IInstrument> void register(Class<I> type);

	<I extends IInstrument> I get(IInstrument instrument, Class<I> type);

}
