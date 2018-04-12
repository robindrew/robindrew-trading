package com.robindrew.trading.price.candle.io;

import java.io.IOException;

import com.robindrew.common.io.data.IDataReader;
import com.robindrew.common.io.data.IDataSerializer;
import com.robindrew.common.io.data.IDataWriter;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.MidPriceCandle;

/**
 * Non-optimal implementation, but still far more efficient than line parsing/formatting.
 */
public class PriceCandleDataSerializer implements IDataSerializer<IPriceCandle> {

	private final int decimalPlaces;

	public PriceCandleDataSerializer(int decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}

	@Override
	public IPriceCandle readObject(IDataReader reader) throws IOException {
		try {

			long openTime = reader.readPositiveLong();
			long closeTime = reader.readPositiveLong();

			int openPrice = reader.readPositiveInt();
			int highPrice = reader.readPositiveInt();
			int lowPrice = reader.readPositiveInt();
			int closePrice = reader.readPositiveInt();

			return new MidPriceCandle(openPrice, highPrice, lowPrice, closePrice, openTime, closeTime, decimalPlaces);

		} catch (IOException e) {

			// Hack to allow for final candle, may need improvement
			if (isEndOfStream(e)) {
				return null;
			}
			throw e;
		}
	}

	protected boolean isEndOfStream(IOException ioe) {
		return "End of stream".equals(ioe.getMessage());
	}

	@Override
	public void writeObject(IDataWriter writer, IPriceCandle candle) throws IOException {
		if (candle.getDecimalPlaces() != decimalPlaces) {
			throw new IllegalArgumentException("candle=" + candle + " expected decimalPlaces=" + decimalPlaces);
		}

		writer.writePositiveLong(candle.getOpenTime());
		writer.writePositiveLong(candle.getCloseTime());

		writer.writePositiveInt(candle.getMidOpenPrice());
		writer.writePositiveInt(candle.getMidHighPrice());
		writer.writePositiveInt(candle.getMidLowPrice());
		writer.writePositiveInt(candle.getMidClosePrice());
	}
}
