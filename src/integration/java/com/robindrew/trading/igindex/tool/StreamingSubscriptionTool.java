package com.robindrew.trading.igindex.tool;

import java.io.File;

import com.robindrew.common.util.Threads;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.io.stream.sink.subscriber.IInstrumentPriceStreamListener;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.price.precision.PricePrecision;
import com.robindrew.trading.provider.igindex.IgInstrument;
import com.robindrew.trading.provider.igindex.platform.sink.PriceCandleTickFileSink;
import com.robindrew.trading.provider.igindex.platform.streaming.subscription.charttick.ChartTickPriceStream;
import com.robindrew.trading.provider.igindex.platform.streaming.subscription.charttick.ChartTickPriceStreamBuilder;

public class StreamingSubscriptionTool {

	public static void main(String[] args) {

		// Configuration
		String priceDirectory = "c:/temp/prices";
		IInstrument instrument = IgInstrument.SPOT_BITCOIN;
		IPricePrecision precision = new PricePrecision(2, 900, 90000);

		// Create the stream
		try (ChartTickPriceStream stream = new ChartTickPriceStreamBuilder().build(instrument, precision)) {
			stream.start();

			PriceCandleTickFileSink outputFile = new PriceCandleTickFileSink(instrument, new File(priceDirectory));
			outputFile.start();

			IInstrumentPriceStreamListener listener = stream.getListener();
			listener.register(outputFile);

			Threads.sleepForever();
		}
	}

}
