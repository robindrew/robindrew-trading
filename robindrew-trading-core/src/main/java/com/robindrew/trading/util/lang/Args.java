package com.robindrew.trading.util.lang;

import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * Command line arguments, addressable by flag.
 * <p>
 * A flag is any argument followed by its value, for example "-f /tmp/in -t /tmp/out".
 */
public class Args {

    private final List<String> args;

    public Args(String... args) {
        this.args = ImmutableList.copyOf(args);
    }

    public List<String> getArgs() {
        return args;
    }

    /**
     * Returns the value for the given flag, failing if it is not present.
     */
    public String get(String flag) {
        String value = getOrNull(flag);
        if (value == null) {
            throw new IllegalArgumentException("Missing argument: '" + flag + "'");
        }
        return value;
    }

    /**
     * Returns the value for the given flag, or the given default if it is not present.
     */
    public String get(String flag, String defaultValue) {
        String value = getOrNull(flag);
        return value == null ? defaultValue : value;
    }

    /**
     * Returns true if the given flag is present.
     */
    public boolean contains(String flag) {
        return args.contains(flag);
    }

    private String getOrNull(String flag) {
        int index = args.indexOf(flag);
        if (index == -1 || index + 1 == args.size()) {
            return null;
        }
        return args.get(index + 1);
    }

    @Override
    public String toString() {
        return args.toString();
    }
}
