package com.robindrew.trading.price.candle.io.line.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.google.common.io.ByteSource;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.Quietly;

public class ByteLineSource implements ILineSource {

	private final String name;
	private final BufferedReader reader;
	private boolean closed = false;

	public ByteLineSource(String name, ByteSource source, Charset charset) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		this.name = name;

		// Create the reader
		try {
			this.reader = new BufferedReader(new InputStreamReader(source.openStream(), charset));
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
		Quietly.close(reader);
	}

	@Override
	public String getNextLine() {
		if (closed) {
			throw new IllegalStateException("Source is closed: " + getName());
		}

		try {
			return reader.readLine();
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

}
