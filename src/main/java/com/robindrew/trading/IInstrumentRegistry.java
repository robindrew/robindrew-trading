package com.robindrew.trading;

import com.google.common.base.Optional;

public interface IInstrumentRegistry {

	<I extends IInstrument> void register(Class<I> type);

	<I extends IInstrument> Optional<I> get(IInstrument instrument, Class<I> type);

}
