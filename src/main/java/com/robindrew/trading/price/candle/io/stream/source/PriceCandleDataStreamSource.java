package com.robindrew.trading.price.candle.io.stream.source;

import java.io.File;
import java.io.IOException;

import com.google.common.io.ByteSource;
import com.robindrew.common.io.data.DataReader;
import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataSerializer;
import com.robindrew.common.io.file.FileByteSource;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.Quietly;
import com.robindrew.trading.price.PriceException;
import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleDataStreamSource implements IPriceCandleStreamSource {

	public static PriceCandleDataStreamSource createLineSource(File file, IDataSerializer<IPriceCandle> serializer) {
		String name = file.getName();
		ByteSource source = new FileByteSource(file);
		return new PriceCandleDataStreamSource(name, source, serializer);
	}

	private final String name;
	private final IDataReader reader;
	private final IDataSerializer<IPriceCandle> serializer;
	private boolean closed = false;

	public PriceCandleDataStreamSource(String name, ByteSource source, IDataSerializer<IPriceCandle> serializer) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		if (source == null) {
			throw new NullPointerException("source");
		}
		if (serializer == null) {
			throw new NullPointerException("serializer");
		}
		this.name = name;
		this.serializer = serializer;

		// Create the reader
		try {
			this.reader = new DataReader(source.openBufferedStream());
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
	public IPriceCandle getNextCandle() {
		try {

			if (closed) {
				throw new IllegalStateException("Source is closed: " + name);
			}

			IPriceCandle candle = reader.readObject(false, serializer);

			// No more candles?
			if (candle == null) {
				return null;
			}

			return candle;

		} catch (IOException e) {
			throw new PriceException("Failed to read next candle from: " + getName());
		}
	}

}
