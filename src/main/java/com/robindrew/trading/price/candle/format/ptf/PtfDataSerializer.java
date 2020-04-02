package com.robindrew.trading.price.candle.format.ptf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataSerializer;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.trading.price.candle.tick.ITickPriceCandle;
import com.robindrew.trading.price.candle.tick.ITickPriceCandleFactory;
import com.robindrew.trading.price.candle.tick.TickPriceCandleFactory;

public class PtfDataSerializer implements IDataSerializer<List<ITickPriceCandle>> {

	private final ITickPriceCandleFactory candleFactory;

	public PtfDataSerializer(ITickPriceCandleFactory candleFactory) {
		this.candleFactory = candleFactory;
	}

	public PtfDataSerializer() {
		this(new TickPriceCandleFactory());
	}

	@Override
	public List<ITickPriceCandle> readObject(IDataReader reader) throws IOException {
		List<ITickPriceCandle> list = new ArrayList<>();

		int count = reader.readPositiveInt();
		int basePrice = reader.readPositiveInt();
		long baseTime = reader.readPositiveLong();
		int decimalPlaces = reader.readPositiveInt();

		for (int i = 0; i < count; i++) {

			long instantTime = reader.readPositiveLong();

			instantTime += baseTime;

			int bidPrice = reader.readDynamicInt() + basePrice;
			int askPrice = reader.readDynamicInt() + basePrice;

			ITickPriceCandle tick = candleFactory.create(bidPrice, askPrice, instantTime, decimalPlaces);
			list.add(tick);

			basePrice = bidPrice;
			baseTime = instantTime;
		}

		return list;
	}

	@Override
	public void writeObject(IDataWriter writer, List<ITickPriceCandle> ticks) throws IOException {
		if (ticks.isEmpty()) {
			throw new IllegalArgumentException("candles is empty");
		}

		ITickPriceCandle firstCandle = ticks.get(0);

		int count = ticks.size();
		int basePrice = firstCandle.getBidPrice();
		long baseTime = firstCandle.getTimestamp();
		int decimalPlaces = firstCandle.getDecimalPlaces();

		writer.writePositiveInt(count);
		writer.writePositiveInt(basePrice);
		writer.writePositiveLong(baseTime);
		writer.writePositiveInt(decimalPlaces);

		for (ITickPriceCandle tick : ticks) {
			try {

				long timestamp = tick.getTimestamp();

				int bidPrice = tick.getBidPrice();
				int askPrice = tick.getAskPrice();

				writer.writePositiveLong(timestamp - baseTime);

				writer.writeDynamicInt(bidPrice - basePrice);
				writer.writeDynamicInt(askPrice - basePrice);

				basePrice = bidPrice;
				baseTime = timestamp;

			} catch (Exception e) {
				throw new IOException("Failed to serialize candle: " + tick, e);
			}
		}
	}

}