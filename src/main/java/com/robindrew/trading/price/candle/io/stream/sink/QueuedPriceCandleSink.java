package com.robindrew.trading.price.candle.io.stream.sink;

import java.util.List;

import com.robindrew.common.concurrent.IEventConsumer;
import com.robindrew.common.concurrent.LoopingEventConsumerThread;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.list.sink.IPriceCandleListSink;

public class QueuedPriceCandleSink implements IPriceCandleStreamSink, IPriceCandleListSink, AutoCloseable, IEventConsumer<IPriceCandle> {

	private final IInstrument instrument;
	private final IPriceCandleListSink delegate;

	private final LoopingEventConsumerThread<IPriceCandle> candleConsumer;

	public QueuedPriceCandleSink(IInstrument instrument, IPriceCandleListSink delegate) {
		this.instrument = Check.notNull("instrument", instrument);
		this.delegate = Check.notNull("delegate", delegate);

		String threadName = "QueuedPriceCandleSink[" + instrument.getName() + "]";
		this.candleConsumer = new LoopingEventConsumerThread<IPriceCandle>(threadName, this);
	}

	public IInstrument getInstrument() {
		return instrument;
	}

	public void start() {
		candleConsumer.start();
	}

	@Override
	public String getName() {
		return delegate.getName();
	}

	@Override
	public void close() {
		delegate.close();
		candleConsumer.close();
	}

	@Override
	public void putNextCandle(IPriceCandle candle) {
		candleConsumer.publishEvent(candle);
	}

	@Override
	public void putNextCandles(List<IPriceCandle> candles) {
		candleConsumer.publishEvents(candles);
	}

	@Override
	public void consumeEvents(List<IPriceCandle> candles) {
		delegate.putNextCandles(candles);
	}

}
