package com.robindrew.trading.dukascopy.download.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robindrew.trading.InstrumentType;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Downloads the symbols from the instrument feed used by the Dukascopy web widgets. The request is ignored (there is
 * only one instrument list), so null may be passed.
 */
public class DukascopySymbolDownloader extends DukascopyWebClientDownloader<Void, List<DukascopySymbol>> {

    public static final String INSTRUMENTS_URL =
            "https://freeserv.dukascopy.com/2.0/index.php?path=common%2Finstruments&json";
    public static final String INSTRUMENTS_REFERER = "https://freeserv.dukascopy.com/";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public DukascopySymbolDownloader(WebClient.Builder builder) {
        super(builder);
    }

    @Override
    public List<DukascopySymbol> download(Void request) {
        byte[] body = fetch(INSTRUMENTS_URL, INSTRUMENTS_REFERER);
        if (body == null) {
            throw new IllegalStateException("Dukascopy instrument list not available");
        }
        try {
            return parseSymbols(new String(body, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to parse Dukascopy instrument list", e);
        }
    }

    static List<DukascopySymbol> parseSymbols(String jsonp) throws IOException {
        // The response is JSONP: jsonp({...})
        int start = jsonp.indexOf('(');
        int end = jsonp.lastIndexOf(')');
        String json = start >= 0 && end > start ? jsonp.substring(start + 1, end) : jsonp;
        JsonNode root = MAPPER.readTree(json);

        Map<String, InstrumentType> nameToType = getTypesByName(root.path("groups"));

        List<DukascopySymbol> symbols = new ArrayList<>();
        Iterator<JsonNode> instruments = root.path("instruments").elements();
        while (instruments.hasNext()) {
            JsonNode instrument = instruments.next();
            String symbol = instrument.path("historical_filename").asText("");
            double pipValue = instrument.path("pipValue").asDouble(0);
            if (symbol.isEmpty() || pipValue <= 0) {
                continue;
            }
            String name = instrument.path("name").asText(symbol);
            InstrumentType type = nameToType.getOrDefault(name, guessType(name));
            symbols.add(new DukascopySymbol(
                    symbol,
                    name,
                    instrument.path("description").asText(""),
                    type,
                    getDecimalPlaces(pipValue),
                    instrument.path("history_start_tick").asLong(0)));
        }
        symbols.sort(Comparator.comparing(DukascopySymbol::type).thenComparing(DukascopySymbol::symbol));
        return symbols;
    }

    /**
     * Tick prices are integers with one more decimal place than the pip (e.g. EURUSD pip 0.0001 is 5 decimal
     * places, USDJPY pip 0.01 is 3 decimal places).
     */
    static int getDecimalPlaces(double pipValue) {
        int scale = BigDecimal.valueOf(pipValue).stripTrailingZeros().scale();
        return Math.max(0, Math.min(8, scale + 1));
    }

    private static Map<String, InstrumentType> getTypesByName(JsonNode groups) {
        Map<String, String> parents = new HashMap<>();
        groups.fields().forEachRemaining(entry -> {
            JsonNode parent = entry.getValue().get("parent");
            if (parent != null) {
                parents.put(entry.getKey(), parent.asText());
            }
        });

        Map<String, InstrumentType> nameToType = new HashMap<>();
        groups.fields().forEachRemaining(entry -> {
            String root = entry.getKey();
            while (parents.containsKey(root)) {
                root = parents.get(root);
            }
            InstrumentType type = getType(entry.getKey(), root);
            for (JsonNode name : entry.getValue().path("instruments")) {
                nameToType.put(name.asText(), type);
            }
        });
        return nameToType;
    }

    private static InstrumentType getType(String groupId, String rootGroupId) {
        if (groupId.equals("FX_METALS")) {
            return InstrumentType.COMMODITIES;
        }
        return switch (rootGroupId) {
            case "FX", "VCCY" -> InstrumentType.CURRENCIES;
            case "CMD" -> InstrumentType.COMMODITIES;
            case "IDX", "BND_CFD" -> InstrumentType.INDICES;
            default -> InstrumentType.SHARES;
        };
    }

    private static InstrumentType guessType(String name) {
        if (name.contains(".IDX")) {
            return InstrumentType.INDICES;
        }
        if (name.contains(".CMD")) {
            return InstrumentType.COMMODITIES;
        }
        if (name.matches("[A-Z]{3}/[A-Z]{3}")) {
            return InstrumentType.CURRENCIES;
        }
        return InstrumentType.SHARES;
    }
}
