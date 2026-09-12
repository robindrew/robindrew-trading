package com.robindrew.trading.util.text;

import java.util.BitSet;

/**
 * The set of characters that separate tokens for a {@link CharTokenizer}.
 */
public class CharDelimiters {

    private final BitSet delimiters = new BitSet();
    private boolean whitespace = false;

    /**
     * Treat any whitespace character as a delimiter.
     */
    public CharDelimiters whitespace() {
        this.whitespace = true;
        return this;
    }

    /**
     * Treat the given character as a delimiter.
     */
    public CharDelimiters character(char c) {
        delimiters.set(c);
        return this;
    }

    /**
     * Treat each of the given characters as a delimiter.
     */
    public CharDelimiters characters(CharSequence chars) {
        for (int i = 0; i < chars.length(); i++) {
            character(chars.charAt(i));
        }
        return this;
    }

    /**
     * Returns true if the given character is a delimiter.
     */
    public boolean isDelimiter(char c) {
        if (whitespace && Character.isWhitespace(c)) {
            return true;
        }
        return delimiters.get(c);
    }
}
