package com.robindrew.trading.platform.streaming.publisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder.SetMultimapBuilder;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.common.io.data.server.IDataConnection;
import com.robindrew.common.io.data.server.IDataConnectionHandler;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.Threads;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.ITickPriceCandle;
import com.robindrew.trading.price.candle.io.stream.sink.IPriceCandleStreamSink;

public class PricePublisherHandler implements AutoCloseable, Runnable, IDataConnectionHandler {

	private static final Logger log = LoggerFactory.getLogger(PricePublisherHandler.class);

	private final BlockingDeque<InstrumentPriceEvent> eventQueue = new LinkedBlockingDeque<>();

	private final Multimap<String, EventSubscriber> subscriberMap = SetMultimapBuilder.treeKeys().arrayListValues().build();
	private final AtomicBoolean closed = new AtomicBoolean(false);

	public IPriceCandleStreamSink getPublisherSink(IInstrument instrument) {
		return new PublisherSink(instrument);
	}

	@Override
	public void close() {
		closed.set(true);
	}

	public boolean isClosed() {
		return closed.get();
	}

	@Override
	public void newConnection(IDataConnection connection) {
		EventSubscriber subscriber = new EventSubscriber(connection);
		synchronized (subscriberMap) {
			subscriberMap.put(subscriber.getInstrumentName(), subscriber);
		}
	}

	private void consumeEvents() {
		List<InstrumentPriceEvent> events = new ArrayList<>();
		eventQueue.drainTo(events);
		if (events.isEmpty()) {
			Threads.sleep(50);
			return;
		}
		consumeEvents(events);
	}

	private void consumeEvents(List<InstrumentPriceEvent> events) {
		synchronized (subscriberMap) {
			for (InstrumentPriceEvent event : events) {
				String instrumentName = event.getInstrument().getName();
				List<EventSubscriber> subscribers = ImmutableList.copyOf(subscriberMap.get(instrumentName));
				for (EventSubscriber subscriber : subscribers) {
					if (!subscriber.consumeEvent(event)) {
						subscriberMap.remove(instrumentName, subscriber);
						break;
					}
				}
			}
		}
	}

	private class EventSubscriber {

		private final IDataConnection connection;
		private final String instrumentName;

		public EventSubscriber(IDataConnection connection) {
			try {
				this.connection = connection;
				this.instrumentName = connection.getReader().readString(false);
			} catch (IOException e) {
				throw Java.propagate(e);
			}
		}

		public String getInstrumentName() {
			return instrumentName;
		}

		private boolean consumeEvent(InstrumentPriceEvent event) {
			try {
				if (connection.isClosed()) {
					return false;
				}

				IDataWriter writer = connection.getWriter();
				ITickPriceCandle tick = (ITickPriceCandle) event.getCandle();
				writer.writePositiveLong(tick.getTimestamp());
				writer.writePositiveInt(tick.getDecimalPlaces());
				writer.writePositiveInt(tick.getBidPrice());
				writer.writePositiveInt(tick.getAskPrice());
				writer.flush();
				return true;

			} catch (Exception e) {
				log.warn("Failed to consume event=" + event, e);
				return false;
			}
		}
	}

	@Override
	public void run() {
		try {
			while (!isClosed()) {
				consumeEvents();
			}
		} finally {
			close();
		}
	}

	private class InstrumentPriceEvent {

		private final IInstrument instrument;
		private final IPriceCandle tick;

		public InstrumentPriceEvent(IInstrument instrument, IPriceCandle tick) {
			this.instrument = instrument;
			this.tick = tick;
		}

		public IInstrument getInstrument() {
			return instrument;
		}

		public IPriceCandle getCandle() {
			return tick;
		}
	}

	private class PublisherSink implements IPriceCandleStreamSink {

		private final IInstrument instrument;

		public PublisherSink(IInstrument instrument) {
			this.instrument = Check.notNull("instrument", instrument);
		}

		@Override
		public String getName() {
			return instrument.getName();
		}

		@Override
		public void close() {
		}

		@Override
		public void putNextCandle(IPriceCandle tick) {
			InstrumentPriceEvent event = new InstrumentPriceEvent(instrument, tick);
			eventQueue.addLast(event);
		}
	}
}
