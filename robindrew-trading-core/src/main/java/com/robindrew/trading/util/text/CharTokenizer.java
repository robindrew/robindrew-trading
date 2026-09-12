package com.robindrew.trading.util.text;

/**
 * Splits text into tokens on a set of {@link CharDelimiters}.
 */
public class CharTokenizer {

    private final String text;
    private final CharDelimiters delimiters;

    private int index = 0;

    public CharTokenizer(String text, CharDelimiters delimiters) {
        if (text == null) {
            throw new NullPointerException("text");
        }
        if (delimiters == null) {
            throw new NullPointerException("delimiters");
        }
        this.text = text;
        this.delimiters = delimiters;
    }

    /**
     * Returns true if there are more tokens to read.
     */
    public boolean hasNext() {
        return index < text.length();
    }

    /**
     * Returns the next token, failing if there is none.
     * @param includeDelimiters when false, delimiter tokens are skipped over.
     */
    public String next(boolean includeDelimiters) {
        String token = nextOrNull(includeDelimiters);
        if (token == null) {
            throw new IllegalStateException("No more tokens in text: '" + text + "'");
        }
        return token;
    }

    /**
     * Returns the next token, or null if there is none.
     * @param includeDelimiters when false, delimiter tokens are skipped over.
     */
    public String nextOrNull(boolean includeDelimiters) {
        if (!includeDelimiters) {
            while (index < text.length() && delimiters.isDelimiter(text.charAt(index))) {
                index++;
            }
        }
        if (index >= text.length()) {
            return null;
        }

        int start = index;
        boolean delimiter = delimiters.isDelimiter(text.charAt(index));
        while (index < text.length() && delimiters.isDelimiter(text.charAt(index)) == delimiter) {
            index++;
        }
        return text.substring(start, index);
    }

    @Override
    public String toString() {
        return text;
    }
}
