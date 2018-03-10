package com.robindrew.trading.price.candle.io.line.source;

import java.io.File;
import java.nio.charset.Charset;

import com.google.common.base.Charsets;
import com.robindrew.common.io.file.FileByteSource;

public class FileLineSource extends ByteLineSource {

	private final File file;

	public FileLineSource(File file, Charset charset) {
		super(file.getName(), new FileByteSource(file), charset);
		this.file = file;
	}

	public FileLineSource(File file) {
		this(file, Charsets.UTF_8);
	}

	public File getFile() {
		return file;
	}

}
