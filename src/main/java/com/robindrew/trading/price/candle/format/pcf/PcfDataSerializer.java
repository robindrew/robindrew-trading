package com.robindrew.trading.price.candle.format.pcf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataSerializer;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandle;

public class PcfDataSerializer implements IDataSerializer<List<IPriceCandle>> {

	private static final Logger log = LoggerFactory.getLogger(PcfDataSerializer.class);

	@Override
	public List<IPriceCandle> readObject(IDataReader reader) throws IOException {
		List<IPriceCandle> list = new ArrayList<>();

		int count = reader.readPositiveInt();
		int basePrice = reader.readPositiveInt();
		long baseTime = reader.readPositiveLong();
		int decimalPlaces = reader.readPositiveInt();

		for (int i = 0; i < count; i++) {

			long openTime = reader.readPositiveLong();
			long closeTime = reader.readPositiveLong();

			openTime += baseTime;
			closeTime += openTime;

			int open = reader.readDynamicInt() + basePrice;
			int high = reader.readDynamicInt() + basePrice;
			int low = reader.readDynamicInt() + basePrice;
			int close = reader.readDynamicInt() + basePrice;

			IPriceCandle candle = new PriceCandle(open, high, low, close, openTime, closeTime, decimalPlaces);
			list.add(candle);

			basePrice = close;
			baseTime = closeTime;
		}

		return list;
	}

	@Override
	public void writeObject(IDataWriter writer, List<IPriceCandle> candles) throws IOException {
		if (candles.isEmpty()) {
			throw new IllegalArgumentException("candles is empty");
		}

		IPriceCandle firstCandle = candles.get(0);

		int count = candles.size();
		int basePrice = firstCandle.getOpenPrice();
		long baseTime = firstCandle.getOpenTime();
		int decimalPlaces = firstCandle.getDecimalPlaces();

		writer.writePositiveInt(count);
		writer.writePositiveInt(basePrice);
		writer.writePositiveLong(baseTime);
		writer.writePositiveInt(decimalPlaces);

		IPriceCandle previous = null;
		for (IPriceCandle candle : candles) {
			try {

				long openTime = candle.getOpenTime();
				long closeTime = candle.getCloseTime();

				int open = candle.getOpenPrice();
				int high = candle.getHighPrice();
				int low = candle.getLowPrice();
				int close = candle.getClosePrice();

				writer.writePositiveLong(openTime - baseTime);
				writer.writePositiveLong(closeTime - openTime);

				writer.writeDynamicInt(open - basePrice);
				writer.writeDynamicInt(high - basePrice);
				writer.writeDynamicInt(low - basePrice);
				writer.writeDynamicInt(close - basePrice);

				basePrice = close;
				baseTime = closeTime;

				previous = candle;
			} catch (Exception e) {
				throw new IOException("Failed to serialize candle: " + candle + " (previous=" + previous + ")", e);
			}
		}
	}

}
