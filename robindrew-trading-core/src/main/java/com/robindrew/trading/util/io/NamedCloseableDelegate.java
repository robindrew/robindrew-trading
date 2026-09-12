package com.robindrew.trading.util.io;

/**
 * Base class for a {@link INamedCloseable} that delegates its name and closure to another.
 */
public class NamedCloseableDelegate<C extends INamedCloseable> implements INamedCloseable {

    private final C delegate;

    public NamedCloseableDelegate(C delegate) {
        if (delegate == null) {
            throw new NullPointerException("delegate");
        }
        this.delegate = delegate;
    }

    public C getDelegate() {
        return delegate;
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public String toString() {
        return delegate.toString();
    }
}
