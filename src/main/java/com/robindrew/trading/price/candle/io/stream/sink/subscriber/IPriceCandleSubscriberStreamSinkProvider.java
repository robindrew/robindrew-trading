package com.robindrew.trading.price.candle.io.stream.sink.subscriber;

import com.robindrew.trading.IInstrument;

public interface IPriceCandleSubscriberStreamSinkProvider {

	IInstrumentPriceStreamListener getSubscriber(IInstrument instrument);

}
