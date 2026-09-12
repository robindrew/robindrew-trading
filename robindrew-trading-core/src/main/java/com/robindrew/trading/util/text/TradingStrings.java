package com.robindrew.trading.util.text;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.text.DecimalFormat;
import java.util.Collection;

/**
 * Text formatting helpers for log and diagnostic output.
 */
public class TradingStrings {

    private static final String[] BYTE_UNITS = {"bytes", "KB", "MB", "GB", "TB", "PB"};

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectWriter PRETTY_WRITER = MAPPER.writerWithDefaultPrettyPrinter();

    /**
     * Returns the given number formatted with grouping separators, for example "1,234,567".
     */
    public static String number(long value) {
        return new DecimalFormat("#,##0").format(value);
    }

    /**
     * Returns the given number formatted with grouping separators, for example "1,234,567".
     */
    public static String number(double value) {
        return new DecimalFormat("#,##0.##").format(value);
    }

    /**
     * Returns the size of the given collection, formatted with grouping separators.
     */
    public static String number(Collection<?> collection) {
        return number(collection.size());
    }

    /**
     * Returns the given byte count in human readable form, for example "1.5 MB".
     */
    public static String bytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " " + BYTE_UNITS[0];
        }
        double value = bytes;
        int unit = 0;
        while (value >= 1024 && unit < BYTE_UNITS.length - 1) {
            value = value / 1024;
            unit++;
        }
        return new DecimalFormat("#,##0.##").format(value) + " " + BYTE_UNITS[unit];
    }

    /**
     * Returns the length of the given array in human readable form, for example "1.5 MB".
     */
    public static String bytes(byte[] bytes) {
        return bytes(bytes.length);
    }

    /**
     * Returns the given object serialized as JSON.
     * @param formatted when true, the JSON is pretty printed.
     */
    public static String json(Object object, boolean formatted) {
        if (object == null) {
            return "null";
        }
        try {
            return formatted ? PRETTY_WRITER.writeValueAsString(object) : MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Unable to serialize to JSON: " + object.getClass(), e);
        }
    }

    private TradingStrings() {}
}
