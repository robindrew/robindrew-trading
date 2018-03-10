package com.robindrew.trading;

public interface IInstrument extends Comparable<IInstrument> {

	String getName();

	InstrumentType getType();

	IInstrument getUnderlying();

	IInstrument getUnderlying(boolean recursive);
}
