package com.robindrew.trading.price.candle.line.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.Quietly;
import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleLineFileIterator implements Iterator<IPriceCandle>, AutoCloseable {

	private static BufferedReader createReader(File file, IPriceCandleLineParser parser) {
		try {
			InputStream input = new FileInputStream(file);

			// GZIP file?
			if (file.getName().endsWith(".gz")) {
				input = new GZIPInputStream(input);
			}

			// ZLIB file?
			if (file.getName().endsWith(".zlib")) {
				input = new InflaterInputStream(input);
			}

			Charset charset = parser.getCharset();
			return new BufferedReader(new InputStreamReader(input, charset));
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	private final File file;
	private final BufferedReader reader;
	private final IPriceCandleLineParser parser;

	private boolean closed = false;
	private IPriceCandle candle = null;

	public PriceCandleLineFileIterator(File file, IPriceCandleLineParser parser) {
		this.file = Check.notNull("file", file);
		this.parser = Check.notNull("parser", parser);
		this.reader = createReader(file, parser);
	}

	public File getFile() {
		return file;
	}

	@Override
	public boolean hasNext() {
		if (closed) {
			return false;
		}

		try {
			while (true) {
				String line = reader.readLine();

				// End of file?
				if (line == null) {
					close();
					return false;
				}

				// Skip the line?
				if (parser.skipLine(line)) {
					continue;
				}

				// Parsing successful?
				candle = parser.parseCandle(line);
				if (candle != null) {
					return true;
				}
			}
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public IPriceCandle next() {
		return candle;
	}

	@Override
	public void close() {
		closed = true;
		Quietly.close(reader);
	}

}
