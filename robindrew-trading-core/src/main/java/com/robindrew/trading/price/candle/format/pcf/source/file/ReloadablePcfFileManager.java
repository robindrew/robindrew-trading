package com.robindrew.trading.price.candle.format.pcf.source.file;

import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.format.IPriceFormat;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSourceProviderManager;
import com.robindrew.trading.provider.ITradingProvider;
import java.io.File;
import java.util.Set;

/**
 * Wraps an immutable {@link PcfFileManager} behind a volatile reference, so that a fresh,
 * fully-built snapshot can be swapped in atomically once the underlying directory changes
 * (eg. after a PTF-to-PCF conversion writes new files). Readers always see a single, consistent,
 * immutable snapshot - never a partially rebuilt one.
 */
public class ReloadablePcfFileManager implements IPcfFileManager {

    private final File rootDirectory;
    private volatile PcfFileManager delegate;

    public ReloadablePcfFileManager(File directory) {
        this.rootDirectory = Check.notNull("directory", directory);
        this.delegate = new PcfFileManager(directory);
    }

    /**
     * Rebuilds the underlying manager from disk and atomically swaps it in.
     */
    public void reload() {
        this.delegate = new PcfFileManager(rootDirectory);
    }

    @Override
    public IPriceFormat getFormat() {
        return delegate.getFormat();
    }

    @Override
    public File getRootDirectory() {
        return delegate.getRootDirectory();
    }

    @Override
    public Set<? extends IPcfFileProviderManager> getProviders() {
        return delegate.getProviders();
    }

    @Override
    public IPcfSourceProviderManager getProvider(ITradingProvider provider) {
        return delegate.getProvider(provider);
    }
}
