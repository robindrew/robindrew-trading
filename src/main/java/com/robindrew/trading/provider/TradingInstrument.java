package com.robindrew.trading.provider;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instrument;
import com.robindrew.trading.price.precision.IPricePrecision;

public abstract class TradingInstrument extends Instrument implements ITradingInstrument {

	private final IPricePrecision precision;

	public TradingInstrument(String name, IInstrument underlying, IPricePrecision precision) {
		super(name, underlying);
		this.precision = Check.notNull("precision", precision);
	}

	@Override
	public IPricePrecision getPrecision() {
		return precision;
	}

}
