package com.robindrew.trading.price.candle.format.ptf.source.file;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import com.robindrew.common.io.Files;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instrument;
import com.robindrew.trading.InstrumentType;
import com.robindrew.trading.price.candle.format.IPriceFormat;
import com.robindrew.trading.price.candle.format.PriceFormat;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSourceSet;
import com.robindrew.trading.provider.ITradingProvider;

public class PtfFileProviderManager implements IPtfFileProviderManager {

	public static File getDirectory(ITradingProvider provider, IInstrument instrument, File rootDirectory) {
		File providerDir = new File(rootDirectory, provider.name());
		File typeDir = new File(providerDir, instrument.getType().name());
		String name = instrument.getUnderlying(true).getName();
		return new File(typeDir, name);
	}

	private final File rootDirectory;
	private final ITradingProvider provider;

	public PtfFileProviderManager(File rootDirectory, ITradingProvider provider) {
		this.rootDirectory = Check.notNull("rootDirectory", rootDirectory);
		this.provider = Check.notNull("provider", provider);
	}

	@Override
	public IPriceFormat getFormat() {
		return PriceFormat.PTF;
	}

	@Override
	public ITradingProvider getProvider() {
		return provider;
	}

	@Override
	public File getRootDirectory() {
		return rootDirectory;
	}

	@Override
	public File getDirectory(IInstrument instrument) {
		return getDirectory(provider, instrument, rootDirectory);
	}

	@Override
	public IPtfSourceSet getSourceSet(IInstrument instrument) {
		return new PtfFileSet(rootDirectory, provider, instrument);
	}

	@Override
	public Set<IInstrument> getInstruments() {
		Set<IInstrument> set = new TreeSet<>();
		File providerDir = new File(rootDirectory, provider.name());
		if (providerDir.exists()) {
			for (File typeDir : Files.listFiles(providerDir, false)) {
				InstrumentType type = InstrumentType.valueOf(typeDir.getName());
				for (File instrumentDir : Files.listFiles(typeDir, false)) {
					IInstrument instrument = new Instrument(instrumentDir.getName(), type);
					set.add(instrument);
				}
			}
		}
		return set;
	}

	@Override
	public IInstrument getInstrument(String name) {
		for (IInstrument instrument : getInstruments()) {
			if (instrument.getName().equals(name)) {
				return instrument;
			}
			if (instrument.getUnderlying().getName().equals(name)) {
				return instrument;
			}
		}
		throw new IllegalArgumentException("instrument not found: '" + name + "'");
	}

}
