package com.robindrew.trading.price.tick.io.stream.sink;

import java.util.List;

import com.robindrew.common.concurrent.IEventConsumer;
import com.robindrew.common.concurrent.LoopingEventConsumerThread;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.io.list.sink.IPriceTickListSink;

public class QueuedPriceTickSink implements IPriceTickStreamSink, IPriceTickListSink, AutoCloseable, IEventConsumer<IPriceTick> {

	private final IInstrument instrument;
	private final IPriceTickListSink delegate;

	private final LoopingEventConsumerThread<IPriceTick> tickConsumer;

	public QueuedPriceTickSink(IInstrument instrument, IPriceTickListSink delegate) {
		this.instrument = Check.notNull("instrument", instrument);
		this.delegate = Check.notNull("delegate", delegate);

		String threadName = "QueuedPriceTickSink[" + instrument.getName() + "]";
		this.tickConsumer = new LoopingEventConsumerThread<IPriceTick>(threadName, this);
	}

	public IInstrument getInstrument() {
		return instrument;
	}

	public void start() {
		tickConsumer.start();
	}

	@Override
	public String getName() {
		return delegate.getName();
	}

	@Override
	public void close() {
		delegate.close();
		tickConsumer.close();
	}

	@Override
	public void putNextTick(IPriceTick tick) {
		tickConsumer.publishEvent(tick);
	}

	@Override
	public void putNextTicks(List<IPriceTick> ticks) {
		tickConsumer.publishEvents(ticks);
	}

	@Override
	public void consumeEvents(List<IPriceTick> ticks) {
		delegate.putNextTicks(ticks);
	}

}
