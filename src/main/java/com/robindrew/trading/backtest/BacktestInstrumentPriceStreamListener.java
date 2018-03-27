package com.robindrew.trading.backtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.streaming.latest.ILatestPrice;
import com.robindrew.trading.platform.streaming.latest.LatestPrice;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.sink.subscriber.PriceCandleSubscriberStreamSink;
import com.robindrew.trading.price.candle.io.stream.source.IPriceCandleStreamSource;
import com.robindrew.trading.price.history.IInstrumentPriceHistory;

public class BacktestInstrumentPriceStreamListener extends PriceCandleSubscriberStreamSink implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(BacktestInstrumentPriceStreamListener.class);

	private final IInstrumentPriceHistory history;
	private final LatestPrice latest = new LatestPrice();

	public BacktestInstrumentPriceStreamListener(IInstrumentPriceHistory history) {
		super(history.getInstrument());
		this.history = Check.notNull("history", history);
	}

	public ILatestPrice getLatestPrice() {
		return latest;
	}

	@Override
	public IInstrument getInstrument() {
		return history.getInstrument();
	}

	@Override
	public void run() {
		IPriceCandleStreamSource source = history.getStreamSource();
		log.info("[Started Streaming Prices] {}", getInstrument());
		while (true) {
			IPriceCandle candle = source.getNextCandle();
			if (candle == null) {
				break;
			}
			latest.update(candle);
			putNextCandle(candle);
		}
		log.info("[Finished Streaming Prices] {}", getInstrument());
	}

	@Override
	public String getName() {
		return "BacktestInstrumentPriceStreamListener[" + getInstrument() + "]";
	}

}
