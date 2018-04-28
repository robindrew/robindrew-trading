package com.robindrew.trading.price.candle.format.pcf.source.file;

import java.io.File;
import java.time.LocalDate;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.robindrew.common.util.Check;
import com.robindrew.trading.price.candle.format.pcf.PcfFormat;
import com.robindrew.trading.price.candle.format.pcf.source.PcfSource;

public class PcfFile extends PcfSource implements IPcfFile {

	private final File file;

	public PcfFile(File file, LocalDate month) {
		super(file.getAbsolutePath(), month);
		this.file = Check.notNull("file", file);
	}

	public PcfFile(File file) {
		this(file, PcfFormat.getMonth(file));
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
}
