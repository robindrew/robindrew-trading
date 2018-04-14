package com.robindrew.trading.price.candle.format.ptf.source.file;

import java.io.File;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.ITickPriceCandle;
import com.robindrew.trading.price.candle.format.ptf.PtfFormat;
import com.robindrew.trading.price.candle.format.ptf.source.IPtfSource;
import com.robindrew.trading.price.candle.format.ptf.source.PtfSource;

public class PtfFile extends PtfSource implements IPtfFile {

	private final File file;

	public PtfFile(File file, LocalDate month) {
		super(file.getAbsolutePath(), month);
		this.file = Check.notNull("file", file);
	}

	public PtfFile(File file) {
		this(file, PtfFormat.getDay(file));
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public ByteSource toByteSource() {
		return Files.asByteSource(file);
	}

	@Override
	public ByteSink toByteSink() {
		return Files.asByteSink(file);
	}

	@Override
	public boolean exists() {
		return file.exists();
	}

	@Override
	public boolean create() {
		File parent = file.getParentFile();
		parent.mkdirs();
		return parent.exists() && parent.isDirectory();
	}

	@Override
	public LocalDate getDay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ITickPriceCandle> read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(Collection<? extends IPriceCandle> candles) {
		// TODO Auto-generated method stub

	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int compareTo(IPtfSource o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
