package com.robindrew.trading.price.candle.format.pcf.source.file;

import static com.robindrew.trading.price.candle.format.pcf.PcfFormat.FILENAME_FILTER;
import static com.robindrew.trading.price.candle.format.pcf.source.file.PcfFileManager.getDirectory;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import com.robindrew.common.io.Files;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.format.pcf.PcfFormat;
import com.robindrew.trading.price.candle.format.pcf.source.PcfSources;
import com.robindrew.trading.provider.ITradeDataProvider;
import com.robindrew.trading.provider.ITradeDataProviderSet;

public class PcfFileSet implements IPcfFileSet {

	private final IInstrument instrument;
	private final File rootDirectory;
	private final Set<File> directorySet = new LinkedHashSet<>();
	private final ITradeDataProviderSet providers;

	public PcfFileSet(IInstrument instrument, File rootDirectory, ITradeDataProviderSet providers) {
		this.instrument = Check.notNull("instrument", instrument);
		this.rootDirectory = Check.existsDirectory("rootDirectory", rootDirectory);
		this.providers = Check.notNull("providers", providers);

		for (ITradeDataProvider provider : providers) {
			File instrumentDir = getDirectory(provider, instrument, rootDirectory);
			directorySet.add(instrumentDir);
		}
	}

	@Override
	public IInstrument getInstrument() {
		return instrument;
	}

	@Override
	public Set<IPcfFile> getSources() {
		Set<IPcfFile> files = new TreeSet<>();
		for (File directory : directorySet) {
			if (directory.exists()) {
				for (File file : Files.listContents(directory, FILENAME_FILTER)) {
					IPcfFile pcf = new PcfFile(file);
					files.add(pcf);
				}
			}
		}
		return files;
	}

	@Override
	public IPcfFile getSource(LocalDate month, boolean create) {
		String filename = PcfFormat.getFilename(month);

		for (File directory : directorySet) {
			File file = new File(directory, filename);
			if (file.exists()) {
				return new PcfFile(file, month);
			}
		}

		File directory = getDirectory(providers.getPrimary(), instrument, rootDirectory);
		File file = new File(directory, filename);
		return new PcfFile(file, month);
	}

	@Override
	public IPcfFile getSource(LocalDate month) {
		String filename = PcfFormat.getFilename(month);

		for (File directory : directorySet) {
			File file = new File(directory, filename);
			if (file.exists()) {
				return new PcfFile(file, month);
			}
		}
		throw new IllegalArgumentException("File not found for instrument: " + getInstrument() + ", month: " + month);
	}

	@Override
	public Set<IPcfFile> getSources(LocalDateTime from, LocalDateTime to) {
		return PcfSources.filterSources(getSources(), from, to);
	}

}
