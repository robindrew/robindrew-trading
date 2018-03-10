package com.robindrew.trading.price.candle.io.line.sink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import com.google.common.io.ByteSink;
import com.robindrew.common.io.file.FileByteSink;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.Quietly;

public class ByteLineSink implements ILineSink {

	public static ByteLineSink createLineSink(File file, Charset charset) {
		String name = file.getName();
		FileByteSink sink = new FileByteSink(file);
		return new ByteLineSink(name, sink, charset);
	}

	private final String name;
	private final BufferedWriter writer;
	private boolean closed = false;

	public ByteLineSink(String name, ByteSink sink, Charset charset) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		if (sink == null) {
			throw new NullPointerException("sink");
		}
		this.name = name;

		// Create the writer
		try {
			this.writer = new BufferedWriter(new OutputStreamWriter(sink.openStream(), charset));
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void close() {
		closed = true;
		Quietly.close(writer);
	}

	@Override
	public void putNextLine(String line) {
		if (closed) {
			throw new IllegalStateException("Sink is closed: " + getName());
		}
		try {
			writer.write(line);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

}
