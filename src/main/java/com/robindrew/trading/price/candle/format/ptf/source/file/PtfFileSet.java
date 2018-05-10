package com.robindrew.trading.price.candle.format.ptf.source.file;

import static com.robindrew.trading.price.candle.format.ptf.PtfFormat.FILENAME_FILTER;
import static com.robindrew.trading.price.candle.format.ptf.source.file.PtfFileManager.getDirectory;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.robindrew.common.io.Files;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.filter.PriceCandleDateTimeFilter;
import com.robindrew.trading.price.candle.format.ptf.PtfFormat;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSource;
import com.robindrew.trading.price.candle.format.ptf.source.PtfSources;
import com.robindrew.trading.price.candle.format.ptf.source.PtfSourcesStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleFilteredStreamSource;
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
	public SortedSet<IPtfFile> getSources() {
		SortedSet<IPtfFile> files = new TreeSet<>();
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
	public IPtfFile getSource(LocalDate day, boolean create) {
		String filename = PtfFormat.getFilename(day);

		for (File directory : directorySet) {
			File file = new File(directory, filename);
			if (file.exists()) {
				return new PtfFile(file, day);
			}
		}

		for (ITradeDataProvider provider : providers) {
			File directory = getDirectory(provider, instrument, rootDirectory);
			File file = new File(directory, filename);
			return new PtfFile(file, day);
		}

		throw new IllegalStateException("Should never reach this line!");
	}

	@Override
	public IPtfFile getSource(LocalDate day) {
		String filename = PtfFormat.getFilename(day);

		for (File directory : directorySet) {
			File file = new File(directory, filename);
			if (file.exists()) {
				return new PtfFile(file, day);
			}
		}
		throw new IllegalArgumentException("File not found for instrument: " + getInstrument() + ", month: " + day);
	}

	@Override
	public Set<IPtfFile> getSources(LocalDateTime from, LocalDateTime to) {
		return PtfSources.filterSources(getSources(), from, to);
	}

	@Override
	public IPriceCandleStreamSource asStreamSource(LocalDateTime from, LocalDateTime to) {
		Set<? extends IPtfSource> sources = getSources(from, to);
		IPriceCandleStreamSource stream = new PtfSourcesStreamSource(sources);
		return new PriceCandleFilteredStreamSource(stream, new PriceCandleDateTimeFilter(from, to));
	}

	@Override
	public SortedSet<LocalDate> getDays() {
		TreeSet<LocalDate> months = new TreeSet<>();
		for (IPtfFile file : getSources()) {
			months.add(file.getDay());
		}
		return months;
	}

}
