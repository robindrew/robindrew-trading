package com.robindrew.trading.price.candle.format.pif;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataSerializer;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.trading.price.candle.IPriceCandleInstant;
import com.robindrew.trading.price.candle.PriceCandleInstant;

public class PifDataSerializer implements IDataSerializer<List<IPriceCandleInstant>> {

	@Override
	public List<IPriceCandleInstant> readObject(IDataReader reader) throws IOException {
		List<IPriceCandleInstant> list = new ArrayList<>();

		int count = reader.readPositiveInt();
		int basePrice = reader.readPositiveInt();
		long baseTime = reader.readPositiveLong();
		int decimalPlaces = reader.readPositiveInt();

		for (int i = 0; i < count; i++) {

			long instantTime = reader.readPositiveLong();

			instantTime += baseTime;

			int price = reader.readDynamicInt() + basePrice;

			IPriceCandleInstant instant = new PriceCandleInstant(price, instantTime, decimalPlaces);
			list.add(instant);

			basePrice = price;
			baseTime = instantTime;
		}

		return list;
	}

	@Override
	public void writeObject(IDataWriter writer, List<IPriceCandleInstant> instants) throws IOException {
		if (instants.isEmpty()) {
			throw new IllegalArgumentException("candles is empty");
		}

		IPriceCandleInstant firstCandle = instants.get(0);

		int count = instants.size();
		int basePrice = firstCandle.getMidPrice();
		long baseTime = firstCandle.getTimestamp();
		int decimalPlaces = firstCandle.getDecimalPlaces();

		writer.writePositiveInt(count);
		writer.writePositiveInt(basePrice);
		writer.writePositiveLong(baseTime);
		writer.writePositiveInt(decimalPlaces);

		for (IPriceCandleInstant candle : instants) {
			try {

				long timestamp = candle.getTimestamp();

				int price = candle.getMidPrice();

				writer.writePositiveLong(timestamp - baseTime);

				writer.writeDynamicInt(price - basePrice);

				basePrice = price;
				baseTime = timestamp;

			} catch (Exception e) {
				throw new IOException("Failed to serialize candle: " + candle, e);
			}
		}
	}

}
