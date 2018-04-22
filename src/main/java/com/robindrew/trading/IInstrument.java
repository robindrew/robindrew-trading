package com.robindrew.trading;

import com.robindrew.trading.price.precision.IPricePrecision;

public interface IInstrument extends Comparable<IInstrument> {

	String getName();

	InstrumentType getType();

	IInstrument getUnderlying();

	IInstrument getUnderlying(boolean recursive);

	boolean matches(IInstrument instrument);

	IPricePrecision getPrecision();
}
