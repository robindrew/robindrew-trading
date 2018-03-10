package com.robindrew.trading.price.candle.io.stream.sink;

import java.io.File;
import java.io.IOException;

import com.google.common.io.ByteSink;
import com.robindrew.common.io.data.DataWriter;
import com.robindrew.common.io.data.IDataSerializer;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.file.FileByteSink;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.Quietly;
import com.robindrew.trading.price.PriceException;
import com.robindrew.trading.price.candle.IPriceCandle;

public class PriceCandleDataStreamSink implements IPriceCandleStreamSink {

	public static PriceCandleDataStreamSink createFileDataSink(File file, IDataSerializer<IPriceCandle> serializer) {
		String name = file.getName();
		ByteSink sink = new FileByteSink(file);
		return new PriceCandleDataStreamSink(name, sink, serializer);
	}

	private final String name;
	private final IDataWriter writer;
	private final IDataSerializer<IPriceCandle> serializer;
	private boolean closed = false;

	public PriceCandleDataStreamSink(String name, ByteSink sink, IDataSerializer<IPriceCandle> serializer) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		if (sink == null) {
			throw new NullPointerException("sink");
		}
		if (serializer == null) {
			throw new NullPointerException("serializer");
		}
		this.name = name;
		this.serializer = serializer;

		// Create the writer
		try {
			writer = new DataWriter(sink.openBufferedStream());
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
	public void putNextCandle(IPriceCandle candle) {
		if (candle == null) {
			throw new NullPointerException("candle");
		}
		if (closed) {
			throw new IllegalStateException("Sink is closed: " + name);
		}

		try {
			writer.writeObject(candle, false, serializer);
		} catch (IOException e) {
			throw new PriceException("Failed to write next candle to: " + getName() + ", candle: " + candle);
		}

	}

}
