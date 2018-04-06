package com.robindrew.trading.price.tick.format.ptf.source;

import static com.robindrew.common.text.Strings.number;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.time.LocalDate;
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
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.format.ptf.PtfDataSerializer;
import com.robindrew.trading.price.tick.format.ptf.PtfFormat;

public abstract class PtfSource implements IPtfSource {

	private static final Logger log = LoggerFactory.getLogger(PtfSource.class);

	private final String name;
	private final LocalDate day;
	private final IDataSerializer<List<IPriceTick>> serializer = new PtfDataSerializer();
	private volatile SoftReference<List<IPriceTick>> cached = new SoftReference<>(null);

	protected PtfSource(String name, LocalDate day) {
		this.name = Check.notEmpty("name", name);
		this.day = Check.notNull("month", day);
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

	@Override
	public void write(List<IPriceTick> ticks) {
		if (ticks.isEmpty()) {
			throw new IllegalArgumentException("ticks is empty");
		}
		checkMonth(ticks);

		if (!exists()) {
			if (!create()) {
				throw new IllegalStateException("Unable to create source for writing: " + this);
			}
		}

		log.debug("Writing PCF file: {}", getName());
		Stopwatch timer = Stopwatch.createStarted();
		try (IDataWriter writer = new DataWriter(toByteSink().openBufferedStream())) {
			serializer.writeObject(writer, ticks);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
		timer.stop();
		log.debug("Written PCF file: " + getName() + ", " + number(ticks) + " ticks in " + timer);
	}

	private void checkMonth(List<IPriceTick> ticks) {
		for (IPriceTick tick : ticks) {
			checkMonth(tick);
		}
	}

	private void checkMonth(IPriceTick tick) {
		if (!day.equals(PtfFormat.getDay(tick))) {
			throw new IllegalArgumentException("Incorrect month for tick: " + tick + ", day=" + day);
		}
	}

	@Override
	public List<IPriceTick> read() {
		List<IPriceTick> ticks = cached.get();
		if (ticks != null) {
			return ticks;
		}

		log.debug("Reading PCF file: {}", getName());
		Stopwatch timer = Stopwatch.createStarted();
		try (IDataReader reader = new DataReader(toByteSource().openBufferedStream())) {
			ticks = serializer.readObject(reader);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
		timer.stop();
		log.debug("Read from PCF file: {}, {} ticks in {} ", getName(), number(ticks), timer);

		cached = new SoftReference<>(ticks);
		return ticks;
	}

	public abstract ByteSource toByteSource();

	public abstract ByteSink toByteSink();

}
