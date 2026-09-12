package com.robindrew.trading.util.lang;

import java.util.List;
import java.util.Random;

/**
 * A seeded source of random values, with inclusive bounds.
 */
public class RandomElement {

    private final Random random;

    public RandomElement() {
        this.random = new Random();
    }

    public RandomElement(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Returns a random int between from (inclusive) and to (inclusive).
     */
    public int nextInt(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("from=" + from + " is greater than to=" + to);
        }
        if (from == to) {
            return from;
        }
        return from + random.nextInt(to - from + 1);
    }

    /**
     * Returns a random long between from (inclusive) and to (inclusive).
     */
    public long nextLong(long from, long to) {
        if (from > to) {
            throw new IllegalArgumentException("from=" + from + " is greater than to=" + to);
        }
        if (from == to) {
            return from;
        }
        return from + Math.floorMod(random.nextLong(), to - from + 1);
    }

    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    public double nextDouble() {
        return random.nextDouble();
    }

    /**
     * Returns a random element from the given list.
     */
    public <E> E nextElement(List<E> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("list is empty");
        }
        return list.get(random.nextInt(list.size()));
    }
}
