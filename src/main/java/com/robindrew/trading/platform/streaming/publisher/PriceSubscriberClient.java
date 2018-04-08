package com.robindrew.trading.platform.streaming.publisher;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.server.DataConnection;
import com.robindrew.common.io.data.server.IDataConnection;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.PriceTick;
import com.robindrew.trading.price.tick.io.stream.sink.IPriceTickStreamSink;

/**
 * Simple price subscriber client for the {@link PricePublisherServer}. Each client must be run in its own thread and
 * subscribes for updates to a single instrument.
 */
public class PriceSubscriberClient implements Runnable, IPriceTickStreamSink {

	private static final Logger log = LoggerFactory.getLogger(PriceSubscriberClient.class);

	private final String host;
	private final int port;
	private final IInstrument instrument;
	private final Set<IPriceTickStreamSink> priceSinks = new CopyOnWriteArraySet<>();
	private final AtomicBoolean closed = new AtomicBoolean(false);

	public PriceSubscriberClient(String host, int port, IInstrument instrument) throws IOException {
		this.host = host;
		this.port = port;
		this.instrument = instrument;
	}

	@Override
	public String getName() {
		return "PriceSubscriber[" + instrument.getName() + "]";
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public IInstrument getInstrument() {
		return instrument;
	}

	public Thread start() {
		Thread thread = new Thread(this, getName());
		thread.start();
		return thread;
	}

	@Override
	public void run() {
		log.error("[{}] PriceSubscriber RUNNING", getName());

		try (IDataConnection connection = new DataConnection(host, port)) {

			// Write the instrument name
			IDataWriter writer = connection.getWriter();
			writer.writeString(instrument.getName(), false);
			writer.flush();

			// Read the price updates
			IDataReader reader = connection.getReader();
			while (!isClosed()) {

				long timestamp = reader.readPositiveLong();
				int decimalPlaces = reader.readPositiveInt();
				int bidPrice = reader.readPositiveInt();
				int askPrice = reader.readPositiveInt();

				IPriceTick tick = new PriceTick(bidPrice, askPrice, timestamp, decimalPlaces);
				putNextTick(tick);
			}

		} catch (Exception e) {
			log.error("Error communicating with server", e);
		} finally {
			close();
		}

		log.error("[{}] PriceSubscriber CLOSED", getName());
	}

	public boolean isClosed() {
		return closed.get();
	}

	public void close() {
		closed.set(true);
		priceSinks.clear();
	}

	public void register(IPriceTickStreamSink sink) {
		Check.notNull("sink", sink);
		priceSinks.add(sink);
	}

	@Override
	public void putNextTick(IPriceTick tick) {
		for (IPriceTickStreamSink sink : priceSinks) {
			sink.putNextTick(tick);
		}
	}
}
