package com.robindrew.trading.price.candle.format.pcf.source.file;

import static com.robindrew.trading.price.candle.format.pcf.PcfFormat.FILENAME_FILTER;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.robindrew.common.io.Files;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.filter.PriceCandleDateTimeFilter;
import com.robindrew.trading.price.candle.format.pcf.PcfFormat;
import com.robindrew.trading.price.candle.format.pcf.source.IPcfSource;
import com.robindrew.trading.price.candle.format.pcf.source.PcfSources;
import com.robindrew.trading.price.candle.format.pcf.source.PcfSourcesStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleFilteredStreamSource;
import com.robindrew.trading.provider.ITradingProvider;

public class PcfFileSet implements IPcfFileSet {

	private final File rootDirectory;
	private final ITradingProvider provider;
	private final IInstrument instrument;
	private final File providerDirectory;

	public PcfFileSet(File rootDirectory, ITradingProvider provider, IInstrument instrument) {
		this.rootDirectory = Check.existsDirectory("rootDirectory", rootDirectory);
		this.provider = Check.notEmpty("provider", provider);
		this.instrument = Check.notNull("instrument", instrument);
		this.providerDirectory = new File(rootDirectory, provider.name());

		if (!providerDirectory.exists()) {
			throw new IllegalStateException("Provider directory does not exist: '" + providerDirectory + "'");
		}
	}

	public File getRootDirectory() {
		return rootDirectory;
	}

	public ITradingProvider getProvider() {
		return provider;
	}

	@Override
	public IInstrument getInstrument() {
		return instrument;
	}

	@Override
	public Set<IPcfFile> getSources() {
		Set<IPcfFile> files = new TreeSet<>();
		for (File file : Files.listContents(providerDirectory, FILENAME_FILTER)) {
			IPcfFile pcf = new PcfFile(file);
			files.add(pcf);
		}
		return files;
	}

	@Override
	public IPcfFile getSource(LocalDate month, boolean create) {
		String filename = PcfFormat.getFilename(month);
		File file = new File(providerDirectory, filename);
		return new PcfFile(file, month);
	}

	@Override
	public IPcfFile getSource(LocalDate month) {
		String filename = PcfFormat.getFilename(month);
		File file = new File(providerDirectory, filename);
		if (file.exists()) {
			return new PcfFile(file, month);
		}
		throw new IllegalArgumentException("File not found for instrument: " + getInstrument() + ", month: " + month);
	}

	@Override
	public Set<IPcfFile> getSources(LocalDateTime from, LocalDateTime to) {
		return PcfSources.filterSources(getSources(), from, to);
	}

	@Override
	public IPriceCandleStreamSource asStreamSource(LocalDateTime from, LocalDateTime to) {
		Set<? extends IPcfSource> sources = getSources(from, to);
		IPriceCandleStreamSource stream = new PcfSourcesStreamSource(sources);
		return new PriceCandleFilteredStreamSource(stream, new PriceCandleDateTimeFilter(from, to));
	}

	@Override
	public SortedSet<LocalDate> getMonths() {
		TreeSet<LocalDate> months = new TreeSet<>();
		for (IPcfFile file : getSources()) {
			months.add(file.getMonth());
		}
		return months;
	}

}
