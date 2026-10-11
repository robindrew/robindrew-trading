package com.robindrew.trading.dukascopy.download.client;

import java.time.LocalDate;

/**
 * A request for a single hour (UTC) of Dukascopy tick data, equivalent to a single datafeed URL.
 * @param symbol the symbol to download.
 * @param day the day (UTC).
 * @param hour the hour of the day (UTC), 0 to 23.
 */
public record DukascopyHourRequest(DukascopySymbol symbol, LocalDate day, int hour) {

    public String getUrl() {
        return DukascopyTickDownloader.getHourUrl(symbol.symbol(), day, hour);
    }
}
