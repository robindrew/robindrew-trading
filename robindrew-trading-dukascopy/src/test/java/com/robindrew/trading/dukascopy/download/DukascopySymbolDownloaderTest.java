package com.robindrew.trading.dukascopy.download;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.robindrew.trading.InstrumentType;
import com.robindrew.trading.dukascopy.download.client.DukascopySymbol;
import com.robindrew.trading.dukascopy.download.client.DukascopySymbolDownloader;
import java.util.List;
import org.junit.jupiter.api.Test;

class DukascopySymbolDownloaderTest {

    @Test
    void decimalPlacesFromPipValue() {
        assertEquals(5, DukascopySymbolDownloader.getDecimalPlaces(0.0001));
        assertEquals(3, DukascopySymbolDownloader.getDecimalPlaces(0.01));
        assertEquals(1, DukascopySymbolDownloader.getDecimalPlaces(1));
    }

    @Test
    void parseSymbols() throws Exception {
        String jsonp = "jsonp({\"instruments\":{"
                + "\"EUR/USD\":{\"name\":\"EUR/USD\",\"description\":\"Euro vs US Dollar\","
                + "\"historical_filename\":\"EURUSD\",\"pipValue\":0.0001,\"history_start_tick\":\"1000\"},"
                + "\"XAU/USD\":{\"name\":\"XAU/USD\",\"historical_filename\":\"XAUUSD\",\"pipValue\":0.01},"
                + "\"USA500.IDX/USD\":{\"name\":\"USA500.IDX/USD\",\"historical_filename\":\"USA500IDXUSD\",\"pipValue\":0.01}"
                + "},\"groups\":{"
                + "\"FX\":{\"id\":\"FX\"},"
                + "\"FX_MAJORS\":{\"id\":\"FX_MAJORS\",\"parent\":\"FX\",\"instruments\":[\"EUR/USD\"]},"
                + "\"FX_METALS\":{\"id\":\"FX_METALS\",\"parent\":\"FX\",\"instruments\":[\"XAU/USD\"]}"
                + "}})";

        List<DukascopySymbol> symbols = DukascopySymbolDownloader.parseSymbols(jsonp);

        assertEquals(
                List.of(
                        new DukascopySymbol(
                                "EURUSD", "EUR/USD", "Euro vs US Dollar", InstrumentType.CURRENCIES, 5, 1000),
                        new DukascopySymbol("USA500IDXUSD", "USA500.IDX/USD", "", InstrumentType.INDICES, 3, 0),
                        new DukascopySymbol("XAUUSD", "XAU/USD", "", InstrumentType.COMMODITIES, 3, 0)),
                symbols);
    }
}
