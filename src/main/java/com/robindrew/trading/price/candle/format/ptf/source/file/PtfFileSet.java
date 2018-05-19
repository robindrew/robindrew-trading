package com.robindrew.trading.price.candle.format.ptf.source.file;

import static com.robindrew.trading.price.candle.format.ptf.PtfFormat.FILENAME_FILTER;

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
import com.robindrew.trading.price.candle.format.ptf.PtfFormat;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSource;
import com.robindrew.trading.price.candle.format.ptf.source.PtfSources;
import com.robindrew.trading.price.candle.format.ptf.source.PtfSourcesStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.candle.io.stream.source.PriceCandleFilteredStreamSource;
import com.robindrew.trading.provider.ITradingProvider;

public class PtfFileSet implements IPtfFileSet {

	private final File directory;
	private final ITradingProvider provider;
	private final IInstrument instrument;

	public PtfFileSet(File directory, ITradingProvider provider, IInstrument instrument) {
		this.directory = Check.existsDirectory("directory", directory);
		this.provider = Check.notNull("provider", provider);
		this.instrument = Check.notNull("instrument", instrument);
	}

	public ITradingProvider getProvider() {
		return provider;
	}

	@Override
	public IInstrument getInstrument() {
		return instrument;
	}

	@Override
	public SortedSet<? extends IPtfFile> getSources() {
		SortedSet<IPtfFile> files = new TreeSet<>();
		for (File file : Files.listContents(directory, FILENAME_FILTER)) {
			IPtfFile pcf = new PtfFile(file);
			files.add(pcf);
		}
		return files;
	}

	@Override
	public IPtfFile getSource(LocalDate month, boolean create) {
		String filename = PtfFormat.getFilename(month);
		File file = new File(directory, filename);
		return new PtfFile(file, month);
	}

	@Override
	public IPtfFile getSource(LocalDate month) {
		String filename = PtfFormat.getFilename(month);
		File file = new File(directory, filename);
		if (file.exists()) {
			return new PtfFile(file, month);
		}
		throw new IllegalArgumentException("File not found for instrument: " + getInstrument() + ", month: " + month);
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
