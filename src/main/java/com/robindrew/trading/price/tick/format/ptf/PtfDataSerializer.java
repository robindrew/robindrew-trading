package com.robindrew.trading.price.tick.format.ptf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataSerializer;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.trading.price.tick.IPriceTick;
import com.robindrew.trading.price.tick.PriceTick;

public class PtfDataSerializer implements IDataSerializer<List<IPriceTick>> {

	@Override
	public List<IPriceTick> readObject(IDataReader reader) throws IOException {
		List<IPriceTick> list = new ArrayList<>();

		int count = reader.readPositiveInt();
		int basePrice = reader.readPositiveInt();
		long baseTime = reader.readPositiveLong();
		int decimalPlaces = reader.readPositiveInt();

		for (int i = 0; i < count; i++) {

			long instantTime = reader.readPositiveLong();

			instantTime += baseTime;

			int bidPrice = reader.readDynamicInt() + basePrice;
			int askPrice = reader.readDynamicInt() + basePrice;

			IPriceTick instant = new PriceTick(bidPrice, askPrice, instantTime, decimalPlaces);
			list.add(instant);

			basePrice = bidPrice;
			baseTime = instantTime;
		}

		return list;
	}

	@Override
	public void writeObject(IDataWriter writer, List<IPriceTick> instants) throws IOException {
		if (instants.isEmpty()) {
			throw new IllegalArgumentException("candles is empty");
		}

		IPriceTick firstCandle = instants.get(0);

		int count = instants.size();
		int basePrice = firstCandle.getBidPrice();
		long baseTime = firstCandle.getTimestamp();
		int decimalPlaces = firstCandle.getDecimalPlaces();

		writer.writePositiveInt(count);
		writer.writePositiveInt(basePrice);
		writer.writePositiveLong(baseTime);
		writer.writePositiveInt(decimalPlaces);

		for (IPriceTick candle : instants) {
			try {

				long timestamp = candle.getTimestamp();

				int bidPrice = candle.getBidPrice();
				int askPrice = candle.getAskPrice();

				writer.writePositiveLong(timestamp - baseTime);

				writer.writeDynamicInt(bidPrice - basePrice);
				writer.writeDynamicInt(askPrice - basePrice);

				basePrice = bidPrice;
				baseTime = timestamp;

			} catch (Exception e) {
				throw new IOException("Failed to serialize candle: " + candle, e);
			}
		}
	}

}
