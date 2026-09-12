package com.robindrew.trading.util.io;

/**
 * A named, closeable resource.
 * <p>
 * Narrows {@link AutoCloseable#close()} so that implementations may be used in try-with-resources
 * blocks without forcing callers to handle a checked exception.
 */
public interface INamedCloseable extends AutoCloseable {

    /**
     * Returns the name of this resource.
     * @return the name of this resource.
     */
    default String getName() {
        return getClass().getSimpleName();
    }

    @Override
    default void close() {
        // Nothing to close by default.
    }
}
