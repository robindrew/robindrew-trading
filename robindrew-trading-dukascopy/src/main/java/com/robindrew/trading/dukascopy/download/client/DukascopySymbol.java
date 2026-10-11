package com.robindrew.trading.dukascopy.download.client;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instrument;
import com.robindrew.trading.InstrumentType;

/**
 * A symbol available from the Dukascopy datafeed.
 * @param symbol the datafeed symbol used in download URLs (e.g. EURUSD, USA500IDXUSD).
 * @param name the display name (e.g. EUR/USD).
 * @param description the instrument description.
 * @param type the instrument type, derived from the Dukascopy group.
 * @param decimalPlaces the number of decimal places of the integer prices in the tick data.
 * @param historyStart the earliest tick available (epoch millis UTC), or 0 if unknown.
 */
public record DukascopySymbol(
        String symbol, String name, String description, InstrumentType type, int decimalPlaces, long historyStart) {

    public IInstrument toInstrument() {
        return new Instrument(symbol, type);
    }
}
