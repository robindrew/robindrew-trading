package com.robindrew.trading.price.tick.format.ptf.source.file;

import static com.robindrew.trading.price.tick.format.ptf.PtfFormat.FILENAME_FILTER;
import static com.robindrew.trading.price.tick.format.ptf.source.file.PtfFileManager.getDirectory;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import com.robindrew.common.io.Files;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.tick.filter.PriceTickDateTimeFilter;
import com.robindrew.trading.price.tick.format.ptf.PtfFormat;
import com.robindrew.trading.price.tick.format.ptf.source.IPtfSource;
import com.robindrew.trading.price.tick.format.ptf.source.PtfSources;
import com.robindrew.trading.price.tick.format.ptf.source.PtfSourcesStreamSource;
import com.robindrew.trading.price.tick.io.stream.source.IPriceTickStreamSource;
import com.robindrew.trading.price.tick.io.stream.source.PriceTickFilteredStreamSource;
import com.robindrew.trading.provider.ITradeDataProvider;

public class PtfFileSet implements IPtfFileSet {

	private final IInstrument instrument;
	private final File rootDirectory;
	private final Set<File> directorySet = new LinkedHashSet<>();
	private final Set<ITradeDataProvider> providers;

	public PtfFileSet(IInstrument instrument, File rootDirectory, Set<ITradeDataProvider> providers) {
		this.instrument = Check.notNull("instrument", instrument);
		this.rootDirectory = Check.existsDirectory("rootDirectory", rootDirectory);
		this.providers = Check.notEmpty("providers", providers);

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
	public Set<IPtfFile> getSources() {
		Set<IPtfFile> files = new TreeSet<>();
		for (File directory : directorySet) {
			if (directory.exists()) {
				for (File file : Files.listContents(directory, FILENAME_FILTER)) {
					IPtfFile pcf = new PtfFile(file);
					files.add(pcf);
				}
			}
		}
		return files;
	}

	@Override
	public IPtfFile getSource(LocalDate month, boolean create) {
		String filename = PtfFormat.getFilename(month);

		for (File directory : directorySet) {
			File file = new File(directory, filename);
			if (file.exists()) {
				return new PtfFile(file, month);
			}
		}

		for (ITradeDataProvider provider : providers) {
			File directory = getDirectory(provider, instrument, rootDirectory);
			File file = new File(directory, filename);
			return new PtfFile(file, month);
		}

		throw new IllegalStateException("Should never reach this line!");
	}

	@Override
	public IPtfFile getSource(LocalDate month) {
		String filename = PtfFormat.getFilename(month);

		for (File directory : directorySet) {
			File file = new File(directory, filename);
			if (file.exists()) {
				return new PtfFile(file, month);
			}
		}
		throw new IllegalArgumentException("File not found for instrument: " + getInstrument() + ", month: " + month);
	}

	@Override
	public Set<IPtfFile> getSources(LocalDateTime from, LocalDateTime to) {
		return PtfSources.filterSources(getSources(), from, to);
	}

	@Override
	public IPriceTickStreamSource asStreamSource(LocalDateTime from, LocalDateTime to) {
		Set<? extends IPtfSource> sources = getSources(from, to);
		IPriceTickStreamSource stream = new PtfSourcesStreamSource(sources);
		return new PriceTickFilteredStreamSource(stream, new PriceTickDateTimeFilter(from, to));
	}

}
