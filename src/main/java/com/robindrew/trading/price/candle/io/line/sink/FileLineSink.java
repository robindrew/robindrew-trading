package com.robindrew.trading.price.candle.io.line.sink;

import java.io.File;
import java.nio.charset.Charset;

import com.google.common.base.Charsets;
import com.robindrew.common.io.file.FileByteSink;

public class FileLineSink extends ByteLineSink {

	private final File file;

	public FileLineSink(File file, Charset charset) {
		super(file.getName(), new FileByteSink(file), charset);
		this.file = file;
	}

	public FileLineSink(File file) {
		this(file, Charsets.UTF_8);
	}

	public File getFile() {
		return file;
	}

}
