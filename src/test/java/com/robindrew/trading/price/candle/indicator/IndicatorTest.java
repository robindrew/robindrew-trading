package com.robindrew.trading.price.candle.indicator;

import static com.robindrew.trading.price.candle.interval.PriceCandleIntervals.DAILY;
import static com.robindrew.trading.price.candle.interval.PriceCandleIntervals.HOURLY;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.generator.PriceCandleGenerator;

public class IndicatorTest {

	@Test
	public void testInterval() {

		PriceCandleGenerator generator = new PriceCandleGenerator(HOURLY);
		List<IPriceCandle> candles = generator.generateCandles(72);

		try (TestIndicator indicator = new TestIndicator(DAILY, 2)) {
			for (IPriceCandle candle : candles) {
				indicator.putNextCandle(candle);

				List<IPriceCandle> latest = indicator.getLatest();
				Assert.assertTrue(latest.size() <= 2);
			}
		}
	}

}
