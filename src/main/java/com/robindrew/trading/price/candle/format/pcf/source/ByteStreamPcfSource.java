package com.robindrew.trading.price.candle.format.pcf.source;

import static com.robindrew.common.text.Strings.number;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.robindrew.common.io.data.DataReader;
import com.robindrew.common.io.data.DataWriter;
import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataSerializer;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.format.pcf.PcfDataSerializer;
import com.robindrew.trading.price.candle.format.pcf.PcfFormat;

public abstract class ByteStreamPcfSource implements IPcfSource {

	private static final Logger log = LoggerFactory.getLogger(ByteStreamPcfSource.class);

	private final String name;
	private final LocalDate month;
	private final IDataSerializer<List<IPriceCandle>> serializer = new PcfDataSerializer();
	private volatile SoftReference<List<IPriceCandle>> cached = new SoftReference<>(null);

	protected ByteStreamPcfSource(String name, LocalDate month) {
		if (month.getDayOfMonth() != 1) {
			throw new IllegalArgumentException("month=" + month);
		}

		this.name = Check.notEmpty("name", name);
		this.month = Check.notNull("month", month);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public LocalDate getMonth() {
		return month;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(IPcfSource source) {
		return getMonth().compareTo(source.getMonth());
	}

	@Override
	public int size() {
		try {
			return (int) toByteSource().size();
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(Collection<? extends IPriceCandle> candles) {
		final List<IPriceCandle> list;
		if (candles instanceof List) {
			list = (List<IPriceCandle>) candles;
		} else {
			list = new ArrayList<>(candles);
		}
		write(list);
	}

	public void write(List<IPriceCandle> candles) {
		if (candles.isEmpty()) {
			throw new IllegalArgumentException("candles is empty");
		}
		checkMonth(candles);

		if (!exists()) {
			if (!create()) {
				throw new IllegalStateException("Unable to create source for writing: " + this);
			}
		}

		log.debug("Writing PCF file: {}", getName());
		Stopwatch timer = Stopwatch.createStarted();
		try (IDataWriter writer = new DataWriter(toByteSink().openBufferedStream())) {
			serializer.writeObject(writer, candles);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
		timer.stop();
		log.debug("Written PCF file: " + getName() + ", " + number(candles) + " candles in " + timer);
	}

	private void checkMonth(Collection<? extends IPriceCandle> candles) {
		for (IPriceCandle candle : candles) {
			checkMonth(candle);
		}
	}

	private void checkMonth(IPriceCandle candle) {
		if (!month.equals(PcfFormat.getMonth(candle))) {
			throw new IllegalArgumentException("Incorrect month for candle: " + candle + ", month=" + month);
		}
	}

	@Override
	public List<IPriceCandle> read() {
		List<IPriceCandle> candles = cached.get();
		if (candles != null) {
			return candles;
		}

		log.debug("Reading PCF file: {}", getName());
		Stopwatch timer = Stopwatch.createStarted();
		try (IDataReader reader = new DataReader(toByteSource().openBufferedStream())) {
			candles = serializer.readObject(reader);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
		timer.stop();
		log.debug("Read from PCF file: {}, {} candles in {} ", getName(), number(candles), timer);

		cached = new SoftReference<>(candles);
		return candles;
	}

	public abstract ByteSource toByteSource();

	public abstract ByteSink toByteSink();

}
