package com.robindrew.trading.price.candle.format.ptf.source.file;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.robindrew.common.io.Files;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instrument;
import com.robindrew.trading.InstrumentType;
import com.robindrew.trading.price.candle.format.IPriceFormat;
import com.robindrew.trading.price.candle.format.PriceFormat;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSourceProviderManager;
import com.robindrew.trading.provider.ITradingProvider;
import com.robindrew.trading.provider.TradingProvider;

public class PtfFileManager implements IPtfFileManager {

	private static final Logger log = LoggerFactory.getLogger(PtfFileManager.class);

	public static File getDirectory(File directory, ITradingProvider provider, IInstrument instrument) {
		File providerDir = new File(directory, provider.name());
		File typeDir = new File(providerDir, instrument.getType().name());
		String name = instrument.getUnderlying(true).getName();
		return new File(typeDir, name);
	}

	public static Set<ITradingProvider> getProviders(File directory) {
		Set<ITradingProvider> providers = new TreeSet<>();
		for (File file : Files.listContents(directory)) {
			if (file.isDirectory()) {
				TradingProvider provider = TradingProvider.valueOf(file.getName());
				providers.add(provider);
			}
		}
		if (providers.isEmpty()) {
			throw new IllegalArgumentException("No providers found in directory: '" + directory + "'");
		}
		return ImmutableSet.copyOf(providers);
	}

	private final File rootDirectory;
	private final Set<IPtfFileProviderManager> providers;

	public PtfFileManager(File directory) {
		this.rootDirectory = Check.notNull("directory", directory);

		Set<IPtfFileProviderManager> providers = new LinkedHashSet<>();

		for (ITradingProvider provider : getProviders(directory)) {
			log.info("[Provider] {}", provider);
			providers.add(new PtfFileProviderManager(directory, provider));

			File providerDir = new File(rootDirectory, provider.name());
			for (File typeDir : Files.listFiles(providerDir, false)) {
				InstrumentType type = InstrumentType.valueOf(typeDir.getName());

				for (File instrumentDir : Files.listFiles(typeDir, false)) {
					IInstrument instrument = new Instrument(instrumentDir.getName(), type);

					log.info("[Instrument] {}", instrument);
				}
			}
		}

		this.providers = ImmutableSet.copyOf(providers);
	}

	@Override
	public IPriceFormat getFormat() {
		return PriceFormat.PTF;
	}

	@Override
	public File getRootDirectory() {
		return rootDirectory;
	}

	@Override
	public Set<? extends IPtfFileProviderManager> getProviders() {
		return providers;
	}

	@Override
	public IPtfSourceProviderManager getProvider(ITradingProvider provider) {
		for (IPtfFileProviderManager manager : providers) {
			if (manager.getProvider().equals(provider)) {
				return manager;
			}
		}
		throw new IllegalArgumentException("provider not available: " + provider);
	}

}
