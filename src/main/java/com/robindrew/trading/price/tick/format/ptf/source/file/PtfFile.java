package com.robindrew.trading.price.tick.format.ptf.source.file;

import java.io.File;
import java.time.LocalDate;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.robindrew.common.util.Check;
import com.robindrew.trading.price.tick.format.ptf.PtfFormat;
import com.robindrew.trading.price.tick.format.ptf.source.PtfSource;

public class PtfFile extends PtfSource implements IPtfFile {

	private final File file;

	public PtfFile(File file, LocalDate day) {
		super(file.getAbsolutePath(), day);
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

}
