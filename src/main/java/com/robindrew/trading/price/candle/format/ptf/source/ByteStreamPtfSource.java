package com.robindrew.trading.price.candle.format.ptf.source;

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
import com.robindrew.trading.price.candle.format.ptf.PtfDataSerializer;
import com.robindrew.trading.price.candle.format.ptf.PtfFormat;
import com.robindrew.trading.price.candle.tick.ITickPriceCandle;
import com.robindrew.trading.price.candle.tick.ITickPriceCandleFactory;
import com.robindrew.trading.price.candle.tick.TickPriceCandleFactory;

public abstract class ByteStreamPtfSource implements IPtfSource {

	private static final Logger log = LoggerFactory.getLogger(ByteStreamPtfSource.class);

	private final String name;
	private final LocalDate day;
	private volatile SoftReference<List<ITickPriceCandle>> cached = new SoftReference<>(null);

	protected ByteStreamPtfSource(String name, LocalDate day) {
		this.name = Check.notEmpty("name", name);
		this.day = Check.notNull("day", day);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public LocalDate getDay() {
		return day;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(IPtfSource source) {
		return getDay().compareTo(source.getDay());
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
		final List<ITickPriceCandle> ticks;
		if (candles instanceof List) {
			ticks = (List<ITickPriceCandle>) candles;
		} else {
			ticks = new ArrayList<>(candles.size());
			for (IPriceCandle candle : candles) {
				ticks.add((ITickPriceCandle) candle);
			}
		}
		write(ticks);
	}

	public void write(List<ITickPriceCandle> candles) {
		if (candles.isEmpty()) {
			throw new IllegalArgumentException("candles is empty");
		}
		checkDay(candles);

		if (!exists()) {
			if (!create()) {
				throw new IllegalStateException("Unable to create source for writing: " + this);
			}
		}

		log.debug("Writing PTF file: {}", getName());
		Stopwatch timer = Stopwatch.createStarted();
		PtfDataSerializer serializer = new PtfDataSerializer();
		try (IDataWriter writer = new DataWriter(toByteSink().openBufferedStream())) {
			serializer.writeObject(writer, candles);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
		timer.stop();
		log.debug("Written PTF file: " + getName() + ", " + number(candles) + " candles in " + timer);
	}

	private void checkDay(List<ITickPriceCandle> candles) {
		for (ITickPriceCandle candle : candles) {
			checkDay(candle);
		}
	}

	private void checkDay(ITickPriceCandle candle) {
		if (!day.equals(PtfFormat.getDay(candle))) {
			throw new IllegalArgumentException("Incorrect month for candle: " + candle + ", month=" + day);
		}
	}

	@Override
	public List<ITickPriceCandle> read() {
		return read(new TickPriceCandleFactory());
	}

	@Override
	public List<ITickPriceCandle> read(ITickPriceCandleFactory factory) {
		List<ITickPriceCandle> candles = cached.get();
		if (candles != null) {
			return candles;
		}

		log.info("Reading PTF file: {}", getName());
		Stopwatch timer = Stopwatch.createStarted();
		PtfDataSerializer serializer = new PtfDataSerializer(factory);
		try (IDataReader reader = new DataReader(toByteSource().openBufferedStream())) {
			candles = serializer.readObject(reader);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
		timer.stop();
		log.info("Read from PTF file: {}, {} candles in {} ", getName(), number(candles), timer);

		cached = new SoftReference<>(candles);
		return candles;
	}

	public abstract ByteSource toByteSource();

	public abstract ByteSink toByteSink();

}
