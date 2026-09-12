package com.robindrew.trading;

import com.robindrew.trading.price.range.IPriceRange;
import java.util.Optional;

public interface IInstrument extends Comparable<IInstrument> {

    String getName();

    InstrumentType getType();

    IInstrument getUnderlying();

    IInstrument getUnderlying(boolean recursive);

    boolean matches(IInstrument instrument);

    Optional<IPriceRange> getRange();
}
